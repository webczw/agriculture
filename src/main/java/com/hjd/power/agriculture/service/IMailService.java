package com.hjd.power.agriculture.service;

import com.hjd.power.agriculture.domain.SimpleMailMessageVO;

public interface IMailService {
	void sendSimpleMail(SimpleMailMessageVO vo) throws Exception;
}
