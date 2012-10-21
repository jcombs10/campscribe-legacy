package com.campscribe.controller.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(method=RequestMethod.POST, value = "/meritbadges/",headers="Accept=application/json")
	public @ResponseBody MeritBadgeDTO addMeritBadge(@RequestBody MeritBadgeDTO mbDTO) {
		System.err.println("addMeritBadge called");
		MeritBadge mb = new MeritBadge(mbDTO.getBadgeName(), mbDTO.isEagleRequired());
		mb.setBsaAdvancementId(mbDTO.getBsaAdvancementId());
		mb.setRequirementsStr(mbDTO.getRequirementsStr());
		mbMgr.addMeritBadge(mb);
		return mbDTO;
	}

	@RequestMapping(method=RequestMethod.DELETE, value = "/meritbadges/{id}",headers="Accept=application/json")
	public void deleteMeritBadge(@PathVariable long id) {
		System.err.println("deleteMeritBadge called");
		mbMgr.deleteMeritBadge(id);
	}

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
		List<Requirement> requirements = buildRequirementsList(mbDTO.getRequirements());
		mb.setRequirements(requirements);
		mbMgr.updateMeritBadge(mb);
		return mbDTO;
	}

	private List<Requirement> buildRequirementsList(
			List<RequirementDTO> requirementDTOs) {
		List<Requirement> requirements = new ArrayList<Requirement>();
		for (RequirementDTO reqDTO:requirementDTOs) {
			Requirement req = new Requirement();
			req.setHowManyToChoose(reqDTO.getHowManyToChoose());
			req.setOptionCount(reqDTO.getOptionCount());
			req.setReqType(reqDTO.getReqType());
			req.setSubRequirements(buildRequirementsList(reqDTO.getSubRequirements()));
			requirements.add(req);
		}
		return requirements;
	}

}
