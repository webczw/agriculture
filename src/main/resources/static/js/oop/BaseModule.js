
define([
	'Constant',
	'Model',
	'Util',
	baseUrl+'js/oop/EventSystem.js',
	'webix',
], function(
	Constant,
	Model,
	Util,
	EventSystem
){
	"use strict";

	function BaseModule(){
		webix.extend(this, new EventSystem());
		this.Constant = Constant;
		this.Model = Model;
		this.Util = Util;
		this.viewId = webix.uid();
		this.init();
	};

	BaseModule.prototype.init = function(){
		this.ui();
		this.addListners();
		this.ready();
	};

	BaseModule.prototype.ui = function(){
	};

	BaseModule.prototype.addListners = function(){
		var view = this.getView();
		view.attachEvent('onDestructor', this.destroy.bind(this));
	};

	BaseModule.prototype.ready = function(){
	};

	BaseModule.prototype.destroy = function(){
		this.off();
	};

	BaseModule.prototype.getView = function(){
		return $$(this.viewId);
	};

	BaseModule.prototype.info = function(msg){
		return webix.message(msg);
	};
	BaseModule.prototype.checkLogin = function(){
		var logined = this.Model.getInstance().getValue('USER');
		if(!logined){
			this.trigger('LOGIN_CLICK');
		}
		return logined;
	};

	BaseModule.prototype.ajax = function(method, url, data, success, fail){
		url = this.Constant.serviceUrls.BASE_URL + url;
		fail = fail || function(data){
			this.info(this.Constant.info.FAIL);
		}.bind(this);
		var callback = function(jsonStr, obj, xhr){
			var data = obj.json();
			return success && success(data);
		};
		return webix.ajax().headers({
			"Content-type":"application/json"
		})[method](url, data, {
			success: callback,
			error: fail,
		});
	};

	return BaseModule;
});