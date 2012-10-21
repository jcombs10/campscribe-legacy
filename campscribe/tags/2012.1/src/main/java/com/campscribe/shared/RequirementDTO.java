package com.campscribe.shared;

import java.util.ArrayList;
import java.util.List;

public class RequirementDTO {
	public static String SIMPLE = "Simple";
	public static String N_OF_M = "Choose";
	
	private String reqType = SIMPLE;
	private int howManyToChoose = 0;
	private int optionCount = 0;
	private List<RequirementDTO> subRequirements = new ArrayList<RequirementDTO>();

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public int getHowManyToChoose() {
		return howManyToChoose;
	}

	public void setHowManyToChoose(int howManyToChoose) {
		this.howManyToChoose = howManyToChoose;
	}

	public int getOptionCount() {
		return optionCount;
	}

	public void setOptionCount(int optionCount) {
		this.optionCount = optionCount;
	}

	public List<RequirementDTO> getSubRequirements() {
		return subRequirements;
	}

	public void setSubRequirements(List<RequirementDTO> subRequirements) {
		this.subRequirements = subRequirements;
	}

}
