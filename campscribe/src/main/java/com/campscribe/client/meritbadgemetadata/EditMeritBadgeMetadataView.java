package com.campscribe.client.meritbadgemetadata;

import java.util.List;
import java.util.logging.Logger;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.client.staff.StaffDTOHelper;
import com.campscribe.client.staff.StaffService;
import com.campscribe.client.staff.StaffServiceJSONImpl;
import com.campscribe.shared.MeritBadgeMetadataDTO;
import com.campscribe.shared.ProgramArea;
import com.campscribe.shared.StaffDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class EditMeritBadgeMetadataView extends Composite implements CampScribeBodyWidget {

	private Logger logger = Logger.getLogger("AddEditMeritBadgeView");

	@UiField Label badgeName;
	@UiField ListBox staff;
	@UiField ListBox programArea;

	private Long id = null;

	MeritBadgeMetadataService mbService = new MeritBadgeMetadataServiceJSONImpl();
	StaffService staffService = new StaffServiceJSONImpl();

	private static EditMeritBadgeMetadataViewUiBinder uiBinder = GWT
			.create(EditMeritBadgeMetadataViewUiBinder.class);

	interface EditMeritBadgeMetadataViewUiBinder extends
	UiBinder<Widget, EditMeritBadgeMetadataView> {
	}

	public EditMeritBadgeMetadataView(final Long id) {
		this.id = id;

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

		staffService.getStaffList(new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				String s = response.getText();
				List<StaffDTO> staffList = StaffDTOHelper.parseStaffListJsonData(s);
				staff.addItem("", "");
				for (StaffDTO sDTO:staffList) {
					staff.addItem(sDTO.getName(), sDTO.getId().toString());
				}

				//TODO - pass the Long here
				mbService.getMeritBadgeMetadata(id, new RequestCallback() {

					@Override
					public void onResponseReceived(Request request, Response response) {
						String str = response.getText();
						logger.info("getMeritBadge response: " + str);
						MeritBadgeMetadataDTO c = MeritBadgeMetadataDTOHelper.parseMeritBadgeJsonData(str);

						EditMeritBadgeMetadataView.this.id = c.getId();
						badgeName.setText(c.getMbName());

						for (int i=0; i<programArea.getItemCount(); i++) {
							if (programArea.getValue(i).equals(c.getProgramArea())) {
								programArea.setSelectedIndex(i);
							}
						}

						for (int i=0; i<staff.getItemCount(); i++) {
							if (staff.getValue(i).equals(c.getStaffId().toString())) {
								staff.setSelectedIndex(i);
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
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		});

	}

	@Override
	public void onSave() {
		if (id != null) {
			mbService.updateMeritBadgeMetadata(getData());
		}
	}

	private MeritBadgeMetadataDTO getData() {
		MeritBadgeMetadataDTO mb = new MeritBadgeMetadataDTO();
		mb.setProgramArea(programArea.getItemText(programArea.getSelectedIndex()));
		mb.setStaffId(Long.valueOf(staff.getValue(staff.getSelectedIndex())));
		if (id != null && !id.equals(-1)) {
			mb.setId(id);
		}
		return mb;
	}

	@Override
	public void onCancel() {
	}

}
