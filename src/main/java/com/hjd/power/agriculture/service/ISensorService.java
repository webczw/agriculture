package com.hjd.power.agriculture.service;

import java.util.List;

import com.hjd.power.agriculture.domain.SensorVO;

public interface ISensorService {
	List<SensorVO> findList() throws Exception;

	SensorVO find(Integer sensorId) throws Exception;

	Integer create(SensorVO vo) throws Exception;

	Integer update(SensorVO vo) throws Exception;

	Integer delete(Integer sensorId) throws Exception;
}
