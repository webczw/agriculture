package com.hjd.power.agriculture;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.hjd.power.agriculture.utils.AESUtils;

@Component
public class ApplicationAware implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		JavaMailSenderImpl javaMailSenderImpl = arg0.getBean(JavaMailSenderImpl.class);
		String password = AESUtils.decrypt(javaMailSenderImpl.getPassword(), Constants.AES_KEY, Constants.AES_IV);
		javaMailSenderImpl.setPassword(password);
	}

}
