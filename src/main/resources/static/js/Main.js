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
        Main._super.constructor.call(this);
    }
    
    Main.prototype.ui = function(){
        Main._super.ui.call(this);
        var view = webix.ui({
            id: this.viewId,
            container: 'app',
            cols: [
                {},
                {
                    width: 1000,
                    rows: [
                        {
                            height: 120,
                            borderless: true,
                            template: '<div id="header"><div id="banner"><img src="'+baseUrl+'imgs/banner.jpg"></div></div>',
                        },
                        { id: this._containerId, rows: [] },
                        {
                            height: 30,
                            borderless: true,
                            template: '<div id="footer" style="text-align:center;">Copyright 湖南保的农业科技有限公司</div>',
                        },
                    ],
                },
                {},
            ],
            
        });
    };

    Main.prototype.addListners = function(){
        Main._super.addListners.call(this);
        this.on('LINK_CLICK', this._showDetail.bind(this));
        this.on('SETTING_CLICK', this._showSetting.bind(this));
        this.on('BACK_OVERVIEW_CLICK', this._showOverview.bind(this));
        this.on('LOGIN_CLICK', this._showLogin.bind(this));
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
                        { view: 'text', label: '自动更新时间', labelWidth: 120, },
                        { view: 'combo', label: '导出文件格式', labelWidth: 120, options: [{id:'pdf',value:'PDF'},{id:'word',value:'WORD'},] },
                        { view: 'text', label: '导出文件路径', labelWidth: 120, },
                        { view: 'text', label: '自动存储时间', labelWidth: 120, },
                        { view: 'text', label: '可进行数据存储的站点', labelWidth: 120, },
                        { view: 'text', label: '常备的存储文件', labelWidth: 120, },
                        {
                            height: 30,
                            cols: [
                                {},
                                { view: 'button', label: '确定', },
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
                        { view: 'text', label: '用户名', labelWidth: 120, },
                        { view: 'text', type: 'password', label: '密码', labelWidth: 120, },
                        {
                            height: 30,
                            cols: [
                                {},
                                { view: 'button', label: '确定', },
                                { view: 'button', label: '取消', on: {
                                    'onItemClick': function(){
                                        $$(this._loginWindowId).close();
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

    Main.prototype.getContainer = function(){
        return $$(this._containerId);
    };

	return new Main();
});