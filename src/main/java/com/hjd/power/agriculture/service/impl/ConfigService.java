package com.hjd.power.agriculture.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.hjd.power.agriculture.Constants;
import com.hjd.power.agriculture.Enums.QuartzConfigEnum;
import com.hjd.power.agriculture.dao.IConfigDao;
import com.hjd.power.agriculture.domain.ConfigVO;
import com.hjd.power.agriculture.service.IConfigService;
import com.hjd.power.agriculture.service.IQuartzService;
import com.hjd.power.agriculture.utils.CommonUtils;

@Service
public class ConfigService implements IConfigService {
	private Logger logger = LoggerFactory.getLogger(ConfigService.class);
	@Autowired
	private IConfigDao configDao;
	@Autowired
	private IQuartzService quartzService;

	@Override
	public List<ConfigVO> findList(String configType) throws Exception {
		return configDao.findList(configType);
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
				if (Constants.SENDING_TIME_ID == configVO.getConfigId()) {
					String val = configVO.getConfigValue();
					if (StringUtils.isEmpty(val)) {
						continue;
					}
					ConfigVO oldVO = this.find(Constants.SENDING_TIME_ID);
					if (!val.equalsIgnoreCase(oldVO.getConfigValue())) {
						quartzService.updateCron(Constants.SEND_EMAIL_JOB_ID, val);
						quartzService.update(Constants.SEND_EMAIL_JOB_ID, QuartzConfigEnum.STATUS_STOP.getCode());
						Thread.sleep(3000);
						quartzService.update(Constants.SEND_EMAIL_JOB_ID, QuartzConfigEnum.STATUS_START.getCode());
					}
				}
				CommonUtils.initUpdate(configVO);
			}
			return configDao.batchUpdate(list);
		}
		this.sysConfig();
		return 0;
	}

	@Override
	public Integer delete(Integer configId) throws Exception {
		ConfigVO vo = new ConfigVO();
		vo.setConfigId(configId);
		CommonUtils.initUpdate(vo);
		return configDao.delete(vo);
	}

	@Override
	public void sysConfig() throws Exception {
		String configType = null;
		List<ConfigVO> list = this.findList(configType);
		for (ConfigVO configVO : list) {
			CommonUtils.addSysConfig(configVO.getConfigCode(), configVO.getConfigValue());
			logger.debug("" + configVO.getConfigCode() + "=" + configVO.getConfigValue());
		}

	}

}
