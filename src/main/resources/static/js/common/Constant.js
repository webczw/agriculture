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
		GET_USER_SESSION: 'user/session/user',

		GET_TOTAL: 'total/list',
		EXPORT_TOTAL: 'total/download',
		GET_SETTING: 'config/list',
		SAVE_SETTING: 'config',
		GET_PROVINCE: 'total/province',

		GET_MAIN_SITE_LIST: 'site/search',
		DELETE_MAIN_SITE: 'site',
		ADD_MAIN_SITE: 'site',

		GET_LIGHT_HOUSE_LIST: 'lighthouse/list/detail',
		DELETE_LIGHT_HOUSE: 'lighthouse',
		EXPORT_LIGHT_HOUSE: 'lighthouse/download',
    };

    return Constant;
});