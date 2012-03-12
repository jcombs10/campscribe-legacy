package com.campscribe.client.clazzes;

import java.util.List;

import com.campscribe.shared.ClazzDTO;
import com.campscribe.shared.ScoutDTO;


public interface ClazzService {

    public void addClazz(ClazzDTO c);
    
    public void addScoutToClazz(Long eventId, Long clazzId, List<ScoutDTO> s);
    
}
