package com.srikanthdev.bootsftp.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import com.srikanthdev.bootsftp.model.Order;

public class BootSftpService {

	private static final Log LOG = LogFactory.getLog(BootSftpService.class);

	private MessageChannel sftpChannel;

	private MessageChannel deleteChannel;

	private static final String COMMA_DELIMITER = ",";
	private static final String LINE_SEPARATOR = "\n";

	private static final String FILE_NAME = "orders.csv";

	@Value("${sftpserver.folder}")
	private String metadataRemoteDirectory;

	@Value("${sftpserver.delete.file.format}")
	private String remoteFileNameFormat;

	private static final String REMOTE_FILE_DIR = "file_remoteDirectory";
	private static final String REMOTE_FILE = "file_remoteFile";

	public MessageChannel getSftpChannel() {
		return sftpChannel;
	}

	public void setSftpChannel(MessageChannel sftpChannel) {
		this.sftpChannel = sftpChannel;
	}

	public MessageChannel getDeleteChannel() {
		return deleteChannel;
	}

	public void setDeleteChannel(MessageChannel deleteChannel) {
		this.deleteChannel = deleteChannel;
	}

	public void createFileAndPushToSFTP(List<Order> orderList) {
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(FILE_NAME);
			for (Order order : orderList) {
				fileWriter.append(order.getOrderId());
				fileWriter.append(COMMA_DELIMITER);

				fileWriter.append(order.getProductName());
				fileWriter.append(COMMA_DELIMITER);

				fileWriter.append(order.getCustomerName());
				fileWriter.append(COMMA_DELIMITER);

				fileWriter.append(order.getCustomerAddress());
				fileWriter.append(LINE_SEPARATOR);
			}
		} catch (Exception e1) {
			LOG.info("Exception while wrting to file");
		} finally {
			try {
				fileWriter.close();
			} catch (IOException ie) {
				LOG.error("Error while closing the filewriter", ie);
			}
		}

		File file = new File(FILE_NAME);
		if (file.exists()) {
			LOG.info("File Ready to Upload");
			Message<File> message = MessageBuilder.withPayload(file).build();
			try {
				sftpChannel.send(message);
				LOG.info("Successfully uploaded to sftp");
			} catch (Exception e) {
				LOG.error("Error while sending file to sftp folder : ", e);
			}
		} else {
			LOG.info("File does not exist, May be no records to process");
		}
	}

	public void deleteFileFromSFTP() {

		LOG.info("Delete of file from SFTP started");
		org.springframework.messaging.Message<String> rmRequest = MessageBuilder.withPayload(metadataRemoteDirectory)
				.setHeader(REMOTE_FILE_DIR, metadataRemoteDirectory).setHeader(REMOTE_FILE, remoteFileNameFormat)
				.build();
		try {
			deleteChannel.send(rmRequest);
			LOG.info("Successfully deleted file from sftp");
		} catch (Exception e) {
			LOG.error("Exception while deleting file from sftp : ", e);
		}

	}
}
