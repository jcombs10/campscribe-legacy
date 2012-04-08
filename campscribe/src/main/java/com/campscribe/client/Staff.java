package com.campscribe.client;

import com.campscribe.client.staff.AddEditStaffView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Staff implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

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
		if (RootPanel.get("staffGWTBlock") != null) {
			RootPanel.get("staffGWTBlock").add(addButton);
		}

		// Create the popup dialog box
		final CampScribeDialogBox dialogBox = new CampScribeDialogBox("Add Staff", new AddEditStaffView());
		dialogBox.setAnimationEnabled(true);

		// Add a handler to send the name to the server
		addButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dialogBox.center();
			}
		});
	}

	private native void addGWTActionTriggers(Staff module)/*-{
        $wnd.StaffGWT = {
            editStaff: function(id) {
                module.@com.campscribe.client.Staff::editStaff(Ljava/lang/String;)(id);
            }
        };
    }-*/;

	public void editStaff(String id) {
		final CampScribeDialogBox dialogBox = new CampScribeDialogBox("Edit Staff", new AddEditStaffView(id));
		dialogBox.setAnimationEnabled(true);
		dialogBox.center();
	}

}
