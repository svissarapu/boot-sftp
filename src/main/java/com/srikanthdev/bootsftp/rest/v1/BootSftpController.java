package com.srikanthdev.bootsftp.rest.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.srikanthdev.bootsftp.model.Order;
import com.srikanthdev.bootsftp.service.BootSftpService;

@RestController
@RequestMapping("/bootsftp")
public class BootSftpController {

	@Autowired
	private BootSftpService service;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void publishToSFTP(@RequestBody List<Order> orderList) {
		service.createFileAndPushToSFTP(orderList);
	}

	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	public void deleteFromSFTP() {
		service.deleteFileFromSFTP();
	}

}
