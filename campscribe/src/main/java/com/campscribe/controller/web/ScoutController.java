package com.campscribe.controller.web;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.campscribe.business.ScoutManager;

@Controller
public class ScoutController {

	protected final Log logger = LogFactory.getLog(getClass());
	private ScoutManager mgr;

	@RequestMapping("/scouts.cs")
	public ModelAndView listScouts()
	            throws ServletException, IOException {

	        logger.info("Returning scouts view");

	        ModelAndView mav = new ModelAndView("scouts.jsp");
	        mav.addObject("scouts", getScoutManager().listScouts());
	        
	        return mav;
	    }

	@RequestMapping("/deleteScout.cs")
	public ModelAndView deleteScout(@RequestParam("id") long id)
	            throws ServletException, IOException {

	        logger.info("Returning scouts view");

	        getScoutManager().deleteScout(id);
	        
	        ModelAndView mav = new ModelAndView("scouts.jsp");
	        mav.addObject("scouts", getScoutManager().listScouts());
	        
	        return mav;
	    }

	private ScoutManager getScoutManager() {
		if (mgr == null) {
			mgr = new ScoutManager();
		}
		return mgr;
	}

}
