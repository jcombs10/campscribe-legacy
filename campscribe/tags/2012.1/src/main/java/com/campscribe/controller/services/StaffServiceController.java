package com.campscribe.controller.services;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campscribe.business.StaffManager;
import com.campscribe.model.Staff;
import com.campscribe.shared.StaffDTO;

@Controller
public class StaffServiceController {

//	@Autowired
	StaffManager staffMgr = new StaffManager();

	@RequestMapping(method=RequestMethod.POST, value = "/staff/",headers="Accept=application/json")
	public @ResponseBody StaffDTO addStaff(@RequestBody StaffDTO staffDTO) {
		System.err.println("addStaff called");
		Staff s = new Staff(staffDTO.getName(), staffDTO.getUserId(), staffDTO.getPassword(), staffDTO.getRoles(), staffDTO.getProgramArea());
		s.setEmailAddress(staffDTO.getEmailAddress());
		//TODO - get key
		staffMgr.addStaff(s);
		return staffDTO;
	}

	@RequestMapping(method=RequestMethod.DELETE, value = "/staff/{id}",headers="Accept=application/json")
	public void deleteStaff(@PathVariable long id) {
		System.err.println("deleteStaff called");
		staffMgr.deleteStaff(id);
	}

	@RequestMapping(method=RequestMethod.GET, value = "/staff/",headers="Accept=application/json")
	public @ResponseBody List<Staff> getAllStaff() {
		List<Staff> staff = staffMgr.listStaff();
		return staff;
	}

	@RequestMapping(method=RequestMethod.GET, value = "/staff/{id}",headers="Accept=application/json")
	public @ResponseBody Staff getStaff(@PathVariable long id) {
		return staffMgr.getStaff(id);
	}

	@RequestMapping(method=RequestMethod.PUT, value = "/staff/{id}",headers="Accept=application/json")
	public @ResponseBody Staff updateStaff(@PathVariable long id, @RequestBody Staff e) {
		staffMgr.updateStaff(e);
		return e;
	}

}
