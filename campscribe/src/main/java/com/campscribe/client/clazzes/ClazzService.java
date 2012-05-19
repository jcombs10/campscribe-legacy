package com.campscribe.client.clazzes;

import java.util.List;

import com.campscribe.shared.ClazzDTO;
import com.campscribe.shared.ScoutDTO;
import com.campscribe.shared.TrackProgressDTO;
import com.google.gwt.http.client.RequestCallback;


public interface ClazzService {

    public void addClazz(ClazzDTO c);
    
    public void addScoutToClazz(Long eventId, Long clazzId, List<ScoutDTO> s);
    
    public void getClazz(Long eventId, Long clazzId, RequestCallback callback);
    
    public void getClazzTracking(Long eventId, Long clazzId, RequestCallback callback);

	public void updateClazzTracking(Long eventId, Long clazzId, List<TrackProgressDTO> data);
    
}
