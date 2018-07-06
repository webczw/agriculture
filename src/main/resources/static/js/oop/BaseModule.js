
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
		return this;
	};

	BaseModule.prototype.init = function(){
		this.ui();
		var view = this.getView();
		if(view) view.attachEvent('onDestruct', this.destroy.bind(this));
		this.addListners();
		this.ready();
	};

	BaseModule.prototype.ui = function(){
	};

	BaseModule.prototype.addListners = function(){
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
		var obj = {
			title: '警告',
			text: msg,
			type: 'alert-warning',
		};
		return webix.alert(obj);
	};
	BaseModule.prototype.message = function(msg, type){
		var obj = {
			text: msg,
			type: type || 'info',
			expire: 5000,
		};
		return webix.message(obj);
	};
	BaseModule.prototype.confirm = function(msg, callback){
		var obj = {
			title: '请确认',
			text: msg,
			ok:"确认", 
    		cancel:"取消",
			callback: callback,
		};
		return webix.confirm(obj);
	};
	BaseModule.prototype.checkLogin = function(callback){
		var logined = this.Model.getInstance().getValue('USER');
		if(!logined){
			this.trigger('LOGIN_CLICK', callback);
		}
		else{
			callback();
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
			if(!data || data.status === undefined){
				return success && success(data);
			}
			else if(data.status === 0){
				return success && success(data.data);
			}
			else if(data.status === 1){
				if(data.errorCode === 'COM_HJD_POWER_00003'){
					this.info(this.Constant.info.NO_LOGIN);
				}
				else{
					fail(data);
				}
			}
		}.bind(this);
		return webix.ajax().headers({
			"Content-type":"application/json"
		})[method](url, data, {
			success: callback,
			error: fail,
		});
	};

	return BaseModule;
});