package com.hjd.power.agriculture.service;

import java.util.List;

import com.hjd.power.agriculture.domain.SiteVO;

public interface ISiteService {
	List<SiteVO> findList() throws Exception;

	SiteVO find(Integer siteId) throws Exception;

	Integer create(SiteVO vo) throws Exception;

	Integer update(SiteVO vo) throws Exception;

	Integer delete(Integer siteId) throws Exception;
}
