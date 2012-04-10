package com.campscribe.client.meritbadges;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.shared.MeritBadgeDTO;
import com.campscribe.shared.RequirementDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AddEditMeritBadgeView extends Composite implements CampScribeBodyWidget {

	private Logger logger = Logger.getLogger("AddEditMeritBadgeView");

	@UiField TextBox badgeName;
	@UiField TextBox bsaAdvancementId;
	@UiField CheckBox eagleRequired;
	@UiField ListBox reqCount;
	@UiField FlowPanel reqsPanel;
	private List<RequirementLineItem> reqSelectList = new ArrayList<RequirementLineItem>();

	private Long id = null;

	MeritBadgeService mbService = new MeritBadgeServiceJSONImpl();

	private static AddEditMeritBadgeViewUiBinder uiBinder = GWT
			.create(AddEditMeritBadgeViewUiBinder.class);

	interface AddEditMeritBadgeViewUiBinder extends
	UiBinder<Widget, AddEditMeritBadgeView> {
	}

	public AddEditMeritBadgeView() {
		initWidget(uiBinder.createAndBindUi(this));

		reqCount.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				setupReqs();
			}

		});

		for (int i=1; i<=20; i++) {
			reqCount.addItem(Integer.toString(i));
		}
		setupReqs();
		badgeName.setFocus(true);
	}

	public AddEditMeritBadgeView(String id) {
		this();

		mbService.getMeritBadge(id, new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				String str = response.getText();
				logger.info("getStaff response: " + str);
				MeritBadgeDTO c = parseMeritBadgeJsonData(str);

				AddEditMeritBadgeView.this.id = c.getId();
				badgeName.setText(c.getBadgeName());
				bsaAdvancementId.setText(c.getBsaAdvancementId());
				eagleRequired.setValue(c.isEagleRequired());

			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		});
	}

	private void setupReqs() {
		reqsPanel.clear();
		reqSelectList.clear();
		for (int i=0; i<=reqCount.getSelectedIndex(); i++) {
			reqsPanel.add(createRequirementSelect(i+1));
		}
	}

	private Widget createRequirementSelect(int i) {
		RequirementLineItem reqSelect = new RequirementLineItem(i);
		reqSelectList.add(reqSelect);
		return reqSelect;
	}

	@Override
	public void onSave() {
		if (id == null) {
			mbService.addMeritBadge(getData());
		} else {
			mbService.updateMeritBadge(getData());
		}
	}

	private MeritBadgeDTO getData() {
		MeritBadgeDTO mb = new MeritBadgeDTO(badgeName.getText(), eagleRequired.getValue());
		mb.setBsaAdvancementId(bsaAdvancementId.getText());
		List<RequirementDTO> requirements = new ArrayList<RequirementDTO>();
		for (RequirementLineItem reqItem:reqSelectList) {
			RequirementDTO reqDto = new RequirementDTO();
			reqDto.setReqType(reqItem.getReqType());
			reqDto.setHowManyToChoose(reqItem.getHowManyToChoose());
			reqDto.setOptionCount(reqItem.getOptionCount());
			requirements.add(reqDto);
		}
		if (id != null) {
			mb.setId(id);
		}
		mb.setRequirements(requirements);
		return mb;
	}

	@Override
	public void onCancel() {
	}

	private class RequirementLineItem extends FlowPanel {
		private ListBox lb;
		private TextBox howManyToChoose;
		private Label ofLabel; 
		private TextBox optionCount;

		public RequirementLineItem(int i) {
			HorizontalPanel hp = new HorizontalPanel();
			Label l = new Label(Integer.toString(i)+".");
			hp.add(l);

			lb = new ListBox();
			lb.addItem(RequirementDTO.SIMPLE);
			lb.addItem(RequirementDTO.N_OF_M);
			hp.add(lb);

			howManyToChoose = new TextBox();
			howManyToChoose.setVisibleLength(2);
			howManyToChoose.setVisible(false);
			hp.add(howManyToChoose);

			ofLabel = new Label(" of ");
			ofLabel.setVisible(false);
			hp.add(ofLabel);

			optionCount = new TextBox();
			optionCount.setVisible(false);
			optionCount.setVisibleLength(2);
			hp.add(optionCount);
			add(hp);

			lb.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {
					// TODO Auto-generated method stub
					if (lb.getSelectedIndex() == 1) {
						howManyToChoose.setVisible(true);
						ofLabel.setVisible(true);
						optionCount.setVisible(true);
					} else {
						howManyToChoose.setVisible(false);
						ofLabel.setVisible(false);
						optionCount.setVisible(false);
					}
				}

			});

			KeyPressHandler numericHandler = new KeyPressHandler() {

				@Override
				public void onKeyPress(KeyPressEvent event) {
					Window.alert(""+event.getCharCode());
					if (!Character.isDigit(event.getCharCode())) {
						Window.alert("stopping propogation");
						event.preventDefault();
					}
				}

			};
			howManyToChoose.addKeyPressHandler(numericHandler);
			optionCount.addKeyPressHandler(numericHandler);
			
//			Event.addNativePreviewHandler(new Event.NativePreviewHandler() {
//
//				@Override
//				public void onPreviewNativeEvent(NativePreviewEvent event) {
//					if (event.)
//					
//				}
//				
//			});
		}

		public int getOptionCount() {
			if (optionCount.getText() == null || optionCount.getText().equals("")) {
				return 0;
			}
			return Integer.parseInt(optionCount.getText());
		}

		public int getHowManyToChoose() {
			if (howManyToChoose.getText() == null || howManyToChoose.getText().equals("")) {
				return 0;
			}
			return Integer.parseInt(howManyToChoose.getText());
		}

		public String getReqType() {
			return lb.getValue(lb.getSelectedIndex());
		}

	}

	private MeritBadgeDTO parseMeritBadgeJsonData(String json) {

		JSONValue value = JSONParser.parseLenient(json);

		//		Window.alert("Got response: " + json);
		JSONObject mbObj = value.isObject();

		double id = mbObj.get("id").isNumber().doubleValue();
		String bsaAdvancementId = mbObj.get("bsaAdvancementId").isString().stringValue();
		String badgeName = mbObj.get("badgeName").isString().stringValue();
		Boolean eagleRequired = mbObj.get("eagleRequired").isBoolean().booleanValue();

		MeritBadgeDTO mb = new MeritBadgeDTO();
		mb.setBadgeName(badgeName);
		Double d = Double.valueOf(id);
		mb.setId(d.longValue());
		mb.setBsaAdvancementId(bsaAdvancementId);
		mb.setEagleRequired(eagleRequired);

		return mb;
	}

}
