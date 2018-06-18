package com.hjd.power.agriculture.service;

import java.util.List;

import com.hjd.power.agriculture.domain.TotalVO;

public interface ITotalService {
	List<TotalVO> findList() throws Exception;

	TotalVO find(Integer totalId) throws Exception;

	Integer create(TotalVO vo) throws Exception;

	Integer update(TotalVO vo) throws Exception;

	Integer delete(Integer totalId) throws Exception;
}
