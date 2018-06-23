package com.hjd.power.agriculture.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hjd.power.agriculture.Constants;
import com.hjd.power.agriculture.annotation.Access;
import com.hjd.power.agriculture.domain.UserVO;
import com.hjd.power.agriculture.service.IUserService;
import com.hjd.power.agriculture.utils.ResutUtils;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;

	@ApiOperation(value = "查询用户信息", notes = "根据入参ID查询用户信息")
	@GetMapping("/{userId}")
	public UserVO find(@PathVariable("userId") Integer userId) throws Exception {
		return userService.find(userId);
	}

	@ApiOperation(value = "查询用户信息集合", notes = "查询用户信息集合")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@Access(roles = { "admin" })
	public List<UserVO> findList() throws Exception {
		return userService.findList();
	}

	@ApiOperation(value = "创建用户信息", notes = "创建用户信息")
	@PostMapping()
	public Integer create(@RequestBody UserVO vo) throws Exception {
		return userService.create(vo);
	}

	@ApiOperation(value = "更新用户信息", notes = "更新用户信息")
	@PutMapping()
	public Integer update(@RequestBody UserVO vo) throws Exception {
		return userService.update(vo);
	}

	@ApiOperation(value = "删除用户信息", notes = "删除用户信息")
	@DeleteMapping("/{userId}")
	public Integer delete(@PathVariable("userId") Integer userId) throws Exception {
		return userService.delete(userId);
	}

	@ApiOperation(value = "登录", notes = "登录")
	@PostMapping("/loggin")
	public ResutUtils<UserVO> loggin(HttpServletRequest request, @RequestBody UserVO vo) throws Exception {
		ResutUtils<UserVO> resut = userService.loggin(vo);
		if (ResutUtils.successStatus == resut.getStatus()) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute(Constants.SESSION_KEY_USER, resut.getData());
		}
		return resut;
	}

	@ApiOperation(value = "注销", notes = "注销")
	@GetMapping("/exit")
	public void exit(HttpServletRequest request) throws Exception {
		HttpSession httpSession = request.getSession();
		if (httpSession.getAttribute(Constants.SESSION_KEY_USER) != null) {
			httpSession.removeAttribute(Constants.SESSION_KEY_USER);
		}
	}
}
