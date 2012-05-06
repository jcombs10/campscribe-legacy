package com.campscribe.controller.web;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.campscribe.model.UploadItem;
import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;


@Controller
@RequestMapping(value = "/upload.cs")
public class UploadController {
	private static final Logger log = Logger.getLogger(UploadController.class.getName());

	@RequestMapping(method = RequestMethod.GET)
	public String getUploadForm(Model model)
	{
		model.addAttribute(new UploadItem());
		return "upload.jsp";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String uploadClassRegistration(UploadItem commentForm) throws IOException {

		MultipartFile fileData = commentForm.getFileData();

		DataInputStream dis = new DataInputStream(fileData.getInputStream());
		int formDataLength = (int) fileData.getSize();
		byte dataBytes[] = new byte[formDataLength];
		int byteRead = 0;
		int totalBytesRead = 0;
		//this loop converting the uploaded file into byte code
		while (totalBytesRead < formDataLength) {
		  byteRead = dis.read(dataBytes, totalBytesRead,formDataLength);
		  totalBytesRead += byteRead;
		}
		String file = new String(dataBytes);
		log.log(Level.FINE, "adding to OrderQueue {0}", file);
		// Create Task and push it into Task Queue
		createBackendTask(file);
		
		return "redirect:/admin.cs"; // redirect after processing!!!
	}

	private void createBackendTask(String data) {
		 // Create Task and push it into Task Queue
		Queue queue = QueueFactory.getQueue("UploadQueue");
		TaskOptions taskOptions = TaskOptions.Builder.withUrl("/processDoubleknot")
		                          .param("csv", data)
		                          .header("Host", BackendServiceFactory.getBackendService().getBackendAddress("doubleknot-backend"))
		                          .method(Method.POST);
		queue.add(taskOptions);	
	}
}