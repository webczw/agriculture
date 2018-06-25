package com.hjd.power.agriculture.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hjd.power.agriculture.domain.ConfigVO;

public interface IConfigDao {
	List<ConfigVO> findList();

	ConfigVO find(@Param("configId") Integer configId);

	Integer create(ConfigVO vo);

	Integer batchUpdate(List<ConfigVO> list);

	Integer delete(ConfigVO vo);
}
