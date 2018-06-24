define([], function(){
    "use strict";

	var Constant = {};

	Constant.default = {
		lang: 'zh-cn',
		module: {name:'index'}
	};

	Constant.events = {
		hashChanged: 'HASH_CHANGED'
    };

    return Constant;
});