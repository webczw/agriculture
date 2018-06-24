package com.hjd.power.agriculture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjd.power.agriculture.dao.ITotalDao;
import com.hjd.power.agriculture.domain.TotalVO;
import com.hjd.power.agriculture.service.ITotalService;
import com.hjd.power.agriculture.utils.CommonUtils;

@Service
public class TotalService implements ITotalService {
	@Autowired
	private ITotalDao totalDao;

	@Override
	public List<TotalVO> findList() throws Exception {
		return totalDao.findList();
	}

	@Override
	public TotalVO find(Integer totalId) throws Exception {
		return totalDao.find(totalId);
	}

	@Override
	public Integer create(TotalVO vo) throws Exception {
		CommonUtils.initCreate(vo);
		return totalDao.create(vo);
	}

	@Override
	public Integer update(TotalVO vo) throws Exception {
		CommonUtils.initUpdate(vo);
		return totalDao.update(vo);
	}

	@Override
	public Integer delete(Integer totalId) throws Exception {
		TotalVO totalVO = new TotalVO();
		totalVO.setTotalId(totalId);
		CommonUtils.initUpdate(totalVO);
		return totalDao.delete(totalVO);
	}

}
