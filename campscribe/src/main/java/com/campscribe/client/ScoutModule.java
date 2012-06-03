package com.campscribe.client;

import com.campscribe.client.scouts.AddEditScoutView;
import com.campscribe.client.scouts.ScoutService;
import com.campscribe.client.scouts.ScoutServiceJSONImpl;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ScoutModule implements EntryPoint {

	ScoutService scoutService = new ScoutServiceJSONImpl();


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
		if (RootPanel.get("scoutGWTBlock") != null) {
			RootPanel.get("scoutGWTBlock").add(addButton);
		}

		// Create the popup dialog box
		final CampScribeDialogBox dialogBox = new CampScribeDialogBox("Add Scout", new AddEditScoutView());
		dialogBox.setAnimationEnabled(true);

		// Add a handler to send the name to the server
		addButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dialogBox.center();
			}
		});
	}

	private native void addGWTActionTriggers(ScoutModule module)/*-{
        $wnd.ScoutGWT = {
            deleteScout: function(id) {
                module.@com.campscribe.client.ScoutModule::deleteScout(Ljava/lang/String;)(id);
            },
            editScout: function(id) {
                module.@com.campscribe.client.ScoutModule::editScout(Ljava/lang/String;)(id);
            }
        };
    }-*/;

	public void deleteScout(String id) {
		if (Window.confirm("Are you sure you want to delete this Scout?")) {
			scoutService.deleteScout(id);
		}
	}

	public void editScout(String id) {
		Window.alert("Edit Scout not yet implemented");
//		final CampScribeDialogBox dialogBox = new CampScribeDialogBox("Edit Scout", new AddEditScoutView(id));
//		dialogBox.setAnimationEnabled(true);
//		dialogBox.center();
	}

}
