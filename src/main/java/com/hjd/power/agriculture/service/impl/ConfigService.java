package com.hjd.power.agriculture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hjd.power.agriculture.dao.IConfigDao;
import com.hjd.power.agriculture.domain.ConfigVO;
import com.hjd.power.agriculture.service.IConfigService;
import com.hjd.power.agriculture.utils.CommonUtils;

@Service
public class ConfigService implements IConfigService {
	@Autowired
	private IConfigDao configDao;

	@Override
	public List<ConfigVO> findList() throws Exception {
		return configDao.findList();
	}

	@Override
	public ConfigVO find(Integer configId) throws Exception {
		return configDao.find(configId);
	}

	@Override
	public Integer create(ConfigVO vo) throws Exception {
		CommonUtils.initCreate(vo);
		return configDao.create(vo);
	}

	@Override
	public Integer batchUpdate(List<ConfigVO> list) throws Exception {
		if (!CollectionUtils.isEmpty(list)) {
			for (ConfigVO configVO : list) {
				CommonUtils.initUpdate(configVO);
			}
			return configDao.batchUpdate(list);
		}
		return 0;
	}

	@Override
	public Integer delete(Integer configId) throws Exception {
		ConfigVO vo = new ConfigVO();
		vo.setConfigId(configId);
		CommonUtils.initUpdate(vo);
		return configDao.delete(vo);
	}

}
