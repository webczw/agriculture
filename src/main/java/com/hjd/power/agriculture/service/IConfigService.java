package com.hjd.power.agriculture.service;

import java.util.List;

import com.hjd.power.agriculture.domain.ConfigVO;

public interface IConfigService {
	List<ConfigVO> findList() throws Exception;

	ConfigVO find(Integer configId) throws Exception;

	Integer create(ConfigVO vo) throws Exception;

	Integer batchUpdate(List<ConfigVO> list) throws Exception;

	Integer delete(Integer configId) throws Exception;
}
