package com.campscribe.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.campscribe.business.CampInfoManager;
import com.campscribe.business.EventManager;
import com.campscribe.business.ScoutManager;
import com.campscribe.business.TrackProgressManager;
import com.campscribe.model.CampInfo;
import com.campscribe.model.Clazz;
import com.campscribe.model.Event;
import com.campscribe.model.EventUtil;
import com.campscribe.model.Scout;
import com.campscribe.model.ScoutComparator;
import com.campscribe.model.TrackProgress;
import com.campscribe.model.TrackProgress.RequirementCompletion;
import com.campscribe.model.Unit;
import com.campscribe.model.UnitComparator;
import com.googlecode.objectify.Key;
import com.pdfjet.Align;
import com.pdfjet.Cell;
import com.pdfjet.CoreFont;
import com.pdfjet.Font;
import com.pdfjet.Letter;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.Table;
import com.pdfjet.TextLine;

public class ReportServlet extends HttpServlet {

	private static final long serialVersionUID = -4984668055373587237L;
	private static final int[] grey = {190,190,190};
	private static final int[] lightGrey = {211,211,211};
	private static final int[] white = {255,255,255};

	protected final Log logger = LogFactory.getLog(getClass());


	private CampInfoManager campInfoMgr;
	private EventManager eventMgr;
	private ScoutManager scoutMgr;
	private TrackProgressManager tpMgr;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		logger.info("starting report generation");

		List<CampInfo> ciList = getCampInfoManager().listCampInfos();
		CampInfo ci = null;
		if (ciList!=null && ciList.size()>0) {
			ci = ciList.get(0);
		} else {
			ci = new CampInfo();
		}

		logger.info("done gathering camp data");

		List<Event> events = getEventManager().listEvents();
		Event event = null;

		//get the request parms to filter results
		Long eventIdParm = Long.valueOf(req.getParameter("eventId"));
		if (eventIdParm == null) {
			event = EventUtil.findCurrentEvent(events);
			eventIdParm = EventUtil.findCurrentEventId(events);
		} else {
			event = eventMgr.getEvent(eventIdParm);
		}
		String programAreaParm = req.getParameter("programArea");
		String unitParm = req.getParameter("unit");

		Map<Key<Clazz>, Clazz> clazzLookup = getClazzLookup(new Key<Event>(Event.class, eventIdParm));

		String fileName = "campscribe_unit_report.pdf"; 

