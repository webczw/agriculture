package com.hjd.power.agriculture.service;

import java.util.List;

import com.hjd.power.agriculture.domain.UserVO;

public interface IUserService {
	List<UserVO> findList() throws Exception;

	UserVO find(Integer userId) throws Exception;

	Integer create(UserVO vo) throws Exception;

	Integer update(UserVO vo) throws Exception;

	Integer delete(Integer userId) throws Exception;
}
