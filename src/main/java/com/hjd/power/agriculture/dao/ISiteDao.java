package com.hjd.power.agriculture.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hjd.power.agriculture.domain.SearchVO;
import com.hjd.power.agriculture.domain.SiteVO;

public interface ISiteDao {
	List<SiteVO> findList();

	List<SiteVO> findListBySearch(SearchVO vo);

	SiteVO find(@Param("siteId") Integer siteId);

	Integer create(SiteVO vo);

	Integer update(SiteVO vo);

	Integer delete(SiteVO vo);
}
