package com.hjd.power.agriculture.service;

import java.util.List;

import com.hjd.power.agriculture.domain.LighthouseQueryVO;
import com.hjd.power.agriculture.domain.LighthouseVO;

public interface ILighthouseService {
	List<LighthouseVO> findList() throws Exception;

	LighthouseVO find(Integer lighthouseId) throws Exception;

	Integer create(LighthouseVO vo) throws Exception;

	Integer update(LighthouseVO vo) throws Exception;

	Integer delete(Integer lighthouseId) throws Exception;

	List<LighthouseVO> findListDetail(LighthouseQueryVO vo) throws Exception;
}
