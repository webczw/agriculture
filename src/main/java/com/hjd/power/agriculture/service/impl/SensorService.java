package com.hjd.power.agriculture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjd.power.agriculture.dao.ISensorDao;
import com.hjd.power.agriculture.domain.SensorVO;
import com.hjd.power.agriculture.service.ISensorService;
import com.hjd.power.agriculture.utils.CommonUtils;

@Service
public class SensorService implements ISensorService {
	@Autowired
	private ISensorDao sensorDao;

	@Override
	public List<SensorVO> findList() throws Exception {
		return sensorDao.findList();
	}

	@Override
	public SensorVO find(Integer sensorId) throws Exception {
		return sensorDao.find(sensorId);
	}

	@Override
	public Integer create(SensorVO vo) throws Exception {
		CommonUtils.initCreate(vo);
		return sensorDao.create(vo);
	}

	@Override
	public Integer update(SensorVO vo) throws Exception {
		CommonUtils.initUpdate(vo);
		return sensorDao.update(vo);
	}

	@Override
	public Integer delete(Integer sensorId) throws Exception {
		SensorVO vo = new SensorVO();
		vo.setSensorId(sensorId);
		CommonUtils.initUpdate(vo);
		return sensorDao.delete(vo);
	}

}
