package com.campscribe.controller.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.campscribe.model.Unit;
import com.campscribe.shared.ScoutDTO;
import com.campscribe.shared.UnitDTO;
import com.googlecode.objectify.Key;

@Controller
public class ScoutServiceController {

	// @Autowired
	ScoutManager scoutMgr = new ScoutManager();

	@RequestMapping(method = RequestMethod.GET, value = "/events/{eventId}/units", headers = "Accept=application/json")
	public @ResponseBody
	List<UnitDTO> getUnits(@PathVariable long eventId) {
		Set<Unit> units = scoutMgr.listUnits(new Key<Event>(Event.class, eventId));
		List<UnitDTO> unitDTOs = new ArrayList<UnitDTO>();
		for (Unit u:units) {
			UnitDTO uDTO = new UnitDTO(u.getUnitType(), u.getUnitNumber());
			unitDTOs.add(uDTO);
		}
		return unitDTOs;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/events/{eventId}/units/scouts", headers = "Accept=application/json")
	public @ResponseBody
	List<ScoutDTO> getScouts(@PathVariable long eventId) {
		List<Scout> scouts = scoutMgr.listScouts(new Key<Event>(Event.class, eventId));
		List<ScoutDTO> scoutDTOs = new ArrayList<ScoutDTO>();
		for (Scout s:scouts) {
			ScoutDTO sDTO = new ScoutDTO();
			sDTO.setId(s.getId());
			sDTO.setFirstName(s.getFirstName());
			sDTO.setLastName(s.getLastName());
			sDTO.setUnitType(s.getUnitType());
			sDTO.setUnitNumber(s.getUnitNumber());
			scoutDTOs.add(sDTO);
		}
		return scoutDTOs;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/events/{eventId}/units/{unit}/scouts", headers = "Accept=application/json")
	public @ResponseBody
	List<ScoutDTO> getScouts(@PathVariable long eventId, @PathVariable String unit) {
		List<Scout> scouts = scoutMgr.listScouts(new Key<Event>(Event.class, eventId));
		List<ScoutDTO> scoutDTOs = new ArrayList<ScoutDTO>();
		String[] unitParts = {};
		if (unit != null) {
			unitParts = unit.split(" ");
		}
		for (Scout s:scouts) {
			if (unitParts.length>1 && unitParts[0].equals(s.getUnitType()) && unitParts[1].equals(s.getUnitNumber())) {
				ScoutDTO sDTO = new ScoutDTO();
				sDTO.setId(s.getId());
				sDTO.setFirstName(s.getFirstName());
				sDTO.setLastName(s.getLastName());
				sDTO.setUnitType(s.getUnitType());
				sDTO.setUnitNumber(s.getUnitNumber());
				scoutDTOs.add(sDTO);
			}
		}
		return scoutDTOs;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/scouts/", headers = "Accept=application/json")
	public @ResponseBody
	List<ScoutDTO> getScouts(
			@RequestParam(value = "eventId", required = true) String eventId,
			@RequestParam(value = "unit", required = false) String unit) {
		String[] unitParts = unit.split(" ");
		List<Scout> scouts = scoutMgr.listScouts(new Key<Event>(Event.class, Long.parseLong(eventId)), null, unitParts.length>1?unitParts[0]:null, unitParts.length>1?unitParts[1]:null);
		List<ScoutDTO> scoutDTOs = new ArrayList<ScoutDTO>();
		for (Scout s:scouts) {
			ScoutDTO sDTO = new ScoutDTO();
			sDTO.setFirstName(s.getFirstName());
			sDTO.setLastName(s.getLastName());
			sDTO.setUnitType(s.getUnitType());
			sDTO.setUnitNumber(s.getUnitNumber());
			scoutDTOs.add(sDTO);
		}
		return scoutDTOs;
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
