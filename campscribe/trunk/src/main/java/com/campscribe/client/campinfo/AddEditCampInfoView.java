package com.campscribe.client.campinfo;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.shared.CampInfoDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AddEditCampInfoView extends Composite implements CampScribeBodyWidget {

	@UiField TextBox campName;
	@UiField TextBox address;
	@UiField TextBox city;
	@UiField TextBox state;
	@UiField TextBox zip;
	@UiField TextBox phoneNbr;
	@UiField TextBox meritBadgeSigner;

	private Long id = null;

	CampInfoService campInfoService = new CampInfoServiceJSONImpl();

	private static AddEditCampInfoViewUiBinder uiBinder = GWT
			.create(AddEditCampInfoViewUiBinder.class);

	interface AddEditCampInfoViewUiBinder extends
	UiBinder<Widget, AddEditCampInfoView> {
	}

	public AddEditCampInfoView() {
		initWidget(uiBinder.createAndBindUi(this));

		campInfoService.getCampInfo(new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				String s = response.getText();
				CampInfoDTO ci = CampInfoDTOHelper.parseCampInfoJsonData(s);
				id = ci.getId();
				campName.setText(ci.getCampName());
				address.setText(ci.getAddress());
				city.setText(ci.getCity());
				state.setText(ci.getState());
				zip.setText(ci.getZip());
				phoneNbr.setText(ci.getPhoneNbr());
				meritBadgeSigner.setText(ci.getMeritBadgeSigner());
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		});

	}

	@Override
	public void onSave() {
//		if (id == null) {
//			campInfoService.addStaff(getData());
//		} else {
			campInfoService.updateStaff(getData());
//		}
	}

	private CampInfoDTO getData() {
		CampInfoDTO ci = new CampInfoDTO();
		ci.setId(id);
		ci.setCampName(campName.getText());
		ci.setAddress(address.getText());
		ci.setCity(city.getText());
		ci.setState(state.getText());
		ci.setZip(zip.getText());
		ci.setPhoneNbr(phoneNbr.getText());
		ci.setMeritBadgeSigner(meritBadgeSigner.getText());
		return ci;
	}

	@Override
	public void onCancel() {
	}

}
