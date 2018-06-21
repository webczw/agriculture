package com.hjd.power.agriculture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjd.power.agriculture.dao.IMainDao;
import com.hjd.power.agriculture.domain.MainVO;
import com.hjd.power.agriculture.service.IMainService;

@Service
public class MainService implements IMainService {
	@Autowired
	private IMainDao mainDao;

	@Override
	public List<MainVO> findList() throws Exception {
		return mainDao.findList();
	}

	@Override
	public MainVO find(Integer mainId) throws Exception {
		return mainDao.find(mainId);
	}

	@Override
	public Integer create(MainVO vo) throws Exception {
		return mainDao.create(vo);
	}

	@Override
	public Integer update(MainVO vo) throws Exception {
		return mainDao.update(vo);
	}

	@Override
	public Integer delete(Integer mainId) throws Exception {
		return mainDao.delete(mainId);
	}

}
