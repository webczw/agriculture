package com.hjd.power.agriculture.utils;

import java.util.regex.Pattern;

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

	public static boolean isNumeric(String str) {
		// 就是判断是否为整数(正负)
		Pattern pattern = Pattern.compile("^\\d+$|-\\d+$");
		// 判断是否为小数(正负)
		Pattern pattern2 = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");
		return (pattern.matcher(str).matches() || pattern2.matcher(str).matches());
	}
}
