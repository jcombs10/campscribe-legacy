package com.campscribe.client;

import com.campscribe.client.meritbadgemetadata.EditMeritBadgeMetadataView;
import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MeritBadgeMetadata implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
//	private final GreetingServiceAsync greetingService = GWT
//			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		addGWTActionTriggers(this);
	}

	private native void addGWTActionTriggers(MeritBadgeMetadata module)/*-{
        $wnd.MeritBadgeMetadataGWT = {
            editMeritBadgeMetadata: function(id) {
                module.@com.campscribe.client.MeritBadgeMetadata::editMeritBadgeMetadata(Ljava/lang/Long;)(id);
            }
        };
    }-*/;

	public void editMeritBadgeMetadata(Long id) {
		final CampScribeDialogBox dialogBox = new CampScribeDialogBox("Edit Class Defaults", new EditMeritBadgeMetadataView(id));
		dialogBox.setAnimationEnabled(true);
		dialogBox.center();
	}

}
