package com.campscribe.client.clazzes;

import java.util.ArrayList;
import java.util.List;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.client.scouts.ScoutService;
import com.campscribe.client.scouts.ScoutServiceJSONImpl;
import com.campscribe.shared.ScoutDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AddEditScoutClazzView extends Composite implements CampScribeBodyWidget {

	@UiField TextBox lastName;
	@UiField ListBox unitType;
	@UiField TextBox unitNumber;
	@UiField Button searchButton;
	@UiField FlowPanel searchResultsPanel;
	@UiField ListBox searchResults;

	ClazzService clazzService = new ClazzServiceJSONImpl();
	ScoutService scoutService = new ScoutServiceJSONImpl();
	List<ScoutDTO> scouts = new ArrayList<ScoutDTO>();

	private Long clazzId = Long.valueOf(-1);

	private Long eventId = Long.valueOf(-1);

	private static AddEditScoutClazzViewUiBinder uiBinder = GWT
			.create(AddEditScoutClazzViewUiBinder.class);

	interface AddEditScoutClazzViewUiBinder extends
	UiBinder<Widget, AddEditScoutClazzView> {
	}

	public AddEditScoutClazzView() {
		initWidget(uiBinder.createAndBindUi(this));

		unitType.addItem(ScoutDTO.TROOP);
		unitType.addItem(ScoutDTO.CREW);
		unitType.addItem(ScoutDTO.TEAM);
		unitType.addItem(ScoutDTO.POST);
	}

	@UiFactory
	public ListBox getSearchResultsBox(Boolean allowMultiSelect) {
		return new ListBox(allowMultiSelect);
	}

	@Override
	public void onSave() {
		clazzService.addScoutToClazz(getEventIdFromPage(), getClazzIdFromPage(), getData());
	}

	private List<ScoutDTO> getData() {
		List<ScoutDTO> selectedScouts = new ArrayList<ScoutDTO>();
		int i=0;
		for (ScoutDTO s:scouts) {
			if (searchResults.isItemSelected(i++)) {
				selectedScouts.add(s);
			}
		}
		return selectedScouts;
	}

	@Override
	public void onCancel() {
	}

	@UiHandler("searchButton")
	public void onSearch(ClickEvent ce) {
		RequestCallback callback = new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				//				Window.alert("received response "+response.getText());
				searchResults.clear();
				String s = response.getText();
				List<ScoutDTO> scouts = parseScoutJsonData(s);
				AddEditScoutClazzView.this.scouts = scouts;
				for (ScoutDTO scout:scouts) {
					searchResults.addItem(scout.getDisplayName(), scout.getId().toString());
				}
				searchResultsPanel.setVisible(true);
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		};

		scoutService.searchScouts(lastName.getText(), unitType.getValue(unitType.getSelectedIndex()), unitNumber.getText(), callback);
	}

	private List<ScoutDTO> parseScoutJsonData(String json) {

		List<ScoutDTO> scouts = new ArrayList<ScoutDTO>();

		JSONValue value = JSONParser.parseLenient(json);
		JSONArray scoutArray = value.isArray();

		//		Window.alert("Got response: " + json);
		if (scoutArray != null) {
			for (int i=0; i<=scoutArray.size()-1; i++) {
				JSONObject mbObj = scoutArray.get(i).isObject();

				String firstName = mbObj.get("firstName").isString().stringValue();
				String lastName = mbObj.get("lastName").isString().stringValue();
				double id = mbObj.get("id").isNumber().doubleValue();

				ScoutDTO s = new ScoutDTO();
				s.setFirstName(firstName);
				s.setLastName(lastName);
				Double d = Double.valueOf(id);
				s.setId(d.longValue());

				scouts.add(s);
			}

		}

		return scouts;
	}

	private native Long getClazzIdFromPage() /*-{
        return this.@com.campscribe.client.clazzes.AddEditScoutClazzView::clazzId = $wnd.clazzId;
        return $wnd.clazzId;
    }-*/;

	private native Long getEventIdFromPage() /*-{
        return this.@com.campscribe.client.clazzes.AddEditScoutClazzView::eventId = $wnd.eventId;
        return $wnd.eventId;
    }-*/;

}
