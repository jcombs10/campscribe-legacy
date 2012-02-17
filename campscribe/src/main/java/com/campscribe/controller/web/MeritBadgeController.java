package com.campscribe.controller.web;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.campscribe.business.MeritBadgeManager;
import com.campscribe.model.MeritBadge;

@Controller
public class MeritBadgeController {

	protected final Log logger = LogFactory.getLog(getClass());
	private MeritBadgeManager mgr;

	@RequestMapping("/addMeritBadge.cs")
	public ModelAndView addMeritBadge(@RequestParam("badgeName") String badgeName, @RequestParam(value="eagleRequired", defaultValue="false") Boolean eagleRequired)
	            throws ServletException, IOException {

	        logger.info("Returning meritBadges view");

	        MeritBadge mb = new MeritBadge(badgeName, eagleRequired);
			getMeritBadgeManager().addMeritBadge(mb);
	        
	        ModelAndView mav = new ModelAndView("meritBadges.jsp");
	        mav.addObject("meritBadges", getMeritBadgeManager().listMeritBadges());
	        
	        return mav;
	    }

	@RequestMapping("/meritBadges.cs")
	public ModelAndView listMeritBadges()
	            throws ServletException, IOException {

	        logger.info("Returning meritBadges view");

	        ModelAndView mav = new ModelAndView("meritBadges.jsp");
	        mav.addObject("meritBadges", getMeritBadgeManager().listMeritBadges());
	        
	        return mav;
	    }

	@RequestMapping("/deleteMeritBadge.cs")
	public ModelAndView deleteMeritBadge(@RequestParam("id") long id)
	            throws ServletException, IOException {

	        logger.info("Returning meritBadges view");

	        getMeritBadgeManager().deleteMeritBadge(id);
	        
	        ModelAndView mav = new ModelAndView("meritBadges.jsp");
	        mav.addObject("meritBadges", getMeritBadgeManager().listMeritBadges());
	        
	        return mav;
	    }

	private MeritBadgeManager getMeritBadgeManager() {
		if (mgr == null) {
			mgr = new MeritBadgeManager();
		}
		return mgr;
	}

}
