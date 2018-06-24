package com.hjd.power.agriculture.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hjd.power.agriculture.Constants;
import com.hjd.power.agriculture.domain.BaseVO;
import com.hjd.power.agriculture.domain.UserVO;

public class CommonUtils {
	public static HttpServletRequest getRequest() {
		try {
			return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		} catch (Exception e) {
			return null;
		}
	}

	public static Integer getCurrentUserId() {
		HttpServletRequest request = getRequest();
		HttpSession httpSession = request.getSession();
		Object object = httpSession.getAttribute(Constants.SESSION_KEY_USER);
		if (object != null) {
			UserVO userVO = (UserVO) object;
			return userVO.getUserId();
		}
		return -999999999;
	}

	public static <T extends BaseVO> void initCreate(T vo) {
		if (vo.getCreateId() == null) {
			vo.setCreateId(getCurrentUserId());
		}
		if (vo.getLastUpdateId() == null) {
			vo.setLastUpdateId(getCurrentUserId());
		}
	}

	public static <T extends BaseVO> void initUpdate(T vo) {
		if (vo.getLastUpdateId() == null) {
			vo.setLastUpdateId(getCurrentUserId());
		}
	}
}
