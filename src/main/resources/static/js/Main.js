var baseUrl = '/agriculture/';
requirejs.config({
	baseUrl: baseUrl,
	map: {
		'*': {
			'css': baseUrl+'libs/css.min.js',
		}
	},
	paths: {
        'jquery': 'libs/jquery/jquery-3.3.1.min',
        'webix': 'libs/webix/webix',
        'echarts': 'libs/echarts.min',
		'Constant': 'js/common/Constant',
		'Model': 'js/common/Model',
        'Util': 'js/common/Util',
        'Oop': 'js/oop/Oop',
	},
});

require([
    'Oop',
    baseUrl+'js/oop/BaseModule.js',
    baseUrl+'js/modules/Overview.js',
    baseUrl+'js/modules/Detail.js',
    'webix',
	'css!'+baseUrl+'libs/webix/webix.css',
	'css!'+baseUrl+'css/main.css',
], function(
    Oop,
    BaseModule,
    Overview,
    Detail
){
    "use strict";
    Oop.extend(Main, BaseModule);
    function Main(){
        this._containerId = webix.uid();
        this._settingWindowId = webix.uid();
        this._loginWindowId = webix.uid();

        this._settingData = null; // 设置的后台数据

        Main._super.constructor.call(this);
    }
    
    Main.prototype.ui = function(){
        Main._super.ui.call(this);
        var view = webix.ui({
            id: this.viewId,
            container: 'app',
            cols: [
                { css: 'sider_bg' },
                { width: 50 },
                {
                    width: this.Constant.default.BODY_WIDTH,
                    rows: [
                        {
                            height: 120,
                            borderless: true,
                            template: '<div id="header"><div id="banner"><img src="'+baseUrl+'imgs/banner.png"></div></div>',
                        },
                        { id: this._containerId, rows: [] },
                        {
                            height: 30,
                            borderless: true,
                            template: '<div id="footer" style="text-align:center;">Copyright 湖南保的农业科技有限公司</div>',
                        },
                    ],
                },
                { width: 50 },
                { css: 'sider_bg' },
            ],
            
        });
    };

    Main.prototype.addListners = function(){
        Main._super.addListners.call(this);
        this.on('LINK_CLICK', this._showDetail.bind(this));
        this.on('SETTING_CLICK', this._showSetting.bind(this));
        this.on('BACK_OVERVIEW_CLICK', this._showOverview.bind(this));
        this.on('LOGIN_CLICK', this._showLogin.bind(this));
        this.on('LOGOUT_CLICK', this._doLogout.bind(this));
    };

    Main.prototype.ready = function(){
        this._showOverview();
    };

    Main.prototype._showOverview = function(){
        var container = this.getContainer();
        var childView = container.getChildViews()[0];
        this.overview = new Overview();
        if(childView) container.removeView(childView.config.id);
        container.addView(this.overview.getView());
    };

    Main.prototype._showDetail = function(){
        var container = this.getContainer();
        var childView = container.getChildViews()[0];
        this.detail = new Detail();
        if(childView) container.removeView(childView.config.id);
        container.addView(this.detail.getView());
    };

    Main.prototype._showSetting = function(){
        this.ajax('get', this.Constant.serviceUrls.GET_SETTING, {}, function(data){
            this._settingData = data;
            var form = $$(this._settingWindowId).getBody();
            var values = {};
            data.forEach(function(item){
                values[item.configCode] = item.configValue;
            });
            form.setValues(values);
        }.bind(this));
        if(!$$(this._settingWindowId)){
            var view = webix.ui({
                view: 'window',
                id: this._settingWindowId,
                head: '设置',
                position: 'center',
                modal: true,
                body: {
                    view: 'form',
                    width: 400,
                    borderless: true,
                    rows: [
                        { name: 'refresh_date', view: 'text', label: '自动更新时间', labelWidth: 120, },
                        { name: 'export_format', view: 'combo', label: '导出文件格式', labelWidth: 120, options: [{id:'excel',value:'EXCEL'}] },
                        { name: 'export_path', view: 'text', label: '导出文件路径', labelWidth: 120, },
                        { name: 'file_storage_time', view: 'text', label: '自动存储时间', labelWidth: 120, },
                        { name: 'storage_site', view: 'text', label: '可进行数据存储的站点', labelWidth: 120, },
                        { name: 'storage_file', view: 'text', label: '常备的存储文件', labelWidth: 120, },
                        { name: 'email_address', view: 'text', label: '发送的邮件地址', labelWidth: 120, },
                        { name: 'sending_time', view: 'text', label: '定点发送时间', labelWidth: 120, },
                        {
                            height: 30,
                            cols: [
                                {},
                                { view: 'button', label: '确定', on: {
                                    'onItemClick': this._doSaveSetting.bind(this),
                                } },
                                { view: 'button', label: '取消', on: {
                                    'onItemClick': function(){
                                        $$(this._settingWindowId).close();
                                    }.bind(this),
                                } },
                                {},
                            ],
                        },
                    ]
                }
            });
            view.show();
            view.resize();
        }
    };

    Main.prototype._showLogin = function(){
        if(!$$(this._loginWindowId)){
            var view = webix.ui({
                view: 'window',
                id: this._loginWindowId,
                head: '登录',
                position: 'center',
                modal: true,
                body: {
                    view: 'form',
                    width: 400,
                    borderless: true,
                    rows: [
                        { name: 'userId', view: 'text', label: '用户名', labelWidth: 120, },
                        { name: 'password', view: 'text', type: 'password', label: '密码', labelWidth: 120, },
                        {
                            height: 30,
                            cols: [
                                {},
                                { view: 'button', label: '确定', on: {
                                    'onItemClick': function(){
                                        this._doLogin();
                                    }.bind(this),
                                } },
                                { view: 'button', label: '取消', on: {
                                    'onItemClick': function(){
                                        $$(this._loginWindowId).close();
                                    }.bind(this),
                                } },
                                {},
                            ],
                        },
                    ],
                    on: {
                        'onSubmit': function(){
                            this._doLogin();
                        }.bind(this),
                    } 
                }
            });
            view.show();
            view.resize();
        }
    };

    Main.prototype._doLogin = function(){
        var form = $$(this._loginWindowId).getBody();
        var values = form.getValues();
        var userId = values.userId;
        var password = values.password;
        this.ajax('post', this.Constant.serviceUrls.LOGIN, {
            loginName: userId,
            password: password,
        }, this._loginSuccess.bind(this));
    };
    Main.prototype._doLogout = function(){
        this.ajax('get', this.Constant.serviceUrls.LOGOUT, {
        }, this._logoutSuccess.bind(this));
    };
    Main.prototype._doSaveSetting = function(){
        if(!this._settingData) return;
        var form = $$(this._settingWindowId).getBody();
        var values = form.getValues();
        var postData = this._settingData.map(function(item){
            return {
                configId: item.configId,
                configValue: values[item.configCode],
            };
        });
        this.ajax('put', this.Constant.serviceUrls.SAVE_SETTING, postData, function(){
            this.info(this.Constant.info.SUCCESS);
            $$(this._settingWindowId).close();
        }.bind(this));
    };

    Main.prototype._loginSuccess = function(data){
        if(data.status == 1){
            this.info(this.Constant.info.LOGIN_FAIL);
            return;
        }
        this.info(this.Constant.info.SUCCESS);
        $$(this._loginWindowId).close();
        this.Model.getInstance().setValue('USER', data);
        this.trigger('LOGIN_SUCCESS', data);
    };
    Main.prototype._logoutSuccess = function(data){
        this.info(this.Constant.info.SUCCESS);
        this.Model.getInstance().deleteValue('USER');
        this.trigger('LOGOUT_SUCCESS', data);
    };

    Main.prototype.getContainer = function(){
        return $$(this._containerId);
    };

	return new Main();
});