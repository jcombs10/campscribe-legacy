package com.campscribe.controller.services;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campscribe.business.ScoutManager;
import com.campscribe.model.Scout;
import com.campscribe.shared.ScoutDTO;

@Controller
public class ScoutServiceController {

//	@Autowired
	ScoutManager scoutMgr = new ScoutManager();

	@RequestMapping(method=RequestMethod.GET, value = "/scouts/",headers="Accept=application/json")
	public @ResponseBody List<Scout> getAllScouts() {
		List<Scout> scouts = scoutMgr.listScouts();
		return scouts;
	}

	@RequestMapping(method=RequestMethod.POST, value = "/scouts/",headers="Accept=application/json")
	public @ResponseBody Scout addScout(@RequestBody ScoutDTO scoutDTO) {
		System.err.println("addScout called");
		Scout e = new Scout(scoutDTO.getFirstName(), scoutDTO.getLastName(), scoutDTO.getRank(), scoutDTO.getUnitType(), scoutDTO.getUnitNumber());
		scoutMgr.addScout(e);
		return e;
	}

	@RequestMapping(method=RequestMethod.GET, value = "/scouts/{id}",headers="Accept=application/json")
	public @ResponseBody Scout getScout(@RequestParam long id) {
		return scoutMgr.getScout(id);
	}

	@RequestMapping(method=RequestMethod.PUT, value = "/scouts/{id}",headers="Accept=application/json")
	public @ResponseBody Scout updateScout(@RequestParam long id, @RequestBody Scout e) {
		scoutMgr.updateScout(e);
		return e;
	}

	@RequestMapping(method=RequestMethod.DELETE, value = "/scouts/{id}",headers="Accept=application/json")
	public @ResponseBody void deleteScout(@RequestParam long id) {
		scoutMgr.deleteScout(id);
	}

}
