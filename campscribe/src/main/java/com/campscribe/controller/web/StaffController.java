package com.campscribe.controller.web;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	        
	        return mav;
	    }

	private StaffManager getStaffManager() {
		if (mgr == null) {
			mgr = new StaffManager();
		}
		return mgr;
	}

}
