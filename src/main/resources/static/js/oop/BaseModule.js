
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

	BaseModule.prototype.ajax = function(method, url, data, success, fail){
		return webix.ajax()[method](url, data, {
			success: success,
			error: fail,
		});
	};

	return BaseModule;
});