package com.campscribe.client;

import com.campscribe.client.events.AddEditEventView;
import com.campscribe.client.events.EventService;
import com.campscribe.client.events.EventServiceJSONImpl;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EventModule implements EntryPoint {

	EventService eventService = new EventServiceJSONImpl();

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
		if (RootPanel.get("eventGWTBlock") != null) {
			RootPanel.get("eventGWTBlock").add(addButton);

			// Create the popup dialog box
			final CampScribeDialogBox dialogBox = new CampScribeDialogBox("Add Event", new AddEditEventView());
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

	private native void addGWTActionTriggers(EventModule module)/*-{
        $wnd.EventGWT = {
            deleteEvent: function(id) {
                module.@com.campscribe.client.EventModule::deleteEvent(Ljava/lang/String;)(id);
            },
            editEvent: function(id) {
                module.@com.campscribe.client.EventModule::editEvent(Ljava/lang/String;)(id);
            }
        };
    }-*/;

	public void deleteEvent(String id) {
		if (Window.confirm("Are you sure you want to delete this Event?")) {
			eventService.deleteEvent(id);
		}
	}

	public void editEvent(String id) {
		final CampScribeDialogBox dialogBox = new CampScribeDialogBox("Edit Event", new AddEditEventView(id));
		dialogBox.setAnimationEnabled(true);
		dialogBox.center();
	}

}
