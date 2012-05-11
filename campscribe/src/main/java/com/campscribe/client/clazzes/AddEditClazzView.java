package com.campscribe.client.clazzes;

import java.util.ArrayList;
import java.util.List;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.client.meritbadges.MeritBadgeService;
import com.campscribe.client.meritbadges.MeritBadgeServiceJSONImpl;
import com.campscribe.client.staff.StaffService;
import com.campscribe.client.staff.StaffServiceJSONImpl;
import com.campscribe.shared.ClazzDTO;
import com.campscribe.shared.MeritBadgeDTO;
import com.campscribe.shared.ProgramArea;
import com.campscribe.shared.StaffDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AddEditClazzView extends Composite implements CampScribeBodyWidget {

	@UiField TextBox description;
	@UiField ListBox meritBadge;
	@UiField ListBox staff;
	@UiField ListBox programArea;
	
	private Long eventId = Long.valueOf(-1);

	ClazzService clazzService = new ClazzServiceJSONImpl();
	StaffService staffService = new StaffServiceJSONImpl();
	MeritBadgeService mbService = new MeritBadgeServiceJSONImpl();

	private static AddEditClazzViewUiBinder uiBinder = GWT
			.create(AddEditClazzViewUiBinder.class);

	interface AddEditClazzViewUiBinder extends
	UiBinder<Widget, AddEditClazzView> {
	}

	public AddEditClazzView() {
		initWidget(uiBinder.createAndBindUi(this));

		programArea.addItem("");
		programArea.addItem(ProgramArea.AQUATICS);
		programArea.addItem(ProgramArea.COPE_AND_CLIMBING);
		programArea.addItem(ProgramArea.EAGLE_RIDGE);
		programArea.addItem(ProgramArea.HANDICRAFT);
		programArea.addItem(ProgramArea.HANDYMAN);
		programArea.addItem(ProgramArea.HEALTH_LODGE);
		programArea.addItem(ProgramArea.NATIVE_AMERICAN_VILLAGE);
		programArea.addItem(ProgramArea.NEST);
		programArea.addItem(ProgramArea.OUTDOOR_SKILLS);
		programArea.addItem(ProgramArea.SHOOTING_SPORTS);

		mbService.getMeritBadges(new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				String s = response.getText();
				List<MeritBadgeDTO> badges = parseMBJsonData(s);
				for (MeritBadgeDTO b:badges) {
					meritBadge.addItem(b.getBadgeName(), b.getId().toString());
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		});

		staffService.getStaffList(new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				String s = response.getText();
				List<StaffDTO> staffList = parseStaffListJsonData(s);
				for (StaffDTO sDTO:staffList) {
					staff.addItem(sDTO.getName(), sDTO.getId().toString());
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		});
	}

	private List<MeritBadgeDTO> parseMBJsonData(String json) {
		
		List<MeritBadgeDTO> badges = new ArrayList<MeritBadgeDTO>();

		JSONValue value = JSONParser.parseLenient(json);
		JSONArray mbArray = value.isArray();

//		Window.alert("Got response: " + json);
		if (mbArray != null) {
			for (int i=0; i<=mbArray.size()-1; i++) {
				JSONObject mbObj = mbArray.get(i).isObject();

				String badgeName = mbObj.get("badgeName").isString().stringValue();
				double id = mbObj.get("id").isNumber().doubleValue();
				String bsaAdvancementId = mbObj.get("bsaAdvancementId").isString().stringValue();

				MeritBadgeDTO b = new MeritBadgeDTO();
				b.setBadgeName(badgeName);
				b.setBsaAdvancementId(bsaAdvancementId);
				Double d = Double.valueOf(id);
				b.setId(d.longValue());
				
				badges.add(b);
			}

		}
		
		return badges;
	}

	private List<StaffDTO> parseStaffListJsonData(String json) {
		
		List<StaffDTO> badges = new ArrayList<StaffDTO>();

		JSONValue value = JSONParser.parseLenient(json);
		JSONArray mbArray = value.isArray();

//		Window.alert("Got response: " + json);
		if (mbArray != null) {
			for (int i=0; i<=mbArray.size()-1; i++) {
				JSONObject mbObj = mbArray.get(i).isObject();

				String name = mbObj.get("name").isString().stringValue();
				double id = mbObj.get("id").isNumber().doubleValue();

				StaffDTO b = new StaffDTO();
				b.setName(name);
				Double d = Double.valueOf(id);
				b.setId(d.longValue());
				
				badges.add(b);
			}

		}
		
		return badges;
	}


	@Override
	public void onSave() {
		clazzService.addClazz(getData());
	}

	private ClazzDTO getData() {
		ClazzDTO c = new ClazzDTO(description.getText(), Long.valueOf(meritBadge.getValue(meritBadge.getSelectedIndex())));
		c.setStaffId(Long.valueOf(staff.getValue(staff.getSelectedIndex())));
		c.setEventId(getEventIdFromPage());
		c.setProgramArea(programArea.getItemText(programArea.getSelectedIndex()));
		return c;
	}

	private native Long getEventIdFromPage() /*-{
	    return this.@com.campscribe.client.clazzes.AddEditClazzView::eventId = $wnd.eventId;
	    return $wnd.eventId;
	}-*/;

	@Override
	public void onCancel() {
	}

}
