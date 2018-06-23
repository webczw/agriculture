package com.hjd.power.agriculture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjd.power.agriculture.dao.IUserDao;
import com.hjd.power.agriculture.domain.UserVO;
import com.hjd.power.agriculture.service.IUserService;

@Service
public class UserService implements IUserService {
	@Autowired
	private IUserDao userDao;

	@Override
	public List<UserVO> findList() throws Exception {
		return userDao.findList();
	}

	@Override
	public UserVO find(Integer userId) throws Exception {
		return userDao.find(userId);
	}

	@Override
	public Integer create(UserVO vo) throws Exception {
		return userDao.create(vo);
	}

	@Override
	public Integer update(UserVO vo) throws Exception {
		return userDao.update(vo);
	}

	@Override
	public Integer delete(Integer userId) throws Exception {
		return userDao.delete(userId);
	}

}
