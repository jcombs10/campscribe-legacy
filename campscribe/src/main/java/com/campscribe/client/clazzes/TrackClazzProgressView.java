package com.campscribe.client.clazzes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.shared.ScoutDTO;
import com.campscribe.shared.TrackProgressDTO;
import com.campscribe.shared.TrackProgressDTO.DateAttendanceDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TrackClazzProgressView extends Composite implements CampScribeBodyWidget {

	private static DateTimeFormat dateFormatterNoTime = DateTimeFormat.getFormat("EEE");

	@UiField FlexTable attendanceTable;

	ClazzService clazzService = new ClazzServiceJSONImpl();

	private Long clazzId = Long.valueOf(-1);
	private Long eventId = Long.valueOf(-1);
	private List<TrackProgressDTO> progressList = new ArrayList<TrackProgressDTO>();
	
	ArrayList<ArrayList<CheckBox>> checkboxes = new ArrayList<ArrayList<CheckBox>>();
	ArrayList<CheckBox> selectAllCheckboxes = new ArrayList<CheckBox>();

	private static TrackClazzProgressViewUiBinder uiBinder = GWT
			.create(TrackClazzProgressViewUiBinder.class);

	interface TrackClazzProgressViewUiBinder extends
	UiBinder<Widget, TrackClazzProgressView> {
	}

	public TrackClazzProgressView(Long eventId, Long clazzId) {
		this.eventId = eventId;
		this.clazzId = clazzId;
		
		initWidget(uiBinder.createAndBindUi(this));
		
		clazzService.getClazzTracking(eventId, clazzId, new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				String str = response.getText();
				progressList = parseTrackProgressJsonData(str);
				int row = 2;
				boolean foundAllSelected[] = null;
				for (TrackProgressDTO t:progressList) {
					ArrayList<CheckBox> l = new ArrayList<CheckBox>(); 
					checkboxes.add(l);
					attendanceTable.setWidget(row, 0, new Label(t.getScout().getDisplayName()));
					int column = 1;
					for (DateAttendanceDTO da:t.getAttendanceList()) {
						if (row == 2) {
							//hacking my way around time zone adjustments by setting the date to end of day
							Date hackDate = new Date(da.getDate().getTime()+(12*60*60*1000));
							attendanceTable.setWidget(0, column, new Label(dateFormatterNoTime.format(hackDate)));
							CheckBox cb = new CheckBox();
							cb.addClickHandler(new SelectAllClickHandler(column));
							selectAllCheckboxes.add(cb);
							attendanceTable.setWidget(1, column, cb);
							foundAllSelected = new boolean[t.getAttendanceList().size()];
							for (int i=0; i<t.getAttendanceList().size(); i++) {
								foundAllSelected[i] = true;
							}
						}
						
						CheckBox cb = new CheckBox();
						cb.setValue(t.getAttendanceList().get(column-1).isPresent());
						if (!t.getAttendanceList().get(column-1).isPresent()) {
							foundAllSelected[column-1] = false;
						}
						cb.addClickHandler(new AttendanceClickHandler(column));
						checkboxes.get(row-2).add(cb);
						attendanceTable.setWidget(row, column++, cb);
					}
					row++;
				}
				for (int i=0; i<selectAllCheckboxes.size(); i++) {
					selectAllCheckboxes.get(i).setValue(foundAllSelected[i]);
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		});
	}

	private List<TrackProgressDTO> parseTrackProgressJsonData(String str) {
//		Window.alert("Got response: " + str);
		List<TrackProgressDTO> dtoList = new ArrayList<TrackProgressDTO>();
		
		JSONValue value = JSONParser.parseLenient(str);
		JSONArray tpArray = value.isArray();

		if (tpArray != null) {
			for (int i=0; i<=tpArray.size()-1; i++) {
				TrackProgressDTO tpDTO = new TrackProgressDTO();
				JSONObject tpObj = tpArray.get(i).isObject();

				double id = tpObj.get("id").isNumber().doubleValue();
				Double d = Double.valueOf(id);
				tpDTO.setId(d.longValue());
				JSONObject scout = tpObj.get("scout").isObject();
				ScoutDTO scoutDTO = new ScoutDTO();
				id = scout.get("id").isNumber().doubleValue();
				d = Double.valueOf(id);
				String firstName = scout.get("firstName").isString().stringValue();
				String lastName = scout.get("lastName").isString().stringValue();
//				Window.alert("found scout " + firstName + " " + lastName);
				scoutDTO.setId(d.longValue());
				scoutDTO.setFirstName(firstName);
				scoutDTO.setLastName(lastName);
				tpDTO.setScout(scoutDTO);

				JSONArray attendanceList = tpObj.get("attendanceList").isArray();
				List<DateAttendanceDTO> attList = new ArrayList<DateAttendanceDTO>();
				if (attendanceList != null) {
					for (int j=0; j<=attendanceList.size()-1; j++) {
						DateAttendanceDTO attDTO = new DateAttendanceDTO();
						JSONObject attObj = attendanceList.get(j).isObject();

						long time = (long) attObj.get("date").isNumber().doubleValue();
						Long l = Long.valueOf(time);
						Boolean present = attObj.get("present").isBoolean().booleanValue();
						attDTO.setDate(new Date(l));
						attDTO.setPresent(present);

						attList.add(attDTO);
					}
				}
				tpDTO.setAttendanceList(attList);
				dtoList.add(tpDTO);
			}
		}

		return dtoList;
	}


	@Override
	public void onSave() {
		clazzService.updateClazzTracking(eventId, clazzId, getData());
	}

	private List<TrackProgressDTO> getData() {
		int i = 0;
		for (ArrayList<CheckBox> aScoutsCheckboxes:checkboxes) {
			int j = 0;
			for (CheckBox cb:aScoutsCheckboxes) {
//				Window.alert("updating scout "+progressList.get(i).getScout().getDisplayName());
				progressList.get(i).getAttendanceList().get(j).setPresent(cb.getValue());
				j++;
			}
			i++;
		}
		return progressList;
	}

	@Override
	public void onCancel() {
	}

	public class SelectAllClickHandler implements ClickHandler {
		
		private int whichOne;

		public SelectAllClickHandler(int whichOne) {
			this.whichOne = whichOne-1;
		}

		@Override
		public void onClick(ClickEvent event) {
			for (List<CheckBox> cb:TrackClazzProgressView.this.checkboxes) {
				cb.get(whichOne).setValue(selectAllCheckboxes.get(whichOne).getValue());
			}
		}

	}

	public class AttendanceClickHandler implements ClickHandler {
		
		private int whichOne;

		public AttendanceClickHandler(int whichOne) {
			this.whichOne = whichOne-1;
		}

		@Override
		public void onClick(ClickEvent event) {
			if (!((CheckBox)event.getSource()).getValue()) {
				selectAllCheckboxes.get(whichOne).setValue(Boolean.FALSE);
			}
		}

	}

}
