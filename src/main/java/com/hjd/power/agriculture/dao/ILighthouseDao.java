package com.hjd.power.agriculture.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hjd.power.agriculture.domain.LighthouseVO;

public interface ILighthouseDao {
	List<LighthouseVO> findList();

	LighthouseVO find(@Param("lighthouseId") Integer lighthouseId);

	Integer create(LighthouseVO vo);

	Integer update(LighthouseVO vo);

	Integer delete(LighthouseVO vo);
}
