package com.campscribe.client.meritbadges;

import java.util.ArrayList;
import java.util.List;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.shared.MeritBadgeDTO;
import com.campscribe.shared.RequirementDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.EventPreview;
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

	@UiField TextBox badgeName;
	@UiField TextBox bsaAdvancementId;
	@UiField CheckBox eagleRequired;
	@UiField ListBox reqCount;
	@UiField FlowPanel reqsPanel;
	private List<RequirementLineItem> reqSelectList = new ArrayList<RequirementLineItem>();

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
		mbService.addMeritBadge(getData());
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
						event.stopPropagation();
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

}
