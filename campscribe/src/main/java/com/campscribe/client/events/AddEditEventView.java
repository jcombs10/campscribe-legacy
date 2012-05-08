package com.campscribe.client.events;

import java.util.Date;
import java.util.logging.Logger;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.shared.EventDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class AddEditEventView extends Composite implements CampScribeBodyWidget {

	private Logger logger = Logger.getLogger("AddEditEventView");

	@UiField TextBox description;
	@UiField DatePicker startDate;
	@UiField DatePicker endDate;

	private Long id = null;

	EventService eventService = new EventServiceJSONImpl();

	private static AddEditEventViewUiBinder uiBinder = GWT
			.create(AddEditEventViewUiBinder.class);

	interface AddEditEventViewUiBinder extends
	UiBinder<Widget, AddEditEventView> {
	}

	public AddEditEventView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public AddEditEventView(String id) {
		this();

		eventService.getEvent(id, new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				String str = response.getText();
				logger.info("getStaff response: " + str);
				EventDTO c = parseEventJsonData(str);

				AddEditEventView.this.id = c.getId();
				description.setText(c.getDescription());
				startDate.setValue(c.getStartDate());
				endDate.setValue(c.getEndDate());
				startDate.setCurrentMonth(c.getStartDate());
				endDate.setCurrentMonth(c.getEndDate());
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		});
	}

	@Override
	public void onSave() {
		if (id == null) {
			eventService.addEvent(getData());
		} else {
			eventService.updateEvent(getData());
		}
	}

	private EventDTO getData() {
		EventDTO e = new EventDTO(description.getText(), startDate.getValue(), endDate.getValue());
		if (id != null) {
			e.setId(id);
		}
		return e;
	}

	@Override
	public void onCancel() {
	}

	private EventDTO parseEventJsonData(String json) {

		JSONValue value = JSONParser.parseLenient(json);

//				Window.alert("Got response: " + json);
		JSONObject mbObj = value.isObject();

		double id = mbObj.get("id").isNumber().doubleValue();
		String description = mbObj.get("description").isString().stringValue();
		double startDate = mbObj.get("startDate").isNumber().doubleValue();
		double endDate = mbObj.get("endDate").isNumber().doubleValue();

		EventDTO event = new EventDTO();
		event.setDescription(description);
		Double d = Double.valueOf(id);
		event.setId(d.longValue());
		//hack to get around GMT conversion
		final Date gmtDate = new Date();
		event.setStartDate(new Date(Double.valueOf(startDate).longValue()+(gmtDate.getTimezoneOffset() * 60 * 1000)));
		event.setEndDate(new Date(Double.valueOf(endDate).longValue()+(gmtDate.getTimezoneOffset() * 60 * 1000)));

		return event;
	}

}
