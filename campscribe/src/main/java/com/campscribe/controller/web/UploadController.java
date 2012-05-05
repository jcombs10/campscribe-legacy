package com.campscribe.controller.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


@Controller
@RequestMapping(value = "/upload.cs")
public class UploadController
{
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
            Scout lastScout = null;
        	Event e = null;
            while ((nextLine = reader.readNext()) != null) {
            	Scout s = processLine(nextLine);
            	if (s == null) {
            		//header line found, skip to the next line
            		continue;
            	}
            	
            	if (lastScout==null || !s.getId().equals(lastScout.getId())) {
            		e = processEvent(nextLine[3], nextLine[5], nextLine[6]);
            	} else {
                	if (e != null) {
                		Clazz c = processClazz(e, nextLine[3]);
                		if (c != null) {
                			List<Long> scoutList = new ArrayList<Long>();
                			scoutList.add(s.getId());
							clazzManager.addScoutsToClazz(e.getId(), c.getId(), scoutList);
                		}
                	}
            	}
            	
            	lastScout = s;
            }
            
            return "redirect:/admin.cs"; // redirect after processing!!!
    }

	private Scout processLine(String[] nextLine) {
        System.out.println(nextLine[0] + "    " + nextLine[1] + "    " + nextLine[2] + "    " + nextLine[3] + "    " + nextLine[4] + "    " + nextLine[5] + " etc...");
        if (nextLine.length > 0 && "Group (Registration)".equals(nextLine[0])) {
        	//header line, nothing to do...
        	return null;
        } else {
        	String[] unitFields = nextLine[0].split(" ");
        	String unitType = unitFields[0];
        	String unitNumber = unitFields[1];
        	String lastName = nextLine[1];
        	String firstName = nextLine[2];
        	Scout s = processScout(firstName, lastName, unitType, unitNumber);
        	
        	return s;
        }
	}

	private Scout processScout(String firstName, String lastName,
			String unitType, String unitNumber) {
		Scout aScout = scoutManager.getScout(firstName, lastName, unitType, unitNumber);
		
    	if (aScout == null) {
    		//create new scout
    		Scout newScout = new Scout(firstName, lastName, "", unitType, unitNumber);
    		scoutManager.addScout(newScout);
    		//do a get so that we have the generated key
    		aScout =  scoutManager.getScout(firstName, lastName, unitType, unitNumber);
    	}
    	
    	return aScout;
	}	

	private Event processEvent(String eventName, String startDateStr, String endDateStr) {
		Event anEvent = eventManager.getEvent(eventName);
		
    	if (anEvent == null) {
    		//create new event
    		Date startDate = null;
    		Date endDate = null;
			try {
				startDate = DateUtils.parseDateStrictly(startDateStr, datePattern);
				startDate = DateUtils.addDays(startDate, 1);
	    		endDate = DateUtils.parseDateStrictly(endDateStr, datePattern);
				endDate = DateUtils.addDays(endDate, -1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		Event newEvent = new Event(eventName, startDate, endDate);
    		eventManager.addEvent(newEvent);
    		//do a get so that we have the generated key
    		anEvent =  eventManager.getEvent(eventName);
    	}
    	
    	return anEvent;
	}

	private Clazz processClazz(Event e, String clazzStr) {
		Clazz aClazz = null;
		String[] clazzFields = clazzStr.split(" ");
		
		if (clazzFields.length > 0) {
			String clazzDescription = clazzFields[clazzFields.length-1];
			List<String> mbNameParts = new ArrayList<String>();
			for (int i=0; i<clazzFields.length-1; i++) {
				mbNameParts.add(clazzFields[i]);
			}
			String mbName = StringUtils.join(mbNameParts, " ");
			MeritBadge mb = mbManager.getMeritBadge(mbName);
			
			if (mb != null) {
				aClazz = eventManager.getClazz(e, clazzDescription, mb.getId());
				if (aClazz == null) {
					aClazz = new Clazz(clazzDescription, mb.getId());
					eventManager.addClazz(e.getId(), aClazz);
				}
			}
		}
		
		return aClazz;
	}

}