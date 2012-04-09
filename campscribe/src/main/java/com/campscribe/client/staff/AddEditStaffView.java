package com.campscribe.client.staff;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.campscribe.client.CampScribeBodyWidget;
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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AddEditStaffView extends Composite implements CampScribeBodyWidget {

	private Logger logger = Logger.getLogger("AddEditStaffView");

	@UiField TextBox name;
	@UiField TextBox userId;
	@UiField PasswordTextBox password;
	@UiField CheckBox counselor;
	@UiField CheckBox areaDirector;
	@UiField CheckBox campAdmin;
	@UiField ListBox programArea;
	
	private Long id = Long.valueOf(-1);

	StaffService staffService = new StaffServiceJSONImpl();

	private static AddEditStaffViewUiBinder uiBinder = GWT
			.create(AddEditStaffViewUiBinder.class);

	interface AddEditStaffViewUiBinder extends
	UiBinder<Widget, AddEditStaffView> {
	}

	public AddEditStaffView() {
		initWidget(uiBinder.createAndBindUi(this));

		programArea.addItem("");
		programArea.addItem(StaffDTO.AQUATICS);
		programArea.addItem(StaffDTO.COPE_AND_CLIMBING);
		programArea.addItem(StaffDTO.EAGLE_RIDGE);
		programArea.addItem(StaffDTO.HANDICRAFT);
		programArea.addItem(StaffDTO.HANDYMAN);
		programArea.addItem(StaffDTO.HEALTH_LODGE);
		programArea.addItem(StaffDTO.NATIVE_AMERICAN_VILLAGE);
		programArea.addItem(StaffDTO.NEST);
		programArea.addItem(StaffDTO.OUTDOOR_SKILLS);
		programArea.addItem(StaffDTO.SHOOTING_SPORTS);
	}

	public AddEditStaffView(String id) {
		this();

		staffService.getStaff(id, new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				String str = response.getText();
				logger.info("getStaff response: " + str);
				StaffDTO c = parseStaffJsonData(str);

				AddEditStaffView.this.id = c.getId();
				name.setText(c.getName());
				userId.setText(c.getUserId());
				password.setText(c.getPassword());

				if (c.getRoles().contains("counselor")) {
					counselor.setValue(true);
				}
				if (c.getRoles().contains("area_director")) {
					areaDirector.setValue(true);
				}
				if (c.getRoles().contains("camp_admin")) {
					campAdmin.setValue(true);
				}

				for (int i=0; i<programArea.getItemCount(); i++) {
					if (programArea.getValue(i).equals(c.getProgramArea())) {
						programArea.setSelectedIndex(i);
					}
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
		if (id.equals(-1)) {
			staffService.addStaff(getData());
		} else {
			staffService.updateStaff(getData());
		}
	}

	private StaffDTO getData() {
		List<String> roles = new ArrayList<String>();
		if (counselor.getValue()) {
			roles.add("counselor");
		}
		if (areaDirector.getValue()) {
			roles.add("area_director");
		}
		if (campAdmin.getValue()) {
			roles.add("camp_admin");
		}
		StaffDTO e = new StaffDTO(name.getText(), userId.getText(), password.getText(), roles, programArea.getValue(programArea.getSelectedIndex()));
		e.setId(id);
		return e;
	}

	@Override
	public void onCancel() {
	}

	private StaffDTO parseStaffJsonData(String json) {

		JSONValue value = JSONParser.parseLenient(json);

		//		Window.alert("Got response: " + json);
		JSONObject mbObj = value.isObject();

		double id = mbObj.get("id").isNumber().doubleValue();
		String name = mbObj.get("name").isString().stringValue();
		String userId = mbObj.get("userId").isString().stringValue();
		String password = mbObj.get("password").isString().stringValue();
		String programArea = mbObj.get("programArea").isString().stringValue();
		JSONArray roleArray = mbObj.get("roles").isArray();
		ArrayList<String> roles = new ArrayList<String>();
		if (roleArray != null) {
			for (int i=0; i<=roleArray.size()-1; i++) {
				String roleObj = roleArray.get(i).isString().stringValue();
				roles.add(roleObj);
			}
		}

		StaffDTO staff = new StaffDTO();
		staff.setName(name);
		Double d = Double.valueOf(id);
		staff.setId(d.longValue());
		staff.setName(name);
		staff.setUserId(userId);
		staff.setPassword(password);
		staff.setProgramArea(programArea);
		staff.setRoles(roles);

		return staff;
	}

}
