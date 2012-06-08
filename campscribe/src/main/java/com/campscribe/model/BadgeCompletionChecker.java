package com.campscribe.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.campscribe.model.TrackProgress.RequirementCompletion;

public class BadgeCompletionChecker {

	private static final char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

	public static boolean isComplete(List<Requirement> requirementsFromBadge, List<RequirementCompletion> requirementCompletionList) {
		Map<String, Boolean> completionMap = new HashMap<String, Boolean>();
		for (RequirementCompletion rc:requirementCompletionList) {
			completionMap.put(rc.getReqNumber(), rc.isCompleted());
		}
		return (countCompleted(requirementsFromBadge, completionMap, "", 0) >= requirementsFromBadge.size());
	}

	private static int countCompleted(List<Requirement> requirementsFromBadge, Map<String, Boolean> requirementCompletionMap, String parentReqStr, int level) {
		int completeCount = 0;
		int i = 1;
		for (Requirement r:requirementsFromBadge) {
			String reqStr = "";
			if (level == 0) {
				reqStr = i+"";
			} else if (level == 1) {
				reqStr = parentReqStr+"."+chars[i-1];
			} else if (level == 2) {
				reqStr = parentReqStr+"."+i;
			}
			
			if (r.getReqType().equals(Requirement.SIMPLE)) {
				if (requirementCompletionMap.get(reqStr)) {
					completeCount++;
				}
			} else {
				int subReqComplete = countCompleted(r.getSubRequirements(), requirementCompletionMap, reqStr, level+1);
				if (subReqComplete >= r.getHowManyToChoose()) {
					completeCount++;
				}
			}
			i++;
		}
		return completeCount;
	}

}
