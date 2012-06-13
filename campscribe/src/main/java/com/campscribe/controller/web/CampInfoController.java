package com.campscribe.controller.web;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.campscribe.business.CampInfoManager;
import com.campscribe.model.CampInfo;

@Controller
public class CampInfoController {

	protected final Log logger = LogFactory.getLog(getClass());
	private CampInfoManager mgr;

	@RequestMapping(value="/campInfo.cs", method=RequestMethod.GET)
	public ModelAndView getCampInfo() {
		System.out.println("getting campInfo");
		
		ModelAndView mav = new ModelAndView("campInfo.jsp");
		
		List<CampInfo> ciList = getCampInfoManager().listCampInfos();
		CampInfo ci = null;
		if (ciList!=null && ciList.size()>0) {
			ci = ciList.get(0);
		} else {
			ci = new CampInfo();
		}
		
		mav.addObject("campInfo", ci);
		
		System.out.println("returning mav");
		return mav;
	}

	private CampInfoManager getCampInfoManager() {
		if (mgr == null) {
			mgr = new CampInfoManager();
		}
		return mgr;
	}

}
