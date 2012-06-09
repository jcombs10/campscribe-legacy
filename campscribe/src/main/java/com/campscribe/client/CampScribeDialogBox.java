package com.campscribe.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CampScribeDialogBox extends DialogBox {

	FlowPanel titleBar = new FlowPanel();
	FlowPanel contentPanel = new FlowPanel();
	FlowPanel footerBar = new FlowPanel();
	Button saveButton = new Button("Save");
	Button cancelButton = new Button("Cancel");
	
	private CampScribeBodyWidget body = null;
	private String title = "";

	public CampScribeDialogBox(String title, final CampScribeBodyWidget body) {
		this.title  = title;
		this.body = body;
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(titleBar);
		vp.add(contentPanel);
		vp.add(footerBar);
		add(vp);
		
		titleBar.add(new Label(title));
		titleBar.addStyleName("titleBar");
		contentPanel.add(body);
		
		footerBar.add(saveButton);
		footerBar.add(cancelButton);
		footerBar.addStyleName("buttonBar");
		
		saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				body.onSave();
				hide();
			}
			
		});
		
		cancelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				body.onCancel();
				hide();
			}
			
		});
		
		cancelButton.getElement().setId("cancelButton");
		saveButton.getElement().setId("saveButton");
	}

	public CampScribeBodyWidget getBody() {
		return body;
	}

	public void setBody(CampScribeBodyWidget body) {
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
