package com.campscribe.client.scouts;

import java.util.List;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.client.events.EventDTOHelper;
import com.campscribe.client.events.EventService;
import com.campscribe.client.events.EventServiceJSONImpl;
import com.campscribe.shared.EventDTO;
import com.campscribe.shared.ScoutDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AddEditScoutView extends Composite implements CampScribeBodyWidget {

	@UiField TextBox firstName;
	@UiField TextBox lastName;
	@UiField ListBox rank;
	@UiField ListBox unitType;
	@UiField TextBox unitNumber;
	@UiField ListBox event;

	ScoutService scoutService = new ScoutServiceJSONImpl();
	EventService eventService = new EventServiceJSONImpl();

	private static AddEditScoutViewUiBinder uiBinder = GWT
			.create(AddEditScoutViewUiBinder.class);

	interface AddEditScoutViewUiBinder extends
	UiBinder<Widget, AddEditScoutView> {
	}

	public AddEditScoutView() {
		initWidget(uiBinder.createAndBindUi(this));

		rank.addItem(ScoutDTO.SCOUT);
		rank.addItem(ScoutDTO.TENDERFOOT);
		rank.addItem(ScoutDTO.SECOND_CLASS);
		rank.addItem(ScoutDTO.FIRST_CLASS);
		rank.addItem(ScoutDTO.STAR);
		rank.addItem(ScoutDTO.LIFE);
		rank.addItem(ScoutDTO.EAGLE);

		unitType.addItem(ScoutDTO.TROOP);
		unitType.addItem(ScoutDTO.CREW);
		unitType.addItem(ScoutDTO.TEAM);
		unitType.addItem(ScoutDTO.POST);

		eventService.getAllEvents(new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				String s = response.getText();
				List<EventDTO> eventList = EventDTOHelper.parseEventListJsonData(s);
				for (EventDTO eDTO:eventList) {
					event.addItem(eDTO.getDescription(), eDTO.getId().toString());
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		});
}

	@Override
	public void onSave() {
		scoutService.addScout(getData());
	}

	private ScoutDTO getData() {
		Long eventId = Long.parseLong(event.getValue(event.getSelectedIndex()));
		ScoutDTO e = new ScoutDTO(firstName.getText(), lastName.getText(), rank.getValue(rank.getSelectedIndex()), unitType.getValue(unitType.getSelectedIndex()), unitNumber.getText(), eventId);
		return e;
	}

	@Override
	public void onCancel() {
	}

}
