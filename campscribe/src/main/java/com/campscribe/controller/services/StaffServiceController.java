package com.campscribe.controller.services;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campscribe.business.StaffManager;
import com.campscribe.model.Staff;
import com.campscribe.shared.StaffDTO;

@Controller
public class StaffServiceController {

//	@Autowired
	StaffManager staffMgr = new StaffManager();

	@RequestMapping(method=RequestMethod.GET, value = "/staff/",headers="Accept=application/json")
	public @ResponseBody List<Staff> getAllStaff() {
		List<Staff> staff = staffMgr.listStaff();
		return staff;
	}

	@RequestMapping(method=RequestMethod.POST, value = "/staff/",headers="Accept=application/json")
	public @ResponseBody Staff addStaff(@RequestBody StaffDTO staffDTO) {
		System.err.println("addStaff called");
		Staff e = new Staff(staffDTO.getName(), staffDTO.getUserId(), staffDTO.getRoles(), staffDTO.getProgramArea());
		staffMgr.addStaff(e);
		return e;
	}

	@RequestMapping(method=RequestMethod.GET, value = "/staff/{id}",headers="Accept=application/json")
	public @ResponseBody Staff getStaff(@RequestParam long id) {
		return staffMgr.getStaff(id);
	}

	@RequestMapping(method=RequestMethod.PUT, value = "/staff/{id}",headers="Accept=application/json")
	public @ResponseBody Staff updateStaff(@RequestParam long id, @RequestBody Staff e) {
		staffMgr.updateStaff(e);
		return e;
	}

	@RequestMapping(method=RequestMethod.DELETE, value = "/staff/{id}",headers="Accept=application/json")
	public @ResponseBody void deleteStaff(@RequestParam long id) {
		staffMgr.deleteStaff(id);
	}

}
