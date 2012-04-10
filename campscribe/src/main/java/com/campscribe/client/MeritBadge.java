package com.campscribe.client;

import com.campscribe.client.meritbadges.AddEditMeritBadgeView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MeritBadge implements EntryPoint {
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
		
		final Button addButton = new Button("Add");

		// We can add style names to widgets
		addButton.addStyleName("addButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		if (RootPanel.get("meritBadgeGWTBlock") != null) {
			RootPanel.get("meritBadgeGWTBlock").add(addButton);
		}
		// Create the popup dialog box
		final CampScribeDialogBox dialogBox = new CampScribeDialogBox("Add Merit Badge", new AddEditMeritBadgeView());
		dialogBox.setAnimationEnabled(true);

		// Add a handler to send the name to the server
		addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.center();
			}
		});
	}

	private native void addGWTActionTriggers(MeritBadge module)/*-{
        $wnd.MeritBadgeGWT = {
            editMeritBadge: function(id) {
                module.@com.campscribe.client.MeritBadge::editMeritBadge(Ljava/lang/String;)(id);
            }
        };
    }-*/;

	public void editMeritBadge(String id) {
		final CampScribeDialogBox dialogBox = new CampScribeDialogBox("Edit Merit Badge", new AddEditMeritBadgeView(id));
		dialogBox.setAnimationEnabled(true);
		dialogBox.center();
	}

}
