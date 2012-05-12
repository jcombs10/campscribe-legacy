package com.campscribe.controller.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campscribe.business.MeritBadgeManager;
import com.campscribe.business.MeritBadgeMetadataManager;
import com.campscribe.model2.MeritBadgeMetadata;
import com.campscribe.model2.Staff;
import com.campscribe.shared.MeritBadgeMetadataDTO;
import com.googlecode.objectify.Key;

@Controller
public class MeritBadgeMetadataServiceController {

	//	@Autowired
	MeritBadgeMetadataManager mbMdMgr = new MeritBadgeMetadataManager();
	MeritBadgeManager mbMgr = new MeritBadgeManager();

	@RequestMapping(method=RequestMethod.GET, value = "/meritbadgemetadata/{id}",headers="Accept=application/json")
	public @ResponseBody MeritBadgeMetadataDTO getMeritBadgeMetadata(@PathVariable long id) {
		MeritBadgeMetadata mb = mbMdMgr.getMeritBadgeMetadata(new Key<MeritBadgeMetadata>(MeritBadgeMetadata.class, id));
		
		MeritBadgeMetadataDTO mbDTO = new MeritBadgeMetadataDTO();
		mbDTO.setId(mb.getId());
		mbDTO.setMbId(mb.getMbKey().getId());
		mbDTO.setMbName(mbMgr.getMeritBadge(mb.getMbKey().getId()).getBadgeName());
		mbDTO.setProgramArea(mb.getProgramArea());
		mbDTO.setStaffId(mb.getStaffKey().getId());
		return mbDTO;

	}

	@RequestMapping(method=RequestMethod.PUT, value = "/meritbadgemetadata/{id}",headers="Accept=application/json")
	public @ResponseBody MeritBadgeMetadataDTO updateMeritBadgeMetadata(@PathVariable long id, @RequestBody MeritBadgeMetadataDTO mbDTO) {
		MeritBadgeMetadata mb = mbMdMgr.getMeritBadgeMetadata(new Key<MeritBadgeMetadata>(MeritBadgeMetadata.class, id));
		mb.setId(mbDTO.getId());
		mb.setProgramArea(mbDTO.getProgramArea());
		mb.setStaffKey(new Key<Staff>(Staff.class, mbDTO.getStaffId()));
		mbMdMgr.updateMeritBadgeMetadata(mb);
		return mbDTO;
	}

}
