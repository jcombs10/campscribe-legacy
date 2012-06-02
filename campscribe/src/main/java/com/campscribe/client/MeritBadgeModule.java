package com.campscribe.client;

import com.campscribe.client.meritbadges.AddEditMeritBadgeView;
import com.campscribe.client.meritbadges.MeritBadgeService;
import com.campscribe.client.meritbadges.MeritBadgeServiceJSONImpl;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MeritBadgeModule implements EntryPoint {

	MeritBadgeService mbService = new MeritBadgeServiceJSONImpl();

	
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

	private native void addGWTActionTriggers(MeritBadgeModule module)/*-{
        $wnd.MeritBadgeGWT = {
            deleteMeritBadge: function(id) {
                module.@com.campscribe.client.MeritBadgeModule::deleteMeritBadge(Ljava/lang/String;)(id);
            },
            editMeritBadge: function(id) {
                module.@com.campscribe.client.MeritBadgeModule::editMeritBadge(Ljava/lang/String;)(id);
            }
        };
    }-*/;

	public void deleteMeritBadge(String id) {
		if (Window.confirm("Are you sure you want to delete this merit badge?")) {
			mbService.deleteMeritBadge(id);
		}
	}

	public void editMeritBadge(String id) {
		final CampScribeDialogBox dialogBox = new CampScribeDialogBox("Edit Merit Badge", new AddEditMeritBadgeView(id));
		dialogBox.setAnimationEnabled(true);
		dialogBox.center();
	}

}
