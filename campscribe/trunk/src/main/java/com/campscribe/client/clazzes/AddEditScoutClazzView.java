package com.campscribe.client.clazzes;

import java.util.ArrayList;
import java.util.List;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.client.scouts.ScoutService;
import com.campscribe.client.scouts.ScoutServiceJSONImpl;
import com.campscribe.shared.ScoutDTO;
import com.campscribe.shared.UnitDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class AddEditScoutClazzView extends Composite implements CampScribeBodyWidget {

	@UiField ListBox unitList;
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

		RequestCallback callback = new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
//				Window.alert("received response "+response.getText());
				String s = response.getText();
				List<UnitDTO> units = parseUnitJsonData(s);
				unitList.addItem("All Units", "ALL");
				for (UnitDTO unit:units) {
					unitList.addItem(unit.getUnitType()+" "+unit.getUnitNumber());
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		};

		scoutService.getUnits(getEventIdFromPage(), callback);

		callback = new RequestCallback() {

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
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		};

		scoutService.getScouts(getEventIdFromPage(), "ALL", callback);
}

	@UiFactory
	public ListBox getSearchResultsBox(Boolean allowMultiSelect) {
		return new ListBox(allowMultiSelect);
	}

	@Override
	public void onSave() {
		clazzService.addScoutToClazz(Long.decode(getEventIdFromPage()), Long.decode(getClazzIdFromPage()), getData());
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

	@UiHandler("unitList")
	public void onSearch(ChangeEvent ce) {
		RequestCallback callback = new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
//					Window.alert("received response "+response.getText());
					searchResults.clear();
					String s = response.getText();
					List<ScoutDTO> scouts = parseScoutJsonData(s);
					AddEditScoutClazzView.this.scouts = scouts;
					for (ScoutDTO scout:scouts) {
						searchResults.addItem(scout.getDisplayName(), scout.getId().toString());
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("Error Occurred: " + exception.getMessage());
				}

			};

			scoutService.getScouts(getEventIdFromPage(), unitList.getValue(unitList.getSelectedIndex()), callback);
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

	private List<UnitDTO> parseUnitJsonData(String json) {

		List<UnitDTO> units = new ArrayList<UnitDTO>();

		JSONValue value = JSONParser.parseLenient(json);
		JSONArray unitArray = value.isArray();

		//		Window.alert("Got response: " + json);
		if (unitArray != null) {
			for (int i=0; i<=unitArray.size()-1; i++) {
				JSONObject mbObj = unitArray.get(i).isObject();

				String unitType = mbObj.get("unitType").isString().stringValue();
				String unitNumber = mbObj.get("unitNumber").isString().stringValue();

				UnitDTO u = new UnitDTO(unitType, unitNumber);

				units.add(u);
			}

		}

		return units;
	}

	private native String getClazzIdFromPage() /*-{
        return this.@com.campscribe.client.clazzes.AddEditScoutClazzView::clazzId = $wnd.clazzId;
        return $wnd.clazzId;
    }-*/;

	private native String getEventIdFromPage() /*-{
        return this.@com.campscribe.client.clazzes.AddEditScoutClazzView::eventId = $wnd.eventId;
        return $wnd.eventId;
    }-*/;

}
