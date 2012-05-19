package com.campscribe.backend;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import au.com.bytecode.opencsv.CSVReader;

import com.campscribe.business.ClazzManager;
import com.campscribe.business.EventManager;
import com.campscribe.business.MeritBadgeManager;
import com.campscribe.business.MeritBadgeMetadataManager;
import com.campscribe.business.ScoutManager;
import com.campscribe.model.Clazz;
import com.campscribe.model.Event;
import com.campscribe.model.MeritBadge;
import com.campscribe.model.MeritBadgeMetadata;
import com.campscribe.model.Scout;
import com.googlecode.objectify.Key;

public class ProcessDoubleknotServlet extends HttpServlet {

	private static final long serialVersionUID = 3262454164072964415L;

	private static final Logger log = Logger.getLogger(ProcessDoubleknotServlet.class.getName());

	private ScoutManager scoutManager = new ScoutManager();
	private EventManager eventManager = new EventManager();
	private ClazzManager clazzManager = new ClazzManager();
	private MeritBadgeManager mbManager = new MeritBadgeManager();
	private MeritBadgeMetadataManager meritBadgeMetadataManager = new MeritBadgeMetadataManager();
	private Map<Key<MeritBadge>, MeritBadgeMetadata> mbMdMap;
	private String[] datePattern = {"MM/dd/yyyy"};

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String csvString = req.getParameter("csv");

		//first pass - look for scouts to add
		CSVReader reader = new CSVReader(new StringReader(csvString));
		String [] nextLine = {};
		Map<String, Key<Scout>> scoutIdMap = new HashMap<String, Key<Scout>>(); 
		while ((nextLine = reader.readNext()) != null) {
			log.info("processing line looking for scouts" + Arrays.toString(nextLine));
			//header or blank line found, skip to the next line
			if (nextLine.length==0) {
				log.warning("skipping blank line");
				continue;
			} else if ("Group (Registration)".equals(nextLine[0])) {
				log.fine("skipping header line");
				continue;
			} else if (nextLine.length>=5 && nextLine[4].startsWith("581 Ibold Rd")) {
				//every scout has a line with the camp address so we don't really need to look at other lines...
				String[] unitFields = nextLine[0].split("[ (]");
				String unitType = unitFields[0];
				String unitNumber = unitFields[1];
				String lastName = nextLine[1];
				String firstName = nextLine[2];
				
				String keyStr = firstName+","+lastName+","+unitType+","+unitNumber;
				
				if (!scoutIdMap.containsKey(keyStr)) {
					Key<Scout> s = processScout(firstName, lastName, unitType, unitNumber);
					if (s == null) {
						log.warning("could not find or create scout " + nextLine[1] + " " + nextLine[2]);
					} else {
						scoutIdMap.put(keyStr, s);
					}
				} else {
					log.fine("same scout as previous line " + nextLine[1] + " " + nextLine[2]);
				}
			}

		}
		
		//second pass - look for events to add
		reader = new CSVReader(new StringReader(csvString));
		Map<String, Key<Event>> eventIdMap = new HashMap<String, Key<Event>>(); 
		while ((nextLine = reader.readNext()) != null) {
			log.info("processing line looking for events" + Arrays.toString(nextLine));
			//header or blank line found, skip to the next line
			if (nextLine.length==0) {
				log.warning("skipping blank line");
				continue;
			} else if ("Group (Registration)".equals(nextLine[0])) {
				log.fine("skipping header line");
				continue;
			} else if (nextLine.length>=5 && nextLine[4].startsWith("581 Ibold Rd")) {
				//every event has a line with the camp address so we don't really need to look at other lines...
				if (!eventIdMap.containsKey(nextLine[3])) {
					Key<Event> e = processEvent(nextLine[3], nextLine[5], nextLine[6]);
					if (e == null) {
						log.warning("could not find or create event " + nextLine[3]);
					} else {
						eventIdMap.put(nextLine[3], e);
					}
				} else {
					log.fine("same event as previous line " + nextLine[3]);
				}
			}
		}

		//third pass - look for clazzes to add.  need to be aware of which event they go to
		reader = new CSVReader(new StringReader(csvString));
		Map<String, Key<Clazz>> clazzIdMap = new HashMap<String, Key<Clazz>>();
		Key<Event> lastEventSeen = null;
		mbMdMap = meritBadgeMetadataManager.getAll();
		while ((nextLine = reader.readNext()) != null) {
			log.info("processing line looking for clazzes" + Arrays.toString(nextLine));
			//header or blank line found, skip to the next line
			if (nextLine.length==0) {
				log.warning("skipping blank line");
				continue;
			} else if ("Group (Registration)".equals(nextLine[0])) {
				log.fine("skipping header line");
				continue;
			} else if (nextLine.length>=5 && nextLine[4].startsWith("581 Ibold Rd")) {
				lastEventSeen = eventIdMap.get(nextLine[3]);
			} else if (nextLine.length>=5 && !nextLine[4].startsWith("581 Ibold Rd")) {
				//handle some weirdness in the data coming from doubleknot
				String eventDesc = nextLine[3].replace(" (Pool)", "").replace("0 - ", "0-").replace("0- ", "0-");
				String clazzKeyStr = lastEventSeen.toString() + "," + eventDesc;
				
				if (!clazzIdMap.containsKey(clazzKeyStr)) {
					Key<Clazz> c = processClazz(lastEventSeen, eventDesc);
					if (c == null) {
						log.warning("could not find or create clazz " + eventDesc);
					} else {
						clazzIdMap.put(clazzKeyStr, c);
					}
				} else {
					log.fine("same clazz as previous line " + eventDesc);
				}
			}
		}
		
