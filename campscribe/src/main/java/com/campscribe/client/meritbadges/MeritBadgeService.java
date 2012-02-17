package com.campscribe.client.meritbadges;

import com.campscribe.shared.MeritBadgeDTO;
import com.google.gwt.http.client.RequestCallback;


public interface MeritBadgeService {

    public void addMeritBadge(MeritBadgeDTO mb);

	public void getMeritBadges(RequestCallback cb);
    
}