		resp.setContentType("application/pdf"); 
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + 
				fileName + "\""); 

		OutputStream ostream = resp.getOutputStream();
		
		Map<Unit, List<Page>> unitPageMap = new HashMap<Unit, List<Page>>(); 

		try {
			logger.info("tie PDF to ostream");

			PDF pdf = new PDF(ostream);
			pdf.setTitle("CampScribe Unit Report");
			pdf.setSubject("CampScribe Unit Report for "+event.getDescription());
			pdf.setAuthor(ci.getCampName()+" via CampScribe Software");

			Font unitFont = new Font(pdf, CoreFont.HELVETICA_BOLD);
			unitFont.setSize(12.0);

			Font headerFont = new Font(pdf, CoreFont.HELVETICA_BOLD);
			headerFont.setSize(7.0);

			Font bodyFont = new Font(pdf, CoreFont.HELVETICA);
			bodyFont.setSize(7.0);

			Font footerFont = new Font(pdf, CoreFont.HELVETICA_OBLIQUE);
			footerFont.setSize(8.0);

			logger.info("gathering data for event "+eventIdParm+" with unit filter "+unitParm);

			TreeMap<Unit, TreeMap<Scout,ArrayList<TrackProgress>>> scoutByUnitMap = new TreeMap<Unit, TreeMap<Scout,ArrayList<TrackProgress>>>(new UnitComparator());

			List<Scout> scoutList = null;
			scoutList = getScoutManager().getScoutsByEvent(new Key<Event>(Event.class, eventIdParm));
			logger.info("got list of scouts of length "+scoutList.size());

			for (Scout s:scoutList) {
				Unit unit = new Unit(s.getUnitType(), s.getUnitNumber());
				if ("ALL".equals(unitParm) || unit.toString().equals(unitParm)) {
					if (!scoutByUnitMap.containsKey(unit)) {
						TreeMap<Scout,ArrayList<TrackProgress>> trackingMap = new TreeMap<Scout,ArrayList<TrackProgress>>(new ScoutComparator());
						scoutByUnitMap.put(unit, trackingMap);
					}

					logger.info("building tracking list for scout "+s.getDisplayName());

					List<TrackProgress> trackingList = getTrackProgressManager().getTrackingForScout(new Key<Scout>(Scout.class, s.getId()));
					for (TrackProgress tp:trackingList) {
						Clazz c = clazzLookup.get(tp.getClazzKey());
						if ("ALL".equals(programAreaParm) || c.getProgramArea().equals(programAreaParm)) {
							if (!scoutByUnitMap.get(unit).containsKey(s)) {
								ArrayList<TrackProgress> badgeList = new ArrayList<TrackProgress>();
								scoutByUnitMap.get(unit).put(s, badgeList);
							}
							scoutByUnitMap.get(unit).get(s).add(tp);
						}
					}
				}
			}

			logger.info("done gathering tracking data");

			for (Map.Entry<Unit, TreeMap<Scout,ArrayList<TrackProgress>>> unit:scoutByUnitMap.entrySet()) {
				logger.info("starting page generation for "+unit.getKey());

				Page page = createPage(pdf, unitFont, ci, event, unit);
				if (!unitPageMap.containsKey(unit.getKey())) {
					unitPageMap.put(unit.getKey(), new ArrayList<Page>());
				}
				unitPageMap.get(unit.getKey()).add(page);
				
				Table table = new Table();
				table.setLineWidth(0.2);
				table.setPosition(36, 126);
				List<List<Cell>> tableData = new ArrayList<List<Cell>>();

				List<Cell> headerRow = new ArrayList<Cell>();
				Cell c = new Cell(headerFont, "Scout Name");
				c.setTextAlignment(Align.CENTER);
				c.setBgColor(grey);
				headerRow.add(c);
				c = new Cell(headerFont, "Merit Badge");
				c.setTextAlignment(Align.CENTER);
				c.setBgColor(grey);
				headerRow.add(c);
				c = new Cell(headerFont, "Status");
				c.setTextAlignment(Align.CENTER);
				c.setBgColor(grey);
				headerRow.add(c);
				c = new Cell(headerFont, "Counselor Signature");
				c.setTextAlignment(Align.CENTER);
				c.setBgColor(grey);
				headerRow.add(c);
				c = new Cell(headerFont, "Date");
				c.setTextAlignment(Align.CENTER);
				c.setBgColor(grey);
				headerRow.add(c);
				c = new Cell(headerFont, "Completed Requirements");
				c.setTextAlignment(Align.CENTER);
				c.setBgColor(grey);
				headerRow.add(c);
				c = new Cell(headerFont, "Incomplete Requirements");
				c.setTextAlignment(Align.CENTER);
				c.setBgColor(grey);
				headerRow.add(c);
				tableData.add(headerRow);

				boolean evenRow = false;
				logger.info("adding scouts to table");
				for (Map.Entry<Scout,ArrayList<TrackProgress>> scout:unit.getValue().entrySet()) {
					for (TrackProgress tp:scout.getValue()) {
						if ("ALL".equals(programAreaParm) || clazzLookup.get(tp.getClazzKey()).getProgramArea().equals(programAreaParm)) {
							List<Cell> aRow = new ArrayList<Cell>();
							c = new Cell(bodyFont, scout.getKey().getDisplayName());
							c.setBgColor(evenRow?lightGrey:white);
							aRow.add(c);
							c = new Cell(bodyFont, clazzLookup.get(tp.getClazzKey()).getMbName());
							c.setBgColor(evenRow?lightGrey:white);
							aRow.add(c);
							c = new Cell(bodyFont, tp.getComplete()?"Complete":"Partial");
							c.setBgColor(evenRow?lightGrey:white);
							aRow.add(c);
							c = new Cell(bodyFont, ""); //signature block
							c.setBgColor(evenRow?lightGrey:white);
							aRow.add(c);
							c = new Cell(bodyFont, tp.getComplete()?event.getEndDateDisplayStr():"");
							c.setBgColor(evenRow?lightGrey:white);
							aRow.add(c);
							StringBuilder completeReqs = new StringBuilder();
							StringBuilder incompleteReqs = new StringBuilder();
							for (RequirementCompletion rc:tp.getRequirementList()) {
								if (rc.isCompleted()) {
									if (completeReqs.length() > 0) {
										completeReqs.append(", ");
									}
									completeReqs.append(rc.getReqNumber());
								} else {
									if (incompleteReqs.length() > 0) {
										incompleteReqs.append(", ");
									}
									incompleteReqs.append(rc.getReqNumber());
								}
							}
							c = new Cell(bodyFont, completeReqs.toString());
							c.setBgColor(evenRow?lightGrey:white);
							aRow.add(c);
							c = new Cell(bodyFont, tp.getComplete()?"":incompleteReqs.toString());
							c.setBgColor(evenRow?lightGrey:white);
							aRow.add(c);
							tableData.add(aRow);
							evenRow = !evenRow;
						}
					}
				}
				logger.info("done adding scouts to table");

				table.setData(tableData, Table.DATA_HAS_1_HEADER_ROWS);
				table.setColumnWidth(0, 100);
				table.setColumnWidth(1, 100);
				table.setColumnWidth(2, 40);
				table.setColumnWidth(3, 150);
				table.setColumnWidth(4, 45);
				table.setColumnWidth(5, 150);
				table.setColumnWidth(6, 150);
				table.wrapAroundCellText();

				logger.info("drawing table on page");
		        while (true) {
					logger.info("drawing table on page");
		            table.drawOn(page);
		            if (!table.hasMoreData()) break;
					page = createPage(pdf, unitFont, ci, event, unit);
					unitPageMap.get(unit.getKey()).add(page);
		        }
			}

			logger.info("getting date time of generation");
			TimeZone tz = TimeZone.getTimeZone("America/New_York");
			FastDateFormat formatter = FastDateFormat.getInstance("MM/dd/yyyy HH:mm:ss", tz);
			String nowStr = formatter.format(new Date());

			logger.info("numbering pages");
			for (List<Page> unitPageList:unitPageMap.values()) {
				int n = 1;
				int m = unitPageList.size();
				for (Page page:unitPageList) {
					TextLine text = new TextLine(footerFont, "Generated by CampScribe on "+nowStr);
					text.setPosition(36,590);
					text.drawOn(page);

					text = new TextLine(footerFont, "Page "+(n++)+" of "+m);
					text.setPosition(375,590);
					text.drawOn(page);
				}
			}
			logger.info("finished numbering pages");
			
			pdf.flush();
			ostream.close();
		} catch (Exception e) {
			logger.error("report generation failed", e);
		}

		logger.info("done generating report");
	}

	private Page createPage(PDF pdf, Font unitFont, CampInfo ci, Event event, Entry<Unit, TreeMap<Scout, ArrayList<TrackProgress>>> unit) throws Exception {
		Page page = new Page(pdf, Letter.LANDSCAPE);

		TextLine text = new TextLine(unitFont, ci.getCampName());
		text.setPosition(36,36);
		text.drawOn(page);

		text = new TextLine(unitFont, ci.getAddress());
		text.setPosition(36,51);
		text.drawOn(page);

		text = new TextLine(unitFont, ci.getCity()+", "+ci.getState()+" "+ci.getZip());
		text.setPosition(36,66);
		text.drawOn(page);

		text = new TextLine(unitFont, ci.getPhoneNbr());
		text.setPosition(36,81);
		text.drawOn(page);

		text = new TextLine(unitFont, unit.getKey().toString());
		text.setPosition(36,111);
		text.drawOn(page);

		text = new TextLine(unitFont, event.getDescription());
		text.setPosition(400,36);
		text.drawOn(page);

		text = new TextLine(unitFont, event.getStartDateDisplayStr()+" - "+event.getEndDateDisplayStr());
		text.setPosition(400,51);
		text.drawOn(page);

		return page;
	}

	private Map<Key<Clazz>, Clazz> getClazzLookup(Key<Event> eKey) {
		Map<Key<Clazz>, Clazz> staffLookup = new HashMap<Key<Clazz>, Clazz>();
		for(Clazz c:getEventManager().getClazzesByEvent(eKey)) {
			staffLookup.put(new Key<Clazz>(eKey, Clazz.class, c.getId()), c);
		}
		return staffLookup;
	}

	private CampInfoManager getCampInfoManager() {
		if (campInfoMgr == null) {
			campInfoMgr = new CampInfoManager();
		}
		return campInfoMgr;
	}

	private EventManager getEventManager() {
		if (eventMgr == null) {
			eventMgr = new EventManager();
		}
		return eventMgr;
	}

	private ScoutManager getScoutManager() {
		if (scoutMgr == null) {
			scoutMgr = new ScoutManager();
		}
		return scoutMgr;
	}

	private TrackProgressManager getTrackProgressManager() {
		if (tpMgr == null) {
			tpMgr = new TrackProgressManager();
		}
		return tpMgr;
	}

}