		//fourth pass - look for scouts to add to clazzes.  need to be aware of which event they go to
		reader = new CSVReader(new StringReader(csvString));
		while ((nextLine = reader.readNext()) != null) {
			log.info("processing line looking for scouts to add to clazzes" + Arrays.toString(nextLine));
			//header or blank line found, skip to the next line
			if (nextLine.length==0) {
				log.warning("skipping blank line");
				continue;
			} else if ("Group (Registration)".equals(nextLine[0])) {
				log.fine("skipping header line");
				continue;
			} else if (nextLine.length>=5 && nextLine[4].startsWith("581 Ibold Rd")) {
				lastEventSeen = eventIdMap.get(nextLine[3]);
			} else if (nextLine.length>=5 && !nextLine[4].startsWith("581 Ibold Rd")) {
				String[] unitFields = nextLine[0].split("[ (]");
				String unitType = unitFields[0];
				String unitNumber = unitFields[1];
				String lastName = nextLine[1];
				String firstName = nextLine[2];
				
				String scoutKeyStr = firstName+","+lastName+","+unitType+","+unitNumber;

				//handle some weirdness in the data coming from doubleknot
				String eventDesc = nextLine[3].replace(" (Pool)", "").replace("0 - ", "0-").replace("0- ", "0-");
				String clazzKeyStr = lastEventSeen.toString() + "," + eventDesc;
				
				if (clazzIdMap.containsKey(clazzKeyStr) && scoutIdMap.containsKey(scoutKeyStr)) {
					Key<Clazz> c = clazzIdMap.get(clazzKeyStr);
					Key<Scout> s = scoutIdMap.get(scoutKeyStr);

					List<Key<Scout>> scoutList = new ArrayList<Key<Scout>>();
					scoutList.add(new Key<Scout>(Scout.class, s.getId()));
					clazzManager.addScoutsToClazz(c, scoutList);
					log.info("added scout " + s.getId() + " to clazz " + c.getId());
				} else {
					log.warning("couldn't find clazz " + clazzKeyStr + " or scout " + scoutKeyStr + " in map ");
				}
			}
		}
		
		log.severe("finished backend processing");
		
	}

	private Key<Scout> processScout(String firstName, String lastName, String unitType, String unitNumber) {
		Scout aScout = scoutManager.getScout(firstName, lastName, unitType, unitNumber);
		Key<Scout> sKey = null;

		if (aScout == null) {
			//create new scout
			Scout newScout = new Scout(firstName, lastName, "", unitType, unitNumber);
			sKey = scoutManager.addScout(newScout);
			log.info("scout created with id " + sKey.getId());
		} else {
			log.info("scout found with id " + aScout.getId());
			sKey = new Key<Scout>(Scout.class, aScout.getId());
		}

		return sKey;
	}

	private Key<Event> processEvent(String eventName, String startDateStr, String endDateStr) {
		Key<Event> returnKey = null;
		Event anEvent = eventManager.getEvent(eventName);

		if (anEvent == null) {
			//create new event
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = DateUtils.parseDateStrictly(startDateStr, datePattern);
				startDate = DateUtils.addDays(startDate, 1);
			} catch (ParseException e) {
				log.log(Level.SEVERE, "start date parse failure", e);
			}
			try {
				endDate = DateUtils.parseDateStrictly(endDateStr, datePattern);
				endDate = DateUtils.addDays(endDate, -1);
			} catch (ParseException e) {
				log.log(Level.SEVERE, "end date parse failure", e);
			}
			Event newEvent = new Event(eventName, startDate, endDate);
			returnKey = eventManager.addEvent(newEvent);
			log.info("event created with id " + returnKey.getId());
		} else {
			log.info("event found with id " + anEvent.getId());
			returnKey = new Key<Event>(Event.class, anEvent.getId());
		}

		return returnKey;
	}

	private Key<Clazz> processClazz(Key<Event> e, String clazzStr) {
		Key<Clazz> returnKey = null;
		Clazz aClazz = null;
		String[] clazzFields = clazzStr.split(" ");

		if (clazzFields.length > 0) {
			String clazzDescription = clazzFields[clazzFields.length-1];
			List<String> mbNameParts = new ArrayList<String>();
			for (int i=0; i<clazzFields.length-1; i++) {
				mbNameParts.add(clazzFields[i]);
			}
			String mbName = StringUtils.join(mbNameParts, " ");
			log.info("searching for merit badge " + mbName);
			MeritBadge mb = mbManager.getByBadgeName(mbName);

			if (mb == null) {
				log.warning("couldn't find merit badge " + mbName);
			} else {
				aClazz = eventManager.getClazz(e, clazzDescription, new Key<MeritBadge>(MeritBadge.class, mb.getId()));
				if (aClazz == null) {
					Clazz newClazz = new Clazz(clazzDescription, new Key<MeritBadge>(MeritBadge.class, mb.getId()));
					newClazz.setMbName(mbName);
					newClazz.setProgramArea(mbMdMap.get(new Key<MeritBadge>(MeritBadge.class, mb.getId())).getProgramArea());
					newClazz.setStaffId(mbMdMap.get(new Key<MeritBadge>(MeritBadge.class, mb.getId())).getStaffKey());
					returnKey = eventManager.addClazz(e.getId(), newClazz);
					log.info("clazz created with id " + returnKey.getId());
				} else {
					log.info("clazz found with id " + aClazz.getId());
					returnKey = new Key<Clazz>(e, Clazz.class, aClazz.getId());
				}
			}
		} else {
			log.warning("couldn't figure out what the clazz description is");
		}

		return returnKey;
	}

}
