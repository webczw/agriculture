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
	};

	Constant.events = {
	};
	
	Constant.serviceUrls = {
		BASE_URL: 'agriculture/',
		LOGIN: 'server/user/loggin',
		LOGOUT: 'server/user/exit',

		GET_TOTAL: 'server/total/list',
		EXPORT_TOTAL: 'server/total/download',
		GET_SETTING: 'server/config/list',
		SAVE_SETTING: 'server/config',
    };

    return Constant;
});