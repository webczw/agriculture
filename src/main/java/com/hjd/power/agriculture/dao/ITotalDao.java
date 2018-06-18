package com.hjd.power.agriculture.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hjd.power.agriculture.domain.TotalVO;

public interface ITotalDao {
	List<TotalVO> findList();

	TotalVO find(@Param("totalId") Integer totalId);

	Integer create(TotalVO vo);

	Integer update(TotalVO vo);

	Integer delete(@Param("totalId") Integer totalId);
}
