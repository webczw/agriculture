package com.hjd.power.agriculture.service;

import java.io.File;

import com.hjd.power.agriculture.domain.SimpleMailMessageVO;

public interface IMailService {
	void sendSimpleMail(SimpleMailMessageVO vo) throws Exception;

	void sendMail(SimpleMailMessageVO vo, File file) throws Exception;
}
