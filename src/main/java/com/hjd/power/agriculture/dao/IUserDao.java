package com.hjd.power.agriculture.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hjd.power.agriculture.domain.UserVO;

public interface IUserDao {
	List<UserVO> findList();

	UserVO find(@Param("userId") Integer userId);

	Integer create(UserVO vo);

	Integer update(UserVO vo);

	Integer delete(UserVO vo);

	UserVO findUser(UserVO vo);

	Integer updatePassowrd(UserVO vo);
}
