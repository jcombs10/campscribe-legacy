package com.campscribe.dao;

import java.util.Collections;
import java.util.List;

import com.campscribe.model.ImportedFile;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public enum ImportedFileDao {
	INSTANCE;

	public Key<ImportedFile> add(ImportedFile ci) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			return ofy.put(ci);
		}
	}

	public ImportedFile get(Key<ImportedFile> ciKey) {
		Objectify ofy = ObjectifyService.begin();
		ImportedFile ci = ofy.get(ciKey);
		return ci;
	}

	public List<ImportedFile> listImportedFiles() {
		Objectify ofy = ObjectifyService.begin();
		List<ImportedFile> allImportedFiles = ofy.query(ImportedFile.class).list();
		return allImportedFiles;
	}

	public void remove(long id) {
		Objectify ofy = ObjectifyService.begin();
		Key<ImportedFile> ci = new Key<ImportedFile>(ImportedFile.class, id);
		ofy.delete(ci);
	}

	public void update(ImportedFile ci) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			ofy.put(ci);
		}
	}

}