package com.campscribe.controller.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campscribe.business.MeritBadgeManager;
import com.campscribe.model.MeritBadge;
import com.campscribe.model.Requirement;
import com.campscribe.shared.MeritBadgeDTO;
import com.campscribe.shared.RequirementDTO;

@Controller
public class MeritBadgeServiceController {

//	@Autowired
	MeritBadgeManager mbMgr = new MeritBadgeManager();

	@RequestMapping(method=RequestMethod.GET, value = "/meritbadges/",headers="Accept=application/json")
	public @ResponseBody List<MeritBadgeDTO> getAllMeritBadges() {
		List<MeritBadgeDTO> badgeDTOs = new ArrayList<MeritBadgeDTO>();
		List<MeritBadge> badges =mbMgr.listMeritBadges();
		for (MeritBadge b:badges) {
			MeritBadgeDTO bDTO = new MeritBadgeDTO();
			bDTO.setId(b.getId());
			bDTO.setBadgeName(b.getBadgeName());
			bDTO.setBsaAdvancementId(b.getBsaAdvancementId());
			bDTO.setEagleRequired(b.isEagleRequired());
			badgeDTOs.add(bDTO);
		}
		return badgeDTOs;
	}

	@RequestMapping(method=RequestMethod.POST, value = "/meritbadges/",headers="Accept=application/json")
	public @ResponseBody MeritBadge addMeritBadge(@RequestBody MeritBadgeDTO mbDTO) {
		System.err.println("addMeritBadge called");
		MeritBadge mb = new MeritBadge(mbDTO.getBadgeName(), mbDTO.isEagleRequired());
		mb.setBsaAdvancementId(mbDTO.getBsaAdvancementId());
		ArrayList<Requirement> reqs = new ArrayList<Requirement>();
		for (RequirementDTO reqDTO:mbDTO.getRequirements()) {
			Requirement req = new Requirement();
			req.setReqType(reqDTO.getReqType());
			reqs.add(req);
		}
		mb.setRequirements(reqs);
		mbMgr.addMeritBadge(mb);
		return mb;
	}

	@RequestMapping(method=RequestMethod.GET, value = "/meritbadges/{id}",headers="Accept=application/json")
	public @ResponseBody MeritBadge getMeritBadge(@RequestParam long id) {
		return mbMgr.getMeritBadge(id);
	}

	@RequestMapping(method=RequestMethod.PUT, value = "/meritbadges/{id}",headers="Accept=application/json")
	public @ResponseBody MeritBadge updateMeritBadge(@RequestParam long id, @RequestBody MeritBadge mb) {
		mbMgr.updateMeritBadge(mb);
		return mb;
	}

	@RequestMapping(method=RequestMethod.DELETE, value = "/meritbadges/{id}",headers="Accept=application/json")
	public @ResponseBody void deleteMeritBadge(@RequestParam long id) {
		mbMgr.deleteMeritBadge(id);
	}

}
