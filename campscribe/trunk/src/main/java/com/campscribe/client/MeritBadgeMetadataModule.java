package com.campscribe.client;

import com.campscribe.client.meritbadgemetadata.EditMeritBadgeMetadataView;
import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MeritBadgeMetadataModule implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		addGWTActionTriggers(this);
	}

	private native void addGWTActionTriggers(MeritBadgeMetadataModule module)/*-{
        $wnd.MeritBadgeMetadataGWT = {
            editMeritBadgeMetadata: function(id) {
                module.@com.campscribe.client.MeritBadgeMetadataModule::editMeritBadgeMetadata(Ljava/lang/Long;)(id);
            }
        };
    }-*/;

	public void editMeritBadgeMetadata(Long id) {
		final CampScribeDialogBox dialogBox = new CampScribeDialogBox("Edit Class Defaults", new EditMeritBadgeMetadataView(id));
		dialogBox.setAnimationEnabled(true);
		dialogBox.center();
	}

}
