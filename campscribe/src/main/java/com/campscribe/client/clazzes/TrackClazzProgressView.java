package com.campscribe.client.clazzes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.campscribe.client.CampScribeBodyWidget;
import com.campscribe.shared.NoteDTO;
import com.campscribe.shared.ScoutDTO;
import com.campscribe.shared.TrackProgressDTO;
import com.campscribe.shared.TrackProgressDTO.DateAttendanceDTO;
import com.campscribe.shared.TrackProgressDTO.RequirementCompletionDTO;
import com.campscribe.shared.TrackProgressWrapperDTO;
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
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class TrackClazzProgressView extends Composite implements CampScribeBodyWidget {

	interface MyStyle extends CssResource {
		String noteAuthor();
		String noteTimestamp();
		String tabBar();
		String tab();
		String tabSelected();
		String trackingTable();
	}

	private static DateTimeFormat dateFormatterNoTime = DateTimeFormat.getFormat("EEE");

	@UiField MyStyle style;
	@UiField Label attendanceTab;
	@UiField Label requirementsTab;
	@UiField Label commentsTab;
	@UiField FlowPanel attendanceContent;
	@UiField FlexTable attendanceTable;
	@UiField FlowPanel requirementsContent;
	@UiField FlexTable requirementsTable;
	@UiField FlowPanel commentsContent;
	@UiField FlowPanel notesList;
	@UiField TextArea noteTextArea;
	
	private static DateTimeFormat fmt = DateTimeFormat.getFormat("EEE MM/dd/yy");

	ClazzService clazzService = new ClazzServiceJSONImpl();

	private Long clazzId = Long.valueOf(-1);
	private Long eventId = Long.valueOf(-1);
	private List<TrackProgressDTO> progressList = new ArrayList<TrackProgressDTO>();

	ArrayList<ArrayList<CheckBox>> attendanceCheckboxes = new ArrayList<ArrayList<CheckBox>>();
	ArrayList<CheckBox> selectAllCheckboxes = new ArrayList<CheckBox>();
	ArrayList<ArrayList<CheckBox>> reqCheckboxes = new ArrayList<ArrayList<CheckBox>>();
	ArrayList<CheckBox> selectAllReqCheckboxes = new ArrayList<CheckBox>();

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
				TrackProgressWrapperDTO wrapper = parseTrackProgressJsonData(str);
				for (NoteDTO n:wrapper.getNotesList()) {
					HorizontalPanel hp = new HorizontalPanel();

					Label timestampLabel = new Label(fmt.format(n.getDate()));
					timestampLabel.setStyleName(style.noteTimestamp());
					hp.add(timestampLabel);
					
					Label authorLabel = new Label(n.getStaffName());
					authorLabel.setStyleName(style.noteAuthor());
					hp.add(authorLabel);
					
					hp.add(new Label(n.getNoteText()));

					notesList.add(hp);
				}
				progressList = wrapper.getTrackingList();
				int row = 2;
				boolean foundAllSelected[] = null;
				boolean foundAllReqSelected[] = null;
				
				Label attendanceLbl = new Label("Select All");
				attendanceLbl.addStyleName("italic");
				attendanceTable.setWidget(1, 0, attendanceLbl);
				attendanceTable.getCellFormatter().addStyleName(1, 0, "borderBottom");

				Label reqLbl = new Label("Select All");
				reqLbl.addStyleName("italic");
				requirementsTable.setWidget(1, 0, reqLbl);
				requirementsTable.getCellFormatter().addStyleName(1, 0, "borderBottom");

				for (TrackProgressDTO t:progressList) {
					if (foundAllSelected == null) {
						foundAllSelected = new boolean[t.getAttendanceList().size()];
						for (int i=0; i<t.getAttendanceList().size(); i++) {
							foundAllSelected[i] = true;
						}
//						Window.alert("foundAllSelected initialized");
					}
					if (foundAllReqSelected == null) {
						foundAllReqSelected = new boolean[t.getRequirementList().size()];
						for (int i=0; i<t.getRequirementList().size(); i++) {
							foundAllReqSelected[i] = true;
						}
//						Window.alert("foundAllReqSelected initialized");
					}

					ArrayList<CheckBox> attendanceListForSingleScout = new ArrayList<CheckBox>(); 
					attendanceCheckboxes.add(attendanceListForSingleScout);
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
							attendanceTable.getCellFormatter().addStyleName(1, column, "borderBottom");
						}

						CheckBox cb = new CheckBox();
						cb.setValue(t.getAttendanceList().get(column-1).isPresent());
						if (!t.getAttendanceList().get(column-1).isPresent()) {
							foundAllSelected[column-1] = false;
						}
//						Window.alert("foundAllSelected="+foundAllSelected);
						cb.addClickHandler(new AttendanceClickHandler(column));
						attendanceListForSingleScout.add(cb);
						attendanceTable.setWidget(row, column++, cb);
					}

					ArrayList<CheckBox> reqsListForSingleScout = new ArrayList<CheckBox>(); 
					reqCheckboxes.add(reqsListForSingleScout);
					requirementsTable.setWidget(row, 0, new Label(t.getScout().getDisplayName()));
					column = 1;
					for (RequirementCompletionDTO rc:t.getRequirementList()) {
						int topLevelReqNbr = 0;
						if (rc.getReqNumber().contains(".")) {
							int dotIndex = rc.getReqNumber().indexOf('.');
							String reqNbrStr = rc.getReqNumber().substring(0, dotIndex); 
							topLevelReqNbr = Integer.parseInt(reqNbrStr);
						} else {
							topLevelReqNbr = Integer.parseInt(rc.getReqNumber());
						}
						String shadingStyle = ((topLevelReqNbr%2==0)?"even":"odd");
						if (row == 2) {
							requirementsTable.setWidget(0, column, new Label(rc.getReqNumber()));
							requirementsTable.getCellFormatter().addStyleName(0, column, "center");
							requirementsTable.getCellFormatter().addStyleName(0, column, shadingStyle);
							CheckBox cb = new CheckBox();
							cb.addClickHandler(new SelectAllReqClickHandler(column));
							selectAllReqCheckboxes.add(cb);
							requirementsTable.setWidget(1, column, cb);
							requirementsTable.getCellFormatter().addStyleName(1, column, shadingStyle);
							requirementsTable.getCellFormatter().addStyleName(1, column, "borderBottom");
						}

						CheckBox cb = new CheckBox();
						cb.setValue(t.getRequirementList().get(column-1).isCompleted());
						if (!t.getRequirementList().get(column-1).isCompleted()) {
							foundAllReqSelected[column-1] = false;
						}
						cb.addClickHandler(new RequirementClickHandler(column));
						reqsListForSingleScout.add(cb);
						requirementsTable.setWidget(row, column, cb);
						requirementsTable.getCellFormatter().addStyleName(row, column, shadingStyle);
						column++;
					}

					row++;
				}
				for (int i=0; i<selectAllCheckboxes.size(); i++) {
					selectAllCheckboxes.get(i).setValue(foundAllSelected[i]);
				}
				for (int i=0; i<selectAllReqCheckboxes.size(); i++) {
					selectAllReqCheckboxes.get(i).setValue(foundAllReqSelected[i]);
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		});
	}

	private TrackProgressWrapperDTO parseTrackProgressJsonData(String str) {
//		Window.alert("Got response: " + str);
		TrackProgressWrapperDTO wrapper = new TrackProgressWrapperDTO();
		
		List<NoteDTO> noteDtoList = new ArrayList<NoteDTO>();
		List<TrackProgressDTO> dtoList = new ArrayList<TrackProgressDTO>();

		JSONValue value = JSONParser.parseLenient(str);
		JSONObject wrapperObj = value.isObject();
//		String commentsStr = wrapperObj.get("comments").isString().stringValue();
//		wrapper.setComments(commentsStr.replaceAll("_newline_","\n").replaceAll("_doublequote_","\""));
		
		JSONArray notesArray = wrapperObj.get("notesList").isArray();

		if (notesArray != null) {
			for (int i=0; i<=notesArray.size()-1; i++) {
				NoteDTO nd = new NoteDTO();
				JSONObject noteObj = notesArray.get(i).isObject();
				String staffName = noteObj.get("staffName").isString().stringValue();
				double date = noteObj.get("date").isNumber().doubleValue();
//				Window.alert("got noteDate "+date);
				Date noteDate = new Date(Double.valueOf(date).longValue());
				String noteText = noteObj.get("noteText").isString().stringValue();
				nd.setStaffName(staffName);
				nd.setDate(noteDate);
				nd.setNoteText(noteText);
				noteDtoList.add(nd);
			}
		}
		wrapper.setNotesList(noteDtoList);
		
		JSONArray tpArray = wrapperObj.get("trackingList").isArray();

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

				JSONArray requirementList = tpObj.get("requirementList").isArray();
				List<RequirementCompletionDTO> reqList = new ArrayList<RequirementCompletionDTO>();
				if (requirementList != null) {
					for (int j=0; j<=requirementList.size()-1; j++) {
						RequirementCompletionDTO reqDTO = new RequirementCompletionDTO();
						JSONObject reqObj = requirementList.get(j).isObject();

						Boolean completed = reqObj.get("completed").isBoolean().booleanValue();
						String reqNumber = reqObj.get("reqNumber").isString().stringValue();
						reqDTO.setCompleted(completed);
						reqDTO.setReqNumber(reqNumber);

						reqList.add(reqDTO);
					}
				}
				tpDTO.setRequirementList(reqList);

				dtoList.add(tpDTO);
			}
		}

		wrapper.setTrackingList(dtoList);
		return wrapper;
	}


	@Override
	public void onSave() {
		clazzService.updateClazzTracking(eventId, clazzId, getData());
	}

	private TrackProgressWrapperDTO getData() {
		TrackProgressWrapperDTO wrapper = new TrackProgressWrapperDTO();
//		wrapper.setComments(noteTextArea.getText().replaceAll("\n","_newline_").replaceAll("\"","_doublequote_"));
		wrapper.setComments(noteTextArea.getText().replaceAll("\n"," ").replaceAll("\""," "));
		
		int i = 0;
		for (ArrayList<CheckBox> aScoutsCheckboxes:attendanceCheckboxes) {
			int j = 0;
//			Window.alert("updating scout "+progressList.get(i).getScout().getDisplayName());
			for (CheckBox cb:aScoutsCheckboxes) {
//				Window.alert("attendance for date "+progressList.get(i).getAttendanceList().get(j).getDate());
				progressList.get(i).getAttendanceList().get(j).setPresent(cb.getValue());
				j++;
			}
			i++;
		}

		int k = 0;
		for (ArrayList<CheckBox> aScoutsCheckboxes:reqCheckboxes) {
			int l = 0;
//			Window.alert("updating scout "+progressList.get(k).getScout().getDisplayName());
			for (CheckBox cb:aScoutsCheckboxes) {
//				Window.alert("attendance for date "+progressList.get(k).getAttendanceList().get(l).getDate());
				progressList.get(k).getRequirementList().get(l).setCompleted(cb.getValue());
				l++;
			}
			k++;
		}
wrapper.setTrackingList(progressList);
		return wrapper;
	}

	@Override
	public void onCancel() {
	}

	@UiHandler("attendanceTab")
	public void showAttendanceTab(ClickEvent ce) {
		attendanceContent.setVisible(true);
		requirementsContent.setVisible(false);
		commentsContent.setVisible(false);
		attendanceTab.addStyleName(style.tabSelected());
		requirementsTab.removeStyleName(style.tabSelected());
		commentsTab.removeStyleName(style.tabSelected());
	}

	@UiHandler("requirementsTab")
	public void showRequirementsTab(ClickEvent ce) {
		attendanceContent.setVisible(false);
		requirementsContent.setVisible(true);
		commentsContent.setVisible(false);
		attendanceTab.removeStyleName(style.tabSelected());
		requirementsTab.addStyleName(style.tabSelected());
		commentsTab.removeStyleName(style.tabSelected());
	}

	@UiHandler("commentsTab")
	public void showCommentsTab(ClickEvent ce) {
		attendanceContent.setVisible(false);
		requirementsContent.setVisible(false);
		commentsContent.setVisible(true);
		attendanceTab.removeStyleName(style.tabSelected());
		requirementsTab.removeStyleName(style.tabSelected());
		commentsTab.addStyleName(style.tabSelected());
	}

	public class SelectAllClickHandler implements ClickHandler {

		private int whichOne;

		public SelectAllClickHandler(int whichOne) {
			this.whichOne = whichOne-1;
		}

		@Override
		public void onClick(ClickEvent event) {
			for (List<CheckBox> cb:TrackClazzProgressView.this.attendanceCheckboxes) {
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

	public class SelectAllReqClickHandler implements ClickHandler {

		private int whichOne;

		public SelectAllReqClickHandler(int whichOne) {
			this.whichOne = whichOne-1;
		}

		@Override
		public void onClick(ClickEvent event) {
			for (List<CheckBox> cb:TrackClazzProgressView.this.reqCheckboxes) {
				cb.get(whichOne).setValue(selectAllReqCheckboxes.get(whichOne).getValue());
			}
		}

	}

	public class RequirementClickHandler implements ClickHandler {

		private int whichOne;

		public RequirementClickHandler(int whichOne) {
			this.whichOne = whichOne-1;
		}

		@Override
		public void onClick(ClickEvent event) {
			if (!((CheckBox)event.getSource()).getValue()) {
				selectAllReqCheckboxes.get(whichOne).setValue(Boolean.FALSE);
			}
		}

	}

}
