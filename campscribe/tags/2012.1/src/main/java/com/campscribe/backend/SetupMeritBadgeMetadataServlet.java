package com.campscribe.backend;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.campscribe.business.MeritBadgeManager;
import com.campscribe.business.MeritBadgeMetadataManager;
import com.campscribe.business.StaffManager;
import com.campscribe.model.MeritBadge;
import com.campscribe.model.MeritBadgeMetadata;
import com.campscribe.model.Staff;
import com.googlecode.objectify.Key;

public class SetupMeritBadgeMetadataServlet extends HttpServlet {

	private static final long serialVersionUID = -4984668055373587237L;

	private static final Logger log = Logger.getLogger(SetupMeritBadgeMetadataServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		StaffManager staffManager = new StaffManager();
		MeritBadgeManager meritBadgeManager = new MeritBadgeManager();
		MeritBadgeMetadataManager meritBadgeMetadataManager = new MeritBadgeMetadataManager();

		for (MeritBadge mb:meritBadgeManager.listMeritBadges()) {
			MeritBadgeMetadata mbMd = meritBadgeMetadataManager.getMetadataForBadge(new Key<MeritBadge>(MeritBadge.class, mb.getId()));
			if (mbMd == null) {
				mbMd = new MeritBadgeMetadata();
				mbMd.setMbKey(new Key<MeritBadge>(MeritBadge.class, mb.getId()));
			}
			mbMd.setProgramArea("Aquatics");
			List<Staff> staffList = staffManager.listStaff();
			if (staffList.size() > 0) {
				mbMd.setStaffKey(new Key<Staff>(Staff.class, staffList.get(0).getId()));
			}
			meritBadgeMetadataManager.addUpdate(mbMd);
		}
		
		log.severe("finished Merit Badge Metadata processing");
		
	}

}
