package com.campscribe.client.meritbadges;

import com.campscribe.shared.MeritBadgeDTO;
import com.google.gwt.http.client.RequestCallback;


public interface MeritBadgeService {

    public void addMeritBadge(MeritBadgeDTO mb);

	void deleteMeritBadge(String id);

    public void getMeritBadge(String id, RequestCallback cb);
    
	public void getMeritBadges(RequestCallback cb);
    
    public void updateMeritBadge(MeritBadgeDTO mb);

}
