package com.campscribe.client.clazzes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.client.meritbadges.MeritBadgeService;
import com.campscribe.client.meritbadges.MeritBadgeServiceJSONImpl;
import com.campscribe.client.staff.StaffService;
import com.campscribe.client.staff.StaffServiceJSONImpl;
import com.campscribe.shared.MeritBadgeDTO;
import com.campscribe.shared.ScoutDTO;
import com.campscribe.shared.StaffDTO;
import com.campscribe.shared.TrackProgressDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TrackClazzProgressView extends Composite implements CampScribeBodyWidget {

	@UiField FlexTable attendanceTable;
	@UiField FlexTable progressTable;

	ClazzService clazzService = new ClazzServiceJSONImpl();
	StaffService staffService = new StaffServiceJSONImpl();
	MeritBadgeService mbService = new MeritBadgeServiceJSONImpl();

	private String clazzKey;

	private Long clazzId = Long.valueOf(-1);

	private Long eventId = Long.valueOf(-1);

	private static TrackClazzProgressViewUiBinder uiBinder = GWT
			.create(TrackClazzProgressViewUiBinder.class);

	interface TrackClazzProgressViewUiBinder extends
	UiBinder<Widget, TrackClazzProgressView> {
	}

	public TrackClazzProgressView(String clazzKey) {
		this.clazzKey = clazzKey;

		initWidget(uiBinder.createAndBindUi(this));

		clazzService.getClazz(getEventIdFromPage(), getClazzIdFromPage(), new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				String str = response.getText();
				TrackProgressDTO c = parseTrackProgressJsonData(str);
				int row = 1;
				for (ScoutDTO s:c.getScouts()) {
					attendanceTable.setWidget(row++, 0, new Label(s.getDisplayName()));
				}
				int column = 1;
				Date sd = (Date) c.getStartDate().clone();
				Date ed = c.getEndDate();
				while (!sd.after(ed)) {
					sd.setDate(sd.getDate()+1);
					attendanceTable.setWidget(0, column++, new Label(DateTimeFormat.getShortDateFormat().format(sd)));
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		});

		//		mbService.getMeritBadges(new RequestCallback() {
		//
		//			@Override
		//			public void onResponseReceived(Request request, Response response) {
		//				String s = response.getText();
		//				List<MeritBadgeDTO> badges = parseMBJsonData(s);
		//				for (MeritBadgeDTO b:badges) {
		//					meritBadge.addItem(b.getBadgeName(), b.getId().toString());
		//				}
		//			}
		//
		//			@Override
		//			public void onError(Request request, Throwable exception) {
		//				Window.alert("Error Occurred: " + exception.getMessage());
		//			}
		//
		//		});
		//
		//		staffService.getStaffList(new RequestCallback() {
		//
		//			@Override
		//			public void onResponseReceived(Request request, Response response) {
		//				String s = response.getText();
		//				List<StaffDTO> staffList = parseStaffListJsonData(s);
		//				for (StaffDTO sDTO:staffList) {
		//					staff.addItem(sDTO.getName(), sDTO.getId().toString());
		//				}
		//			}
		//
		//			@Override
		//			public void onError(Request request, Throwable exception) {
		//				Window.alert("Error Occurred: " + exception.getMessage());
		//			}
		//
		//		});
	}

	private TrackProgressDTO parseTrackProgressJsonData(String str) {
		TrackProgressDTO dto = new TrackProgressDTO();
		dto.setStartDate(DateTimeFormat.getShortDateFormat().parse("06/18/2012"));
		dto.setEndDate(DateTimeFormat.getShortDateFormat().parse("06/22/2012"));
		List<ScoutDTO> scouts = new ArrayList<ScoutDTO>();
		scouts.add(new ScoutDTO("Jacob", "Combs", "Tenderfoot", "Troop", "408"));
		scouts.add(new ScoutDTO("Nick", "Berger", "Eagle", "Troop", "24"));
		dto.setScouts(scouts);
		return dto;
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

		List<StaffDTO> staffs = new ArrayList<StaffDTO>();

		JSONValue value = JSONParser.parseLenient(json);
		JSONArray mbArray = value.isArray();

		//		Window.alert("Got response: " + json);
		if (mbArray != null) {
			for (int i=0; i<=mbArray.size()-1; i++) {
				JSONObject mbObj = mbArray.get(i).isObject();

				double id = mbObj.get("id").isNumber().doubleValue();
				String name = mbObj.get("name").isString().stringValue();
				String userId = mbObj.get("userId").isString().stringValue();
				String password = mbObj.get("password").isString().stringValue();

				StaffDTO b = new StaffDTO();
				b.setName(name);
				Double d = Double.valueOf(id);
				b.setId(d.longValue());

				staffs.add(b);
			}

		}

		return staffs;
	}


	@Override
	public void onSave() {
		//		clazzService.addClazz(getData());
	}

	//	private ClazzDTO getData() {
	//		ClazzDTO c = new ClazzDTO(description.getText(), Long.valueOf(meritBadge.getValue(meritBadge.getSelectedIndex())));
	//		c.setStaffId(Long.valueOf(staff.getValue(staff.getSelectedIndex())));
	//		c.setEventId(getEventIdFromPage());
	//		return c;
	//	}
	//
	@Override
	public void onCancel() {
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
