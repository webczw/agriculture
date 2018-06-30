define([], function(){
    "use strict";

	var Constant = {};

	Constant.default = {
		BODY_WIDTH: 1020,
	};

	Constant.info = {
		SUCCESS: '操作成功',
		FAIL: '操作失败',
		LOGIN_FAIL: '登录失败',
		NO_LOGIN: '没有登录或者登录超时，请重新登录',
	};

	Constant.events = {
	};
	
	Constant.serviceUrls = {
		BASE_URL: '/agriculture/',
		LOGIN: 'user/loggin',
		LOGOUT: 'user/exit',

		GET_TOTAL: 'total/list',
		EXPORT_TOTAL: 'total/download',
		GET_SETTING: 'config/list',
		SAVE_SETTING: 'config',
    };

    return Constant;
});