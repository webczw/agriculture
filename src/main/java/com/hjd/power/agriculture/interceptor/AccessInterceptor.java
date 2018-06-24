package com.hjd.power.agriculture.interceptor;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.hjd.power.agriculture.Constants;
import com.hjd.power.agriculture.Enums.FailEnum;
import com.hjd.power.agriculture.annotation.Access;
import com.hjd.power.agriculture.domain.UserVO;
import com.hjd.power.agriculture.utils.ResutUtils;

public class AccessInterceptor extends HandlerInterceptorAdapter {
	// 在调用方法之前执行拦截
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!handler.getClass().getName().equalsIgnoreCase(HandlerMethod.class.getName())) {
			return true;
		}
		// 将handler强转为HandlerMethod, 前面已经证实这个handler就是HandlerMethod
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 从方法处理器中获取出要调用的方法
		Method method = handlerMethod.getMethod();

		// 获取出方法上的Access注解
		Access access = method.getAnnotation(Access.class);
		if (access == null) {
			// 如果注解为null, 说明不需要拦截, 直接放过
			return true;
		}
		if (access.roles().length > 0) {
			// 如果权限配置不为空, 则取出配置值
			String[] roles = access.roles();
			Set<String> roleSet = new HashSet<>();
			for (String role : roles) {
				// 将权限加入一个set集合中
				roleSet.add(role);
			}
			// 这里我为了方便是直接参数传入权限, 在实际操作中应该是从参数中获取用户Id
			// 到数据库权限表中查询用户拥有的权限集合, 与set集合中的权限进行对比完成权限校验
			HttpSession httpSession = request.getSession();
			Object object = httpSession.getAttribute(Constants.SESSION_KEY_USER);
			if (object == null) {
				PrintWriter printWriter = response.getWriter();
				printWriter.write(JSON.toJSONString(ResutUtils.fail(FailEnum.COM_HJD_POWER_00003)));
				// printWriter.write("{code:0,message:\"session is
				// invalid,please login again!\"}");
				// response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				// response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				// "session is invalid,please login again!");
				return false;
			}
			UserVO userVO = (UserVO) object;
			String userRole = userVO.getRole();
			if (!StringUtils.isEmpty(userRole)) {
				String[] userRoles = userRole.split(",");
				for (int i = 0; i < userRoles.length; i++) {
					if (roleSet.contains(userRoles[i])) {
						// 校验通过返回true, 否则拦截请求
						return true;
					}
				}
			}
		}
		// 拦截之后应该返回公共结果, 这里没做处理
		PrintWriter printWriter = response.getWriter();
		printWriter.write(JSON.toJSONString(ResutUtils.fail(FailEnum.COM_HJD_POWER_00004)));
		return false;
	}
}
