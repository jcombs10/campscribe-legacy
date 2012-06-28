package com.campscribe.client;

import com.campscribe.client.clazzes.AddEditScoutClazzView;
import com.campscribe.client.clazzes.ClazzService;
import com.campscribe.client.clazzes.ClazzServiceJSONImpl;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ScoutClazzModule implements EntryPoint {

	ClazzService clazzService = new ClazzServiceJSONImpl();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		addGWTActionTriggers(this);

		final Button addButton = new Button("Add Scout");

		// We can add style names to widgets
		addButton.addStyleName("addButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		if (RootPanel.get("scoutClazzGWTBlock") != null) {
			RootPanel.get("scoutClazzGWTBlock").add(addButton);

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

	private native void addGWTActionTriggers(ScoutClazzModule module)/*-{
    $wnd.ScoutClazzGWT = {
        deleteScoutFromClazz: function(eventId, clazzId, scoutId, clazzName, scoutName) {
            module.@com.campscribe.client.ScoutClazzModule::deleteScoutFromClazz(Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;)(eventId, clazzId, scoutId, clazzName, scoutName);
        }
    };
	}-*/;

	public void deleteScoutFromClazz(Long eventId, Long clazzId, Long scoutId, String clazzName, String scoutName) {
		if (Window.confirm("Are you sure you want to remove "+scoutName+" from "+clazzName+"?")) {
			clazzService.deleteScoutFromClazz(eventId, clazzId, scoutId);
		}
	}

}
