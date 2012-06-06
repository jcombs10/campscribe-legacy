package com.campscribe.client;

import com.campscribe.client.clazzes.AddEditScoutClazzView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ScoutClazzModule implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		final Button addButton = new Button("Add Scout");

		// We can add style names to widgets
		addButton.addStyleName("addButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		if (RootPanel.get("scoutClazzGWTBlock") != null) {
			RootPanel.get("scoutClazzGWTBlock").add(addButton);
		}

		// Create the popup dialog box
		final CampScribeDialogBox dialogBox = new CampScribeDialogBox("Add Scout", new AddEditScoutClazzView());
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
