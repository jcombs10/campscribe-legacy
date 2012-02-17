package com.campscribe.client.scouts;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.shared.ScoutDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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

	ScoutService scoutService = new ScoutServiceJSONImpl();

	private static AddEditEventViewUiBinder uiBinder = GWT
			.create(AddEditEventViewUiBinder.class);

	interface AddEditEventViewUiBinder extends
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
	}

	@Override
	public void onSave() {
		scoutService.addScout(getData());
	}

	private ScoutDTO getData() {
		ScoutDTO e = new ScoutDTO(firstName.getText(), lastName.getText(), rank.getValue(rank.getSelectedIndex()), unitType.getValue(unitType.getSelectedIndex()), unitNumber.getText());
		return e;
	}

	@Override
	public void onCancel() {
	}

}
