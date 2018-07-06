package com.hjd.power.agriculture.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hjd.power.agriculture.domain.QuartzConfigVO;

public interface IQuartzConfigDao {
	QuartzConfigVO findOne(@Param("id") Long id);

	Integer setStatusById(@Param("status") String status, @Param("id") Long id);

	List<QuartzConfigVO> findAll();

	Integer setScheduleById(@Param("cronSchedule") String cronSchedule, @Param("id") Long id);
}
