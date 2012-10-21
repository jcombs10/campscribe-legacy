package com.campscribe.business;

import java.util.List;

import com.campscribe.dao.ImportedFileDao;
import com.campscribe.model.ImportedFile;
import com.googlecode.objectify.Key;

public class ImportedFileManager extends BaseManager {
	
	public Key<ImportedFile> addImportedFile(ImportedFile impFile) {
		return ImportedFileDao.INSTANCE.add(impFile);
	}

	public void deleteImportedFile(long id) {
		ImportedFileDao.INSTANCE.remove(id);
	}

	public ImportedFile getImportedFile(long id) {
		return ImportedFileDao.INSTANCE.get(new Key<ImportedFile>(ImportedFile.class, id));
	}

	public List<ImportedFile> listImportedFiles() {
		return ImportedFileDao.INSTANCE.listImportedFiles();
	}

	public void updateImportedFile(ImportedFile impFile) {
		ImportedFileDao.INSTANCE.update(impFile);
	}

}
