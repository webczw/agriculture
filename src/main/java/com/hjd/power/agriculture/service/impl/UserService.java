package com.hjd.power.agriculture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjd.power.agriculture.Constants;
import com.hjd.power.agriculture.Enums.FailEnum;
import com.hjd.power.agriculture.dao.IUserDao;
import com.hjd.power.agriculture.domain.UserVO;
import com.hjd.power.agriculture.service.IUserService;
import com.hjd.power.agriculture.utils.AESUtils;
import com.hjd.power.agriculture.utils.ResutUtils;

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
		String password = vo.getPassword();
		password = AESUtils.encrypt(password, Constants.AES_KEY, Constants.AES_IV);
		vo.setPassword(password);
		return userDao.create(vo);
	}

	@Override
	public Integer update(UserVO vo) throws Exception {
		String password = vo.getPassword();
		password = AESUtils.encrypt(password, Constants.AES_KEY, Constants.AES_IV);
		vo.setPassword(password);
		return userDao.update(vo);
	}

	@Override
	public Integer delete(Integer userId) throws Exception {
		return userDao.delete(userId);
	}

	@Override
	public ResutUtils<UserVO> loggin(UserVO vo) throws Exception {
		UserVO userVO = userDao.findUser(vo);
		if (userVO != null) {
			String password = vo.getPassword();
			password = AESUtils.encrypt(password, Constants.AES_KEY, Constants.AES_IV);
			if (password.equalsIgnoreCase(userVO.getPassword())) {
				return ResutUtils.success(userVO);
			} else {
				return ResutUtils.fail(FailEnum.COM_HJD_POWER_00002);
			}
		} else {
			return ResutUtils.fail(FailEnum.COM_HJD_POWER_00001);
		}
	}

}
