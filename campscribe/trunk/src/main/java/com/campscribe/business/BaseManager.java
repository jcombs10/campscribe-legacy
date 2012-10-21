package com.campscribe.business;

import com.campscribe.model.CampInfo;
import com.campscribe.model.Clazz;
import com.campscribe.model.Event;
import com.campscribe.model.ImportedFile;
import com.campscribe.model.MeritBadge;
import com.campscribe.model.MeritBadgeMetadata;
import com.campscribe.model.Scout;
import com.campscribe.model.Staff;
import com.campscribe.model.TrackProgress;
import com.googlecode.objectify.ObjectifyService;

public class BaseManager {

	static {
		ObjectifyService.register(CampInfo.class);
		ObjectifyService.register(Clazz.class);
		ObjectifyService.register(Event.class);
		ObjectifyService.register(ImportedFile.class);
		ObjectifyService.register(MeritBadge.class);
		ObjectifyService.register(MeritBadgeMetadata.class);
		ObjectifyService.register(Scout.class);
		ObjectifyService.register(Staff.class);
		ObjectifyService.register(TrackProgress.class);
	}
	
}
