package com.hjd.power.agriculture.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hjd.power.agriculture.domain.MainVO;

public interface IMainDao {
	List<MainVO> findList();

	MainVO find(@Param("mainId") Integer mainId);

	Integer create(MainVO vo);

	Integer update(MainVO vo);

	Integer delete(MainVO vo);
}
