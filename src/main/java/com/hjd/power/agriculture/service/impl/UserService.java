package com.hjd.power.agriculture.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjd.power.agriculture.Constants;
import com.hjd.power.agriculture.Enums.FailEnum;
import com.hjd.power.agriculture.dao.IUserDao;
import com.hjd.power.agriculture.domain.UserVO;
import com.hjd.power.agriculture.service.IUserService;
import com.hjd.power.agriculture.utils.AESUtils;
import com.hjd.power.agriculture.utils.CommonUtils;
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
		CommonUtils.initCreate(vo);
		return userDao.create(vo);
	}

	@Override
	public Integer update(UserVO vo) throws Exception {
		String password = vo.getPassword();
		password = AESUtils.encrypt(password, Constants.AES_KEY, Constants.AES_IV);
		vo.setPassword(password);
		CommonUtils.initUpdate(vo);
		return userDao.update(vo);
	}

	@Override
	public Integer delete(Integer userId) throws Exception {
		UserVO userVO = new UserVO();
		userVO.setUserId(userId);
		CommonUtils.initUpdate(userVO);
		return userDao.delete(userVO);
	}

	@Override
	public ResutUtils<UserVO> loggin(UserVO vo) throws Exception {
		UserVO userVO = userDao.findUser(vo);
		if (userVO != null) {
			String password = vo.getPassword();
			password = AESUtils.encrypt(password, Constants.AES_KEY, Constants.AES_IV);
			if (password.equalsIgnoreCase(userVO.getPassword())) {
				int loginCount = userVO.getLoginCount();
				loginCount = loginCount + 1;
				Date loginTime = userVO.getLoginTime();
				UserVO upUserVO = new UserVO();
				upUserVO.setLastLoginTime(loginTime);
				upUserVO.setLoginTime(new Date());
				upUserVO.setLoginCount(loginCount);
				upUserVO.setUserId(userVO.getUserId());
				upUserVO.setLastUpdateId(userVO.getUserId());
				userDao.update(upUserVO);
				return ResutUtils.success(userVO);
			} else {
				return ResutUtils.fail(FailEnum.COM_HJD_POWER_00002);
			}
		} else {
			return ResutUtils.fail(FailEnum.COM_HJD_POWER_00001);
		}
	}

	@Override
	public Integer updatePwd(String loginName, String oldPwd, String newPwd) throws Exception {
		UserVO vo = new UserVO();
		vo.setLoginName(loginName);
		UserVO userVO = userDao.findUser(vo);
		if (userVO != null) {
			String password = userVO.getPassword();
			oldPwd = AESUtils.encrypt(oldPwd, Constants.AES_KEY, Constants.AES_IV);
			if (password.equalsIgnoreCase(oldPwd)) {
				newPwd = AESUtils.encrypt(newPwd, Constants.AES_KEY, Constants.AES_IV);
				vo.setPassword(newPwd);
				return userDao.updatePassowrd(vo);
			}
		}
		return 0;
	}

}
