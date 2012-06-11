package com.campscribe.controller.services;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campscribe.business.CampInfoManager;
import com.campscribe.model.CampInfo;
import com.campscribe.shared.CampInfoDTO;

@Controller
public class CampInfoServiceController {

//	@Autowired
	CampInfoManager ciMgr = new CampInfoManager();

	@RequestMapping(method=RequestMethod.GET, value = "/campInfo/",headers="Accept=application/json")
	public @ResponseBody CampInfoDTO getCampInfo() {
		List<CampInfo> ciList = ciMgr.listCampInfos();
		CampInfoDTO ci = new CampInfoDTO();
		if (ciList!=null && ciList.size()>0) {
			ci.setId(ciList.get(0).getId());
			ci.setCampName(ciList.get(0).getCampName());
			ci.setAddress(ciList.get(0).getAddress());
			ci.setCity(ciList.get(0).getCity());
			ci.setState(ciList.get(0).getState());
			ci.setZip(ciList.get(0).getZip());
			ci.setMeritBadgeSigner(ciList.get(0).getMeritBadgeSigner());
		}
		return ci;
	}

	@RequestMapping(method=RequestMethod.PUT, value = "/campInfo/",headers="Accept=application/json")
	public @ResponseBody CampInfoDTO updateStaff(@RequestBody CampInfoDTO ciDTO) {
		CampInfo ci = new CampInfo();
		ci.setId(ciDTO.getId());
		ci.setCampName(ciDTO.getCampName());
		ci.setAddress(ciDTO.getAddress());
		ci.setCity(ciDTO.getCity());
		ci.setState(ciDTO.getState());
		ci.setZip(ciDTO.getZip());
		ci.setMeritBadgeSigner(ciDTO.getMeritBadgeSigner());
		ciMgr.updateCampInfo(ci);
		return ciDTO;
	}

}
