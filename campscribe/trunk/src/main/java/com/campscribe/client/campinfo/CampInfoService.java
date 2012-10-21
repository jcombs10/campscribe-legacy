package com.campscribe.client.campinfo;

import com.campscribe.shared.CampInfoDTO;
import com.google.gwt.http.client.RequestCallback;


public interface CampInfoService {

    public void getCampInfo(RequestCallback callback);

	public void updateStaff(CampInfoDTO data);
    
}
