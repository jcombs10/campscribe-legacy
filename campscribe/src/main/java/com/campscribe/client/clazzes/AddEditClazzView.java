package com.campscribe.client.clazzes;

import java.util.ArrayList;
import java.util.List;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.client.meritbadges.MeritBadgeService;
import com.campscribe.client.meritbadges.MeritBadgeServiceJSONImpl;
import com.campscribe.shared.ClazzDTO;
import com.campscribe.shared.MeritBadgeDTO;
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
	
	private Long eventId = Long.valueOf(-1);

	ClazzService clazzService = new ClazzServiceJSONImpl();
	MeritBadgeService mbService = new MeritBadgeServiceJSONImpl();

	private static AddEditClazzViewUiBinder uiBinder = GWT
			.create(AddEditClazzViewUiBinder.class);

	interface AddEditClazzViewUiBinder extends
	UiBinder<Widget, AddEditClazzView> {
	}

	public AddEditClazzView() {
		initWidget(uiBinder.createAndBindUi(this));
		mbService.getMeritBadges(new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				String s = response.getText();
				List<MeritBadgeDTO> badges = parseJsonData(s);
				for (MeritBadgeDTO b:badges) {
					meritBadge.addItem(b.getBadgeName(), b.getId().toString());
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		});
	}

	private List<MeritBadgeDTO> parseJsonData(String json) {
		
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


	@Override
	public void onSave() {
		clazzService.addClazz(getData());
	}

	private ClazzDTO getData() {
		ClazzDTO c = new ClazzDTO(description.getText(), Long.valueOf(meritBadge.getValue(meritBadge.getSelectedIndex())));
		c.setEventId(getEventIdFromPage());
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
