package com.campscribe.controller.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVReader;

import com.campscribe.business.ClazzManager;
import com.campscribe.business.EventManager;
import com.campscribe.business.MeritBadgeManager;
import com.campscribe.business.ScoutManager;
import com.campscribe.model.UploadItem;
import com.campscribe.model2.Clazz;
import com.campscribe.model2.Event;
import com.campscribe.model2.MeritBadge;
import com.campscribe.model2.Scout;
import com.googlecode.objectify.Key;


@Controller
@RequestMapping(value = "/upload.cs")
public class UploadController
{
	private static final Logger log = Logger.getLogger(UploadController.class.getName());

	private ScoutManager scoutManager = new ScoutManager();
	private EventManager eventManager = new EventManager();
	private ClazzManager clazzManager = new ClazzManager();
	private MeritBadgeManager mbManager = new MeritBadgeManager();
	private String[] datePattern = {"MM/dd/yyyy"};

	@RequestMapping(method = RequestMethod.GET)
	public String getUploadForm(Model model)
	{
		model.addAttribute(new UploadItem());
		return "upload.jsp";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String uploadClassRegistration(UploadItem commentForm) throws IOException {

		MultipartFile fileData = commentForm.getFileData();

		CSVReader reader = new CSVReader(new InputStreamReader(fileData.getInputStream()));
		String [] nextLine;
		Key<Scout> lastScout = null;
		Key<Event> lastEvent = null;
		while ((nextLine = reader.readNext()) != null) {
			log.info("processing line " + Arrays.toString(nextLine));
			//header or blank line found, skip to the next line
			if (nextLine.length==0) {
				log.warning("skipping blank line");
				continue;
			} else if ("Group (Registration)".equals(nextLine[0])) {
				log.info("skipping header line");
				continue;
			}

			Key<Scout> s = processScout(nextLine);
			if (s == null) {
				log.warning("could not find or create scout " + nextLine[1] + " " + nextLine[2]);
				continue;
			} else {
				Key<Event> e = lastEvent;
				if (lastScout==null || s.getId()!=lastScout.getId()) {
					log.info("detected change of scout, checking for new event");
					e = processEvent(nextLine[3], nextLine[5], nextLine[6]);
				} else {
					log.info("same scout as last line, checking for new clazz");
					if (e == null) {
						log.warning("could not find or create event " + nextLine[3]);
						continue;
					} else {
						Key<Clazz> c = processClazz(e, nextLine[3]);
						if (c == null) {
							log.warning("could not find or create clazz " + nextLine[3]);
							continue;
						} else {
							List<Long> scoutList = new ArrayList<Long>();
							scoutList.add(s.getId());
							clazzManager.addScoutsToClazz(c, scoutList);
							log.info("added scout " + s.getId() + " to clazz " + c.getId());
						}
					}
				}

				lastScout = s;
				lastEvent = e;
			}

		}

		return "redirect:/admin.cs"; // redirect after processing!!!
	}

	private Key<Scout> processScout(String[] nextLine) {
		String[] unitFields = nextLine[0].split("[ (]");
		String unitType = unitFields[0];
		String unitNumber = unitFields[1];
		String lastName = nextLine[1];
		String firstName = nextLine[2];
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
			MeritBadge mb = mbManager.getMeritBadge(mbName);

			if (mb == null) {
				log.warning("couldn't find merit badge " + mbName);
			} else {
				aClazz = eventManager.getClazz(e, clazzDescription, mb.getId());
				if (aClazz == null) {
					Clazz newClazz = new Clazz(clazzDescription, mb.getId());
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