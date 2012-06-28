package com.campscribe.client;

import com.campscribe.client.clazzes.AddEditClazzView;
import com.campscribe.client.clazzes.TrackClazzProgressView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ClazzModule implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		addGWTActionTriggers(this);
		
		final Button addButton = new Button("Add Class");

		// We can add style names to widgets
		addButton.addStyleName("addButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		if (RootPanel.get("clazzGWTBlock") != null) {
			RootPanel.get("clazzGWTBlock").add(addButton);

			// Create the popup dialog box
			final CampScribeDialogBox dialogBox = new CampScribeDialogBox("Add Class", new AddEditClazzView());
			dialogBox.setAnimationEnabled(true);

			// Add a handler to send the name to the server
			addButton.addClickHandler(new ClickHandler() {
			
				@Override
				public void onClick(ClickEvent event) {
					dialogBox.center();
				}
			});
		}
	}
	
	private native void addGWTActionTriggers(ClazzModule module)/*-{
	    $wnd.ClazzGWT = {
	        trackProgress: function(eventId, clazzId, badgeName, clazzDescription) {
	            module.@com.campscribe.client.ClazzModule::editClazzProgress(Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;)(eventId, clazzId, badgeName, clazzDescription);
	        }
	    };
	}-*/;
	
	public void editClazzProgress(Long eventId, Long clazzId, String badgeName, String clazzDescription) {
		final CampScribeDialogBox dialogBox = new CampScribeDialogBox("Track Progress - "+badgeName+" "+clazzDescription, new TrackClazzProgressView(eventId, clazzId));
		dialogBox.setAnimationEnabled(true);
		dialogBox.center();
	}

}
