package com.hjd.power.agriculture.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hjd.power.agriculture.domain.SensorVO;

public interface ISensorDao {
	List<SensorVO> findList();

	SensorVO find(@Param("sensorId") Integer sensorId);

	Integer create(SensorVO vo);

	Integer update(SensorVO vo);

	Integer delete(SensorVO vo);
}
