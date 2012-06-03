package com.campscribe.controller.services;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campscribe.business.ScoutManager;
import com.campscribe.model.Event;
import com.campscribe.model.Scout;
import com.campscribe.shared.ScoutDTO;
import com.googlecode.objectify.Key;

@Controller
public class ScoutServiceController {

	// @Autowired
	ScoutManager scoutMgr = new ScoutManager();

	@RequestMapping(method = RequestMethod.GET, value = "/scouts/", headers = "Accept=application/json")
	public @ResponseBody
	List<Scout> getScouts(
			@RequestParam(value = "eventId", required = true) String eventId,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "unitType", required = false) String unitType,
			@RequestParam(value = "unitNumber", required = false) String unitNumber) {
		List<Scout> scouts = scoutMgr.listScouts(new Key<Event>(Event.class, eventId), name, unitType, unitNumber);
		return scouts;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/scouts/", headers = "Accept=application/json")
	public @ResponseBody
	Scout addScout(@RequestBody ScoutDTO scoutDTO) {
		System.err.println("addScout called");
		Scout e = new Scout(scoutDTO.getFirstName(), scoutDTO.getLastName(),
				scoutDTO.getRank(), scoutDTO.getUnitType(),
				scoutDTO.getUnitNumber(), new Key<Event>(Event.class, scoutDTO.getEventId()));
		scoutMgr.addScout(e);
		return e;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/scouts/{id}", headers = "Accept=application/json")
	public void deleteScout(@PathVariable long id) {
		System.err.println("deleteScout called");
		scoutMgr.deleteScout(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/scouts/{id}", headers = "Accept=application/json")
	public @ResponseBody
	ScoutDTO getScout(@RequestParam long id) {
		Scout s = scoutMgr.getScout(id);
		return new ScoutDTO(s.getFirstName(), s.getLastName(), s.getRank(), s.getUnitType(), s.getUnitNumber(), s.getEventKey().getId());
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/scouts/{id}", headers = "Accept=application/json")
	public @ResponseBody
	ScoutDTO updateScout(@RequestParam long id, @RequestBody ScoutDTO scoutDTO) {
		Scout s = new Scout(scoutDTO.getFirstName(), scoutDTO.getLastName(),
				scoutDTO.getRank(), scoutDTO.getUnitType(),
				scoutDTO.getUnitNumber(), new Key<Event>(Event.class, scoutDTO.getEventId()));
		scoutMgr.updateScout(s);
		return scoutDTO;
	}

}
