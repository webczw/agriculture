package com.hjd.power.agriculture.service.impl;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hjd.power.agriculture.domain.SimpleMailMessageVO;
import com.hjd.power.agriculture.service.IEmailService;

@Service
public class EmailService implements IEmailService {
	private Logger logger = LoggerFactory.getLogger(EmailService.class);
	@Autowired
	private JavaMailSender mailSender; // 自动注入的Bean

	@Value("${spring.mail.username}")
	private String Sender; // 读取配置文件中的参数

	@Override
	public void sendSimpleMail(SimpleMailMessageVO vo) throws Exception {
		MimeMessage message = null;
		try {
			message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(Sender);
			helper.setTo(vo.getTo());
			helper.setSubject(vo.getSubject());
			helper.setText(vo.getText());
			// 注意项目路径问题，自动补用项目路径
			FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/map.png"));
			// 加入邮件
			helper.addAttachment("图片.jpg", file);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("sendSimpleMail-->Error", e);
		}
		mailSender.send(message);

	}

	@Override
	public void sendMail(SimpleMailMessageVO vo, File file) throws Exception {
		MimeMessage message = null;
		try {
			message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(Sender);
			String to = vo.getTo();
			if (to.indexOf(";") >= 0) {
				helper.setTo(to.toString().split(";"));
			} else {
				helper.setTo(to);
			}

			helper.setSubject(vo.getSubject());
			helper.setText(vo.getText());
			// 注意项目路径问题，自动补用项目路径
			FileSystemResource resource = new FileSystemResource(file);
			// 加入邮件
			helper.addAttachment(file.getName(), resource);
			logger.info("sendMail-->End");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("sendMail-->Error", e);
		}
		mailSender.send(message);
	}

}
