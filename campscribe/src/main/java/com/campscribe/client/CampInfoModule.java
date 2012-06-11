package com.campscribe.client;

import com.campscribe.client.campinfo.AddEditCampInfoView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CampInfoModule implements EntryPoint {

//	CampInfoService scoutService = new CampInfoServiceJSONImpl();


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button editButton = new Button("Edit");

		// We can add style names to widgets
		editButton.addStyleName("addButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		if (RootPanel.get("campInfoGWTBlock") != null) {
			RootPanel.get("campInfoGWTBlock").add(editButton);
		}

		// Create the popup dialog box
		final CampScribeDialogBox dialogBox = new CampScribeDialogBox("Edit Camp Info", new AddEditCampInfoView());
		dialogBox.setAnimationEnabled(true);

		// Add a handler to send the name to the server
		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dialogBox.center();
			}
		});
	}

}
