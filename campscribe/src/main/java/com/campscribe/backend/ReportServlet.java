package com.campscribe.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.campscribe.business.EventManager;
import com.campscribe.business.ScoutManager;
import com.campscribe.business.TrackProgressManager;
import com.campscribe.model.Clazz;
import com.campscribe.model.Event;
import com.campscribe.model.EventUtil;
import com.campscribe.model.Scout;
import com.campscribe.model.ScoutComparator;
import com.campscribe.model.TrackProgress;
import com.campscribe.model.Unit;
import com.campscribe.model.UnitComparator;
import com.googlecode.objectify.Key;
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

	private static final Logger log = Logger.getLogger(ReportServlet.class.getName());

	private EventManager eventMgr;
	private ScoutManager scoutMgr;
	private TrackProgressManager tpMgr;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<Event> events = getEventManager().listEvents();

		Long eventId = EventUtil.findCurrentEventId(events);

		Set<Unit> unitSet = new TreeSet<Unit>(new UnitComparator());
		List<Scout> allScoutList = getScoutManager().getScoutsByEvent(new Key<Event>(Event.class, eventId));
		for (Scout s:allScoutList) {
			unitSet.add(new Unit(s.getUnitType(), s.getUnitNumber()));
		}

        Map<Key<Clazz>, Clazz> clazzLookup = getClazzLookup(new Key<Event>(Event.class, eventId));
        

        String fileName = "campscribe_unit_report.pdf"; 

		resp.setContentType("application/pdf"); 
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + 
				fileName + "\""); 

		OutputStream ostream = resp.getOutputStream();

		try {
			PDF pdf = new PDF(ostream);
			pdf.setTitle("CampScribe Unit Report");
			pdf.setSubject("CampScribe Unit Report");
			pdf.setAuthor("CampScribe Software");

	        Font unitFont = new Font(pdf, CoreFont.HELVETICA_BOLD);
	        unitFont.setSize(12.0);

	        Font headerFont = new Font(pdf, CoreFont.HELVETICA_BOLD);
	        headerFont.setSize(9.0);

	        Font bodyFont = new Font(pdf, CoreFont.HELVETICA);
	        bodyFont.setSize(9.0);

	        TreeMap<String, TreeMap<Scout,ArrayList<TrackProgress>>> scoutByUnitMap = new TreeMap<String, TreeMap<Scout,ArrayList<TrackProgress>>>();
			
			List<Scout> scoutList = null;
//			if (fbo.getUnit()==null || "ALL".equals(fbo.getUnit())) {
				scoutList = getScoutManager().getScoutsByEvent(new Key<Event>(Event.class, eventId));
//			} else {
//				String[] unitParts = fbo.getUnit().split(" ");
//				scoutList = getScoutManager().getScoutsByUnit(new Key<Event>(Event.class, eventId), unitParts[0], unitParts[1]);
//			}
			for (Scout s:scoutList) {
				String unit = s.getUnitType()+" "+s.getUnitNumber();
				if (!scoutByUnitMap.containsKey(unit)) {
					TreeMap<Scout,ArrayList<TrackProgress>> trackingMap = new TreeMap<Scout,ArrayList<TrackProgress>>(new ScoutComparator());
					scoutByUnitMap.put(unit, trackingMap);
				}

				List<TrackProgress> trackingList = getTrackProgressManager().getTrackingForScout(new Key<Scout>(Scout.class, s.getId()));
				for (TrackProgress tp:trackingList) {
					if (!scoutByUnitMap.get(unit).containsKey(s)) {
						ArrayList<TrackProgress> badgeList = new ArrayList<TrackProgress>();
						scoutByUnitMap.get(unit).put(s, badgeList);
					}
					scoutByUnitMap.get(unit).get(s).add(tp);
				}
			}

			for (Map.Entry<String, TreeMap<Scout,ArrayList<TrackProgress>>> unit:scoutByUnitMap.entrySet()) {
		        Page page = new Page(pdf, Letter.LANDSCAPE);

		        TextLine text = new TextLine(unitFont, unit.getKey());
		        text.setPosition(90, 30);
		        text.drawOn(page);

		        Table table = new Table();
		        table.setLineWidth(0.2);
		        table.setPosition(140, 30);
		        List<List<Cell>> tableData = new ArrayList<List<Cell>>();
		        
		        List<Cell> headerRow = new ArrayList<Cell>();
		        headerRow.add(new Cell(headerFont, "Scout Name"));
		        headerRow.add(new Cell(headerFont, "Merit Badge"));
		        headerRow.add(new Cell(headerFont, "Status"));
		        headerRow.add(new Cell(headerFont, "Completed Requirements"));
		        headerRow.add(new Cell(headerFont, "Incomplete Requirements"));
		        tableData.add(headerRow);
		        
		        for (Map.Entry<Scout,ArrayList<TrackProgress>> scout:unit.getValue().entrySet()) {
			        for (TrackProgress tp:scout.getValue()) {
				        List<Cell> aRow = new ArrayList<Cell>();
				        aRow.add(new Cell(bodyFont, scout.getKey().getDisplayName()));
				        aRow.add(new Cell(bodyFont, clazzLookup.get(tp.getClazzKey()).getMbName()));
				        aRow.add(new Cell(bodyFont, tp.getComplete()?"Complete":"Partial"));
				        aRow.add(new Cell(bodyFont, "Completed Requirements"));
				        aRow.add(new Cell(bodyFont, "Incomplete Requirements"));
				        tableData.add(aRow);
			        }
		        }
		        
		        table.setData(tableData, Table.DATA_HAS_1_HEADER_ROWS);
		        table.drawOn(page);
			}

//	        Table table = new Table();
//	        List<List<Cell>> tableData = getData(Table.DATA_HAS_2_HEADER_ROWS, f1, f2);
//	        table.setData(tableData, Table.DATA_HAS_2_HEADER_ROWS);
//	        
//	        table.setTextColorInRow(6, RGB.BLUE);
//	        table.setTextColorInRow(39, RGB.RED);
//	        table.setTextFontInRow(26, f3, 7);
//	        table.removeLineBetweenRows(0, 1);  
//	        table.autoAdjustColumnWidths();
//	        table.setColumnWidth(0, 120);
//	        table.rightAlignNumbers();
//	        int numOfPages = table.getNumberOfPages(page);
//	        while (true) {
//	            Point point = table.drawOn(page);
//	            // System.out.println(table.getRowsRendered());
//	            // System.out.println(point.getX() + " " + point.getY());
//	            // TO DO: Draw "Page 1 of N" here
//	            if (!table.hasMoreData()) break;
//	            page = new Page(pdf, Letter.PORTRAIT);
//	        }

			pdf.flush();
			ostream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//    private void appendMissingCells(List<List<Cell>> tableData, Font f2) {
//        List<Cell> firstRow = tableData.get(0);
//        int numOfColumns = firstRow.size();
//        for (int i = 0; i < tableData.size(); i++) {
//            List<Cell> dataRow = tableData.get(i);
//            int dataRowColumns = dataRow.size();
//            if (dataRowColumns < numOfColumns) {
//                for (int j = 0; j < (numOfColumns - dataRowColumns); j++) {
//                    dataRow.add(new Cell(f2));
//                }
//                dataRow.get(dataRowColumns - 1).setColSpan((numOfColumns - dataRowColumns) + 1);
//            }
//        }
//    }
//
	private Map<Key<Clazz>, Clazz> getClazzLookup(Key<Event> eKey) {
		Map<Key<Clazz>, Clazz> staffLookup = new HashMap<Key<Clazz>, Clazz>();
		for(Clazz c:getEventManager().getClazzesByEvent(eKey)) {
			staffLookup.put(new Key<Clazz>(eKey, Clazz.class, c.getId()), c);
		}
		return staffLookup;
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
