package com.campscribe.controller.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.campscribe.business.StaffManager;

@Controller
public class StaffController {

	protected final Log logger = LogFactory.getLog(getClass());
	private StaffManager mgr;

	@RequestMapping("/staff.cs")
	public ModelAndView listStaffs()
	            throws ServletException, IOException {

	        logger.info("Returning staff view");

	        ModelAndView mav = new ModelAndView("staff.jsp");
	        mav.addObject("staff", getStaffManager().listStaff());
	        mav.addObject("roleLookup", getRoleLookup());
	        
	        return mav;
	    }

	@RequestMapping("/deleteStaff.cs")
	public ModelAndView deleteStaff(@RequestParam("id") long id)
	            throws ServletException, IOException {

	        logger.info("Returning staff view");

	        getStaffManager().deleteStaff(id);

	        ModelAndView mav = new ModelAndView("staff.jsp");
	        mav.addObject("staff", getStaffManager().listStaff());
	        mav.addObject("roleLookup", getRoleLookup());
	        
	        return mav;
	    }

	private StaffManager getStaffManager() {
		if (mgr == null) {
			mgr = new StaffManager();
		}
		return mgr;
	}

	private Map<String, String> getRoleLookup() {
		Map<String, String> roleLookup = new HashMap<String, String>();
		roleLookup.put("counselor", "Counselor");
		roleLookup.put("area_director", "Area Director");
		roleLookup.put("camp_admin", "Camp Admin");
		return roleLookup;
	}

}
