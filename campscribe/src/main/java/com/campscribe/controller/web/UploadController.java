package com.campscribe.controller.web;

import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVReader;

import com.campscribe.business.ScoutManager;
import com.campscribe.model.UploadItem;
import com.campscribe.model2.Scout;


@Controller
@RequestMapping(value = "/upload.cs")
public class UploadController
{
	private ScoutManager scoutManager = new ScoutManager();
	
	@RequestMapping(method = RequestMethod.GET)
	public String getUploadForm(Model model)
	{
		model.addAttribute(new UploadItem());
		return "upload.jsp";
	}

    @RequestMapping(method = RequestMethod.POST)
    public String addComment(UploadItem commentForm) throws IOException {

            MultipartFile fileData = commentForm.getFileData();

            CSVReader reader = new CSVReader(new InputStreamReader(fileData.getInputStream()));
            String [] nextLine;
    		Scout lastScout = new Scout();
            while ((nextLine = reader.readNext()) != null) {
            	lastScout = processLine(nextLine, lastScout);
            }
            
            return "redirect:/admin.cs"; // redirect after processing!!!
    }

	private Scout processLine(String[] nextLine, Scout lastScout) {
        System.out.println(nextLine[0] + "    " + nextLine[1] + "    " + nextLine[2] + "    " + nextLine[3] + "    " + nextLine[4] + "    " + nextLine[5] + " etc...");
        if (nextLine.length > 0 && "Group (Registration)".equals(nextLine[0])) {
        	//header line, nothing to do...
        	return lastScout;
        } else {
        	String[] unitFields = nextLine[0].split(" ");
        	String unitType = unitFields[0];
        	String unitNumber = unitFields[1];
        	String lastName = nextLine[1];
        	String firstName = nextLine[2];
        	
        	if (firstName.equals(lastScout.getFirstName()) &&
        			lastName.equals(lastScout.getLastName()) &&
        			unitType.equals(lastScout.getUnitType()) &&
        			unitNumber.equals(lastScout.getUnitNumber())) {
        		//parse and add to class
            	return lastScout;
        	} else {
        		//create new scout
        		Scout newScout = new Scout(firstName, lastName, "", unitType, unitNumber);
        		scoutManager.addScout(newScout);
        		return scoutManager.getScout(firstName, lastName, unitType, unitNumber);
        	}
        	
        }
	}	
}