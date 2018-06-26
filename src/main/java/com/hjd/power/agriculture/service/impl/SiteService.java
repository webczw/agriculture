package com.hjd.power.agriculture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjd.power.agriculture.dao.ISiteDao;
import com.hjd.power.agriculture.domain.SearchVO;
import com.hjd.power.agriculture.domain.SiteVO;
import com.hjd.power.agriculture.service.ISiteService;
import com.hjd.power.agriculture.utils.CommonUtils;

@Service
public class SiteService implements ISiteService {
	@Autowired
	private ISiteDao siteDao;

	@Override
	public List<SiteVO> findList() throws Exception {
		return siteDao.findList();
	}

	@Override
	public SiteVO find(Integer siteId) throws Exception {
		return siteDao.find(siteId);
	}

	@Override
	public Integer create(SiteVO vo) throws Exception {
		CommonUtils.initCreate(vo);
		return siteDao.create(vo);
	}

	@Override
	public Integer update(SiteVO vo) throws Exception {
		CommonUtils.initUpdate(vo);
		return siteDao.update(vo);
	}

	@Override
	public Integer delete(Integer siteId) throws Exception {
		SiteVO vo = new SiteVO();
		vo.setSiteId(siteId);
		CommonUtils.initUpdate(vo);
		return siteDao.delete(vo);
	}

	@Override
	public List<SiteVO> findListBySearch(SearchVO vo) throws Exception {
		return siteDao.findListBySearch(vo);
	}

}
