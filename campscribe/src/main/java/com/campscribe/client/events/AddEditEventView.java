package com.campscribe.client.events;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.shared.EventDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class AddEditEventView extends Composite implements CampScribeBodyWidget {

	@UiField TextBox description;
	@UiField DatePicker startDate;
	@UiField DatePicker endDate;

	EventService eventService = new EventServiceJSONImpl();

	private static AddEditEventViewUiBinder uiBinder = GWT
			.create(AddEditEventViewUiBinder.class);

	interface AddEditEventViewUiBinder extends
	UiBinder<Widget, AddEditEventView> {
	}

	public AddEditEventView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void onSave() {
		eventService.addEvent(getData());
	}

	private EventDTO getData() {
		EventDTO e = new EventDTO(description.getText(), startDate.getValue(), endDate.getValue());
		return e;
	}

	@Override
	public void onCancel() {
	}

}
