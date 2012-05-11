package com.campscribe.controller.web;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.campscribe.business.MeritBadgeManager;
import com.campscribe.business.MeritBadgeMetadataManager;
import com.campscribe.business.StaffManager;

@Controller
public class MeritBadgeMetadataController {

	protected final Log logger = LogFactory.getLog(getClass());
	private MeritBadgeMetadataManager mgr;
	private MeritBadgeManager mbMgr;
	private StaffManager staffMgr;

	@RequestMapping("/meritBadgeMetadata.cs")
	public ModelAndView listMeritBadges()
	            throws ServletException, IOException {

	        logger.info("Returning meritBadges view");

	        ModelAndView mav = new ModelAndView("meritBadgeMetadata.jsp");
	        mav.addObject("meritBadgeLookup", getMeritBadgeManager().getLookup());
	        mav.addObject("staffLookup", getStaffManager().getLookup());
	        mav.addObject("meritBadgeMetadata", getMeritBadgeMetadataManager().list());
	        
	        return mav;
	    }

	private MeritBadgeMetadataManager getMeritBadgeMetadataManager() {
		if (mgr == null) {
			mgr = new MeritBadgeMetadataManager();
		}
		return mgr;
	}

	private MeritBadgeManager getMeritBadgeManager() {
		if (mbMgr == null) {
			mbMgr = new MeritBadgeManager();
		}
		return mbMgr;
	}

	private StaffManager getStaffManager() {
		if (staffMgr == null) {
			staffMgr = new StaffManager();
		}
		return staffMgr;
	}

}
