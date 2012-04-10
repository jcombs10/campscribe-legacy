package com.campscribe.controller.web;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TrackingController {

	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping("/tracking")
	public ModelAndView index()
	            throws ServletException, IOException {

	        logger.info("Returning tracking view");

	        return new ModelAndView("tracking.jsp");
	    }

}
