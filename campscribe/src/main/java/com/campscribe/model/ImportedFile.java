package com.campscribe.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ImportedFile {
    @Id
    private Long id;
    private String csvText = "";
    private String originalFileName = "";
    
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCsvText() {
		return csvText;
	}
	
	public void setCsvText(String csvText) {
		this.csvText = csvText;
	}
	
	public String getOriginalFileName() {
		return originalFileName;
	}
	
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	
}
