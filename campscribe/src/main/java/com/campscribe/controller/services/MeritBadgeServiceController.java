package com.campscribe.controller.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campscribe.business.MeritBadgeManager;
import com.campscribe.model2.MeritBadge;
import com.campscribe.shared.MeritBadgeDTO;

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
	public @ResponseBody MeritBadgeDTO addMeritBadge(@RequestBody MeritBadgeDTO mbDTO) {
		System.err.println("addMeritBadge called");
		MeritBadge mb = new MeritBadge(mbDTO.getBadgeName(), mbDTO.isEagleRequired());
		mb.setBsaAdvancementId(mbDTO.getBsaAdvancementId());
		mb.setRequirementsStr(mbDTO.getRequirementsStr());
		mbMgr.addMeritBadge(mb);
		return mbDTO;
	}

	@RequestMapping(method=RequestMethod.GET, value = "/meritbadges/{id}",headers="Accept=application/json")
	public @ResponseBody MeritBadgeDTO getMeritBadge(@PathVariable long id) {
		MeritBadge mb = mbMgr.getMeritBadge(id);
		
		MeritBadgeDTO mbDTO = new MeritBadgeDTO();
		mbDTO.setId(mb.getId());
		mbDTO.setBadgeName(mb.getBadgeName());
		mbDTO.setEagleRequired(mb.isEagleRequired());
		mbDTO.setBsaAdvancementId(mb.getBsaAdvancementId());
		mbDTO.setRequirementsStr(mb.getRequirementsStr());
		return mbDTO;

	}

	@RequestMapping(method=RequestMethod.PUT, value = "/meritbadges/{id}",headers="Accept=application/json")
	public @ResponseBody MeritBadgeDTO updateMeritBadge(@PathVariable long id, @RequestBody MeritBadgeDTO mbDTO) {
		MeritBadge mb = new MeritBadge(mbDTO.getBadgeName(), mbDTO.isEagleRequired());
		mb.setId(mbDTO.getId());
		mb.setBsaAdvancementId(mbDTO.getBsaAdvancementId());
		mb.setRequirementsStr(mbDTO.getRequirementsStr());
		mbMgr.updateMeritBadge(mb);
		return mbDTO;
	}

	@RequestMapping(method=RequestMethod.DELETE, value = "/meritbadges/{id}",headers="Accept=application/json")
	public @ResponseBody void deleteMeritBadge(@RequestParam long id) {
		mbMgr.deleteMeritBadge(id);
	}

}
