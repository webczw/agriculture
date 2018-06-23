package com.hjd.power.agriculture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjd.power.agriculture.dao.ILighthouseDao;
import com.hjd.power.agriculture.domain.LighthouseVO;
import com.hjd.power.agriculture.service.ILighthouseService;

@Service
public class LighthouseService implements ILighthouseService {
	@Autowired
	private ILighthouseDao lighthouseDao;

	@Override
	public List<LighthouseVO> findList() throws Exception {
		return lighthouseDao.findList();
	}

	@Override
	public LighthouseVO find(Integer lighthouseId) throws Exception {
		return lighthouseDao.find(lighthouseId);
	}

	@Override
	public Integer create(LighthouseVO vo) throws Exception {
		return lighthouseDao.create(vo);
	}

	@Override
	public Integer update(LighthouseVO vo) throws Exception {
		return lighthouseDao.update(vo);
	}

	@Override
	public Integer delete(Integer lighthouseId) throws Exception {
		return lighthouseDao.delete(lighthouseId);
	}

}
