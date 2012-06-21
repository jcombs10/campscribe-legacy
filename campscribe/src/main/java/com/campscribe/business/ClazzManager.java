package com.campscribe.business;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import com.campscribe.auth.CampScribeUser;
import com.campscribe.dao.ClazzDao;
import com.campscribe.model.Clazz;
import com.campscribe.model.Clazz.Note;
import com.campscribe.model.Event;
import com.campscribe.model.Scout;
import com.campscribe.model.Staff;
import com.googlecode.objectify.Key;



public class ClazzManager extends BaseManager {

	private StaffManager staffMgr = new StaffManager();;

	public void addClazz(Clazz c) {
		ClazzDao.INSTANCE.add(c);
	}

	public void deleteClazz(long id) {
		ClazzDao.INSTANCE.remove(id);
	}

	public Clazz getClazz(Key<Clazz> clazzKey) {
		return ClazzDao.INSTANCE.get(clazzKey);
	}

	public List<Clazz> listClazzes() {
		return ClazzDao.INSTANCE.listClazzes();
	}

	public List<Clazz> listClazzes(Key<Event> eventKey) {
		return ClazzDao.INSTANCE.listClazzes(eventKey);
	}

	public void addScoutsToClazz(Key<Clazz> clazzKey, List<Key<Scout>> scoutList) {
		ClazzDao.INSTANCE.addScoutsToClazz(clazzKey, scoutList);
	}

	public void updateComments(Long eventId, Long clazzId, String comments) {
		Key<Event> eKey = new Key<Event>(Event.class, eventId);
		Clazz c = ClazzDao.INSTANCE.get(new Key<Clazz>(eKey, Clazz.class, clazzId));
		Note note = new Note();
		note.setNoteText(comments);
		note.setDate(new Date());
		
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CampScribeUser) {
			CampScribeUser user = (CampScribeUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String name = user.getUsername(); //get logged in username
			Staff s = staffMgr.getStaffByName(name);
			note.setStaffName(s.getName());
			note.setStaffKey(new Key<Staff>(Staff.class, s.getId()));
		} else {
			note.setStaffName("System Administrator");
		}

		c.addNote(note );
		ClazzDao.INSTANCE.update(c);
	}

}
