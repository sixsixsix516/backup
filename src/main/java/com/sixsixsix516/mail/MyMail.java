package com.sixsixsix516.mail;

import com.sixsixsix516.backup.mysql.MysqlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.time.LocalDate;

/**
 * @author sun 2020/10/27 11:40
 */
@Component
public class MyMail {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sourceEmail;

	/**
	 * 发送备份文件
	 *
	 * @param filePath    要发送的sql文件路径
	 */
	public void sendBackFile(String filePath) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(sourceEmail);
			helper.setTo(MysqlProperties.sendToEmail);
			helper.setSubject(MysqlProperties.title + LocalDate.now());
			helper.setText(MysqlProperties.title);
			// 要发送的文件
			FileSystemResource file = new FileSystemResource(filePath);
			String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
			helper.addAttachment(fileName, file);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}


}
