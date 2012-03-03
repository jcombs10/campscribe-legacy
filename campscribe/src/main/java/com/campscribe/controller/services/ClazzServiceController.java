package com.campscribe.controller.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campscribe.business.ClazzManager;
import com.campscribe.business.EventManager;
import com.campscribe.model.Clazz;
import com.campscribe.shared.ClazzDTO;

@Controller
public class ClazzServiceController {

//	@Autowired
	EventManager eventMgr = new EventManager();
//	@Autowired
	ClazzManager clazzMgr = new ClazzManager();

//	@RequestMapping(method=RequestMethod.GET, value = "/events/{id}/clazzes/",headers="Accept=application/json")
//	public @ResponseBody List<Clazz> getAllClazzesForEvent(@PathVariable Long id) {
//		List<Clazz> clazzes = clazzMgr.getClazzesForEvent(id);
//		return clazzes;
//	}
//
	@RequestMapping(method=RequestMethod.POST, value = "/events/{id}/classes/",headers="Accept=application/json")
	public @ResponseBody ClazzDTO addClazzToEvent(@PathVariable Long id, @RequestBody ClazzDTO clazzDTO) {
		System.err.println("addClazz called");
		Clazz c = new Clazz(clazzDTO.getDescription(), clazzDTO.getMbId());
		c.setStaffId(clazzDTO.getStaffId());
		
		eventMgr.addClazz(id, c);
		return clazzDTO;
	}

}
