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
import com.google.gwt.json.client.JSONArray;
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
		this.id = Long.valueOf(id);

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
				setupReqs(c.getRequirements());

			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		});
	}

	protected void setupReqs(List<RequirementDTO> requirements) {
		reqSelectList.clear();
		reqsPanel.clear();
		reqCount.setSelectedIndex(requirements.size()-1);
		int i = 0;
		for (RequirementDTO req:requirements) {
			reqsPanel.add(createRequirementSelect(i+1, req));
			i++;
		}
	}

	private void setupReqs() {
		reqsPanel.clear();
		reqSelectList.clear();
		for (int i=0; i<=reqCount.getSelectedIndex(); i++) {
			reqsPanel.add(createRequirementSelect(i+1, null));
		}
	}

	private Widget createRequirementSelect(int i, RequirementDTO req) {
		RequirementLineItem reqSelect = new RequirementLineItem(i, 0, req);
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
			reqDto.setSubRequirements(reqItem.getSubRequirements());
			requirements.add(reqDto);
		}
		if (id != null && !id.equals(-1)) {
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
		private FlowPanel subReqPanel;

		private List<RequirementLineItem> subReqSelectList = new ArrayList<RequirementLineItem>();
		private int level;
		private int i;
		private final char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

		public RequirementLineItem(int i, int level, RequirementDTO req) {
			this.i = i;
			this.level = level;
			HorizontalPanel hp = new HorizontalPanel();
			Label l = null;
			if (level == 0) {
				l = new Label(Integer.toString(i)+". ");
			} else if (level == 1) {
					l = new Label(Character.toString(chars[i])+". ");
					l.addStyleName("paddingLeft20");
			} else if (level == 2) {
				l = new Label(Integer.toString(i+1)+". ");
				l.addStyleName("paddingLeft40");
			}
			hp.add(l);

			lb = new ListBox();
			lb.addItem(RequirementDTO.SIMPLE);
			lb.addItem(RequirementDTO.N_OF_M);
			if (req != null && RequirementDTO.N_OF_M.equals(req.getReqType())) {
				lb.setSelectedIndex(1);
			}
			hp.add(lb);

			howManyToChoose = new TextBox();
			howManyToChoose.setVisibleLength(2);
			howManyToChoose.setVisible(false);
			if (req != null && RequirementDTO.N_OF_M.equals(req.getReqType())) {
				howManyToChoose.setText(Integer.toString(req.getHowManyToChoose()));
				howManyToChoose.setVisible(true);
			}
			hp.add(howManyToChoose);

			ofLabel = new Label(" of ");
			ofLabel.setVisible(false);
			if (req != null && RequirementDTO.N_OF_M.equals(req.getReqType())) {
				ofLabel.setVisible(true);
			}
			hp.add(ofLabel);

			optionCount = new TextBox();
			optionCount.setVisible(false);
			optionCount.setVisibleLength(2);
			if (req != null && RequirementDTO.N_OF_M.equals(req.getReqType())) {
				optionCount.setText(Integer.toString(req.getOptionCount()));
				optionCount.setVisible(true);
			}
			hp.add(optionCount);
			add(hp);
			
			subReqPanel = new FlowPanel();
			add(subReqPanel);

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
					if (!Character.isDigit(event.getCharCode())) {
						event.preventDefault();
					}
				}

			};
			howManyToChoose.addKeyPressHandler(numericHandler);
			optionCount.addKeyPressHandler(numericHandler);
			optionCount.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {
					subReqPanel.clear();
					subReqSelectList.clear();
					for (int i=0; i<getOptionCount(); i++) {
						RequirementLineItem subRli = new RequirementLineItem(i, RequirementLineItem.this.level+1, null);
						subReqPanel.add(subRli);
						subReqSelectList.add(subRli);
					}
				}
				
			});
			
			if (req != null && RequirementDTO.N_OF_M.equals(req.getReqType())) {
				for (RequirementDTO subReq:req.getSubRequirements()) {
					RequirementLineItem subRli = new RequirementLineItem(i, RequirementLineItem.this.level+1, subReq);
					subReqPanel.add(subRli);
					subReqSelectList.add(subRli);
				}
			}

		}

		public List<RequirementDTO> getSubRequirements() {
			List<RequirementDTO> requirements = new ArrayList<RequirementDTO>();
			for (RequirementLineItem reqItem:subReqSelectList) {
				RequirementDTO reqDto = new RequirementDTO();
				reqDto.setReqType(reqItem.getReqType());
				reqDto.setHowManyToChoose(reqItem.getHowManyToChoose());
				reqDto.setOptionCount(reqItem.getOptionCount());
				reqDto.setSubRequirements(reqItem.getSubRequirements());
				requirements.add(reqDto);
			}
			return requirements;
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
		
		String jsonStr = mbObj.get("requirementsStr").isString().stringValue().replaceAll("@@", "\"");
//		Window.alert("Got requirementsStr: " + jsonStr);
		JSONValue reqsValue = JSONParser.parseLenient(jsonStr);
		JSONArray reqsArray = reqsValue.isArray();
		List<RequirementDTO> reqDTOList = parseRequirementsJsonData(reqsArray);
		mb.setRequirements(reqDTOList);
		
		return mb;
	}

	private List<RequirementDTO> parseRequirementsJsonData(JSONArray reqsArray) {
//		Window.alert("Got here with array of size "+reqsArray.size());
		List<RequirementDTO> requirements = new ArrayList<RequirementDTO>();

		for (int i=0; i<reqsArray.size(); i++) {
			JSONObject reqObj = reqsArray.get(i).isObject();
//			Window.alert("Got here with reqObj "+reqObj);
			RequirementDTO reqDTO = new RequirementDTO();
			reqDTO.setHowManyToChoose(Integer.parseInt(reqObj.get("howManyToChoose").isString().stringValue()));
			reqDTO.setOptionCount(Integer.parseInt(reqObj.get("optionCount").isString().stringValue()));
			reqDTO.setReqType(reqObj.get("reqType").isString().stringValue());
			reqDTO.setSubRequirements(parseRequirementsJsonData(reqObj.get("subRequirements").isArray()));
			requirements.add(reqDTO);
		}
		
		return requirements;
	}

}
