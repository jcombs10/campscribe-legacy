package com.campscribe.controller.web;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.campscribe.business.ClazzManager;
import com.campscribe.business.EventManager;
import com.campscribe.business.MeritBadgeManager;

@Controller
public class ClazzController {

	protected final Log logger = LogFactory.getLog(getClass());
	private ClazzManager clazzMgr;
	private MeritBadgeManager mbMgr;
	private EventManager eventMgr;

	@RequestMapping("/deleteClazz.cs")
	public ModelAndView deleteClazz(@RequestParam("id") long id)
	            throws ServletException, IOException {

	        logger.info("Returning clazzes view");

	        getClazzManager().deleteClazz(id);
	        
	        ModelAndView mav = new ModelAndView("clazzes.jsp");
	        mav.addObject("clazzes", getClazzManager().listClazzes());
	        mav.addObject("meritBadges", getMeritBadgeManager().listMeritBadges());
	        
	        return mav;
	    }

	private ClazzManager getClazzManager() {
		if (clazzMgr == null) {
			clazzMgr = new ClazzManager();
		}
		return clazzMgr;
	}

	private MeritBadgeManager getMeritBadgeManager() {
		if (mbMgr == null) {
			mbMgr = new MeritBadgeManager();
		}
		return mbMgr;
	}

}
