package com.campscribe.model;

import org.springframework.web.multipart.MultipartFile;

public class UploadItem {
        private MultipartFile fileData;

		public MultipartFile getFileData() {
			return fileData;
		}

		public void setFileData(MultipartFile fileData) {
			this.fileData = fileData;
		}

}