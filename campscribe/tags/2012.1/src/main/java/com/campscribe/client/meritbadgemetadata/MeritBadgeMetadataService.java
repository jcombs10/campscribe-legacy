package com.campscribe.client.meritbadgemetadata;

import com.campscribe.shared.MeritBadgeMetadataDTO;
import com.google.gwt.http.client.RequestCallback;


public interface MeritBadgeMetadataService {

	void updateMeritBadgeMetadata(MeritBadgeMetadataDTO data);

	void getMeritBadgeMetadata(Long id, RequestCallback requestCallback);

}
