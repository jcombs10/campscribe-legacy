package com.campscribe.client.staff;

import java.util.ArrayList;
import java.util.List;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.shared.StaffDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AddEditStaffView extends Composite implements CampScribeBodyWidget {

	@UiField TextBox name;
	@UiField TextBox userId;
	@UiField CheckBox counselor;
	@UiField CheckBox areaDirector;
	@UiField CheckBox campAdmin;
	@UiField ListBox programArea;

	StaffService staffService = new StaffServiceJSONImpl();

	private static AddEditStaffViewUiBinder uiBinder = GWT
			.create(AddEditStaffViewUiBinder.class);

	interface AddEditStaffViewUiBinder extends
	UiBinder<Widget, AddEditStaffView> {
	}

	public AddEditStaffView() {
		initWidget(uiBinder.createAndBindUi(this));

		programArea.addItem(StaffDTO.AQUATICS);
		programArea.addItem(StaffDTO.HANDICRAFT);
		programArea.addItem(StaffDTO.OUTDOOR_SKILLS);
	}

	@Override
	public void onSave() {
		staffService.addStaff(getData());
	}

	private StaffDTO getData() {
		List<String> roles = new ArrayList<String>();
		if (counselor.getValue()) {
			roles.add("Counselor");
		}
		if (areaDirector.getValue()) {
			roles.add("Area Director");
		}
		if (campAdmin.getValue()) {
			roles.add("Camp Admin");
		}
		StaffDTO e = new StaffDTO(name.getText(), userId.getText(), roles, programArea.getValue(programArea.getSelectedIndex()));
		return e;
	}

	@Override
	public void onCancel() {
	}

}
