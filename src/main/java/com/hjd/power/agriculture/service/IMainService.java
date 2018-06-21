package com.hjd.power.agriculture.service;

import java.util.List;

import com.hjd.power.agriculture.domain.MainVO;

public interface IMainService {
	List<MainVO> findList() throws Exception;

	MainVO find(Integer mainId) throws Exception;

	Integer create(MainVO vo) throws Exception;

	Integer update(MainVO vo) throws Exception;

	Integer delete(Integer mainId) throws Exception;
}
