package com.srikanthdev.bootsftp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("integration.xml")
public class BootSftpApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootSftpApplication.class, args);
	}
}
