package com.campscribe.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Requirement implements Serializable {
	private static final long serialVersionUID = 1437339843977370688L;
	
	public static String SIMPLE = "Simple";
	public static String N_OF_M = "Choose";
	
	private String reqType = SIMPLE;
	private int howManyToChoose = 0;
	private int optionCount = 0;
	private List<Requirement> subRequirements = new ArrayList<Requirement>();

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

	public List<Requirement> getSubRequirements() {
		return subRequirements;
	}

	public void setSubRequirements(List<Requirement> subRequirements) {
		this.subRequirements = subRequirements;
	}

}
