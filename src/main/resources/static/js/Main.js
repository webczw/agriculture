var baseUrl = '/agriculture/static/';
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
        this._searchWindowId = webix.uid();
        this._mainSiteWindowId = webix.uid();
        this._mainSiteAddWindowId = webix.uid();

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
                {
                    width: this.Constant.default.BODY_WIDTH,
                    paddingX: 50,
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
                { css: 'sider_bg' },
            ],
            
        });
    };

    Main.prototype.addListners = function(){
        Main._super.addListners.call(this);
        this.on('DETAIL_CLICK', this._showDetail.bind(this));
        this.on('SETTING_CLICK', this._showSetting.bind(this));
        this.on('BACK_OVERVIEW_CLICK', this._showOverview.bind(this));
        this.on('LOGIN_CLICK', this._showLogin.bind(this));
        this.on('LOGOUT_CLICK', this._doLogout.bind(this));
        this.on('SEARCH_CLICK', this._showSearch.bind(this));
        this.on('MAIN_SITE_CLICK', this._showMainSite.bind(this));
        this.on('GET_USER', this._getUser.bind(this));
    };

    Main.prototype.ready = function(){
        this._showOverview();
    };

    Main.prototype._getUser = function(){
        this.ajax('get', this.Constant.serviceUrls.GET_USER_SESSION, {}, function(data){
            if(data) this._loginSuccess(function(){}, data);
        }.bind(this));
    };

    Main.prototype._showOverview = function(){
        var container = this.getContainer();
        var childView = container.getChildViews()[0];
        if(childView) container.removeView(childView.config.id);
        this.overview = new Overview();
        container.addView(this.overview.getView());
    };

    Main.prototype._showDetail = function(obj, mainSiteData){
        var container = this.getContainer();
        var childView = container.getChildViews()[0];
        if(childView) container.removeView(childView.config.id);
        this.detail = new Detail(mainSiteData);
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
                        //{ name: 'refresh_date', view: 'text', label: '自动更新时间', labelWidth: 120, },
                        { name: 'export_format', view: 'combo', label: '导出文件格式', labelWidth: 120, options: [{id:'excel',value:'EXCEL'}] },
                        //{ name: 'export_path', view: 'text', label: '导出文件路径', labelWidth: 120, },
                        //{ name: 'file_storage_time', view: 'text', label: '自动存储时间', labelWidth: 120, },
                        //{ name: 'storage_site', view: 'text', label: '可进行数据存储的站点', labelWidth: 120, },
                        //{ name: 'storage_file', view: 'text', label: '常备的存储文件', labelWidth: 120, },
                        { name: 'email_address', view: 'text', label: '发送的邮件地址', labelWidth: 120, },
                        { name: 'sending_time', view: 'text', label: '定点发送时间', labelWidth: 120, },
                        {
                            height: 30,
                            cols: [
                                {},
                                { view: 'button', label: '确定', width: 90, on: {
                                    'onItemClick': this._doSaveSetting.bind(this),
                                } },
                                { view: 'button', label: '取消', width: 90, on: {
                                    'onItemClick': function(){
                                        $$(this._settingWindowId).close();
                                    }.bind(this),
                                } },
                                { view: 'button', label: '主站点设置', width: 120, on: {
                                    'onItemClick': function(){
                                        this._mainSiteClick();
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

    Main.prototype._mainSiteClick = function(){
        this.trigger('MAIN_SITE_CLICK', '');
    };

    Main.prototype._showMainSite = function(event, keywords){
        if(!$$(this._mainSiteWindowId)){
            var provinces = [
                {id: '北京', value: '北京',},
                {id: '天津', value: '天津',},
                {id: '上海', value: '上海',},
                {id: '重庆', value: '重庆',},
                {id: '河北', value: '河北',},
                {id: '河南', value: '河南',},
                {id: '云南', value: '云南',},
                {id: '辽宁', value: '辽宁',},
                {id: '黑龙江', value: '黑龙江',},
                {id: '湖南', value: '湖南',},
                {id: '安徽', value: '安徽',},
                {id: '山东', value: '山东',},
                {id: '新疆', value: '新疆',},
                {id: '江苏', value: '江苏',},
                {id: '浙江', value: '浙江',},
                {id: '江西', value: '江西',},
                {id: '湖北', value: '湖北',},
                {id: '广西', value: '广西',},
                {id: '甘肃', value: '甘肃',},
                {id: '山西', value: '山西',},
                {id: '内蒙古', value: '内蒙古',},
                {id: '陕西', value: '陕西',},
                {id: '吉林', value: '吉林',},
                {id: '福建', value: '福建',},
                {id: '贵州', value: '贵州',},
                {id: '广东', value: '广东',},
                {id: '青海', value: '青海',},
                {id: '西藏', value: '西藏',},
                {id: '四川', value: '四川',},
                {id: '宁夏', value: '宁夏',},
                {id: '海南', value: '海南',},
                {id: '台湾', value: '台湾',},
                {id: '香港', value: '香港',},
                {id: '澳门', value: '澳门',}
            ];
            var view = webix.ui({
                view: 'window',
                id: this._mainSiteWindowId,
                head: '主站点',
                position: 'center',
                modal: true,
                body: {
                    width: 800,
                    height: 400,
                    padding: 10,
                    rows: [
                        {
                            
                            view: 'datatable',
                            width: 400,
                            columns: [
                                { id: 'action', header: { text: '<i class="fas fa-plus-circle add normal"></i>' }, template: function(){
                                    return '<i class="fas fa-minus-circle delete normal"></i>';
                                }, width: 50 },
                                { id: 'siteCode', header: '站点编码', fillspace: 1, template: function(obj, common, value){
                                    return '<a class="detail">'+value+'</a>';
                                } },
                                { id: 'siteName', header: '站点名称', fillspace: 1, },
                                { id: 'province', header: '省', fillspace: 1, },
                                { id: 'city', header: '市', fillspace: 1, },
                                { id: 'county', header: '县', fillspace: 1, },
                            ],
                            on: {
                                'onStructureLoad': function(){
                                    this.ajax('get', this.Constant.serviceUrls.GET_MAIN_SITE_LIST, {key:keywords||''}, function(data){
                                        var datatable = $$(this._mainSiteWindowId).getBody().getChildViews()[0];
                                        datatable.parse(data);
                                    }.bind(this));
                                }.bind(this),
                                'onHeaderClick': function(ids, e, node){
                                    if($(e.target).hasClass('add')){
                                        webix.ui({
                                            view: 'window',
                                            id: this._mainSiteAddWindowId,
                                            head: '增加主站点',
                                            position: 'center',
                                            modal: true,
                                            body: {
                                                width: 400,
                                                view: 'form',
                                                rows: [
                                                    { name: 'siteCode', view: 'text', label: '站点编码', labelWidth: 120, required: true, },
                                                    { name: 'siteName', view: 'text', label: '站点名称', labelWidth: 120, required: true, },
                                                    { name: 'province', view: 'combo', label: '省', labelWidth: 120, options: provinces, required: true, },
                                                    { name: 'city', view: 'text', label: '市', labelWidth: 120, },
                                                    { name: 'county', view: 'text', label: '县', labelWidth: 120, },
                                                    {
                                                        height: 30,
                                                        cols: [
                                                            {},
                                                            { view: 'button', label: '确定', width: 90, on: {
                                                                'onItemClick': function(){
                                                                    var form = $$(this._mainSiteAddWindowId).getBody();
                                                                    if(!form.validate()) return;
                                                                    var values = form.getValues();
                                                                    this.ajax('post', this.Constant.serviceUrls.ADD_MAIN_SITE, values, function(data){
                                                                        var datatable = $$(this._mainSiteWindowId).getBody().getChildViews()[0];
                                                                        this.message(this.Constant.info.SUCCESS);
                                                                        datatable.add(data);
                                                                        $$(this._mainSiteAddWindowId).close();
                                                                        this.trigger('ADD_MAIN_SITE_SUCCESS');
                                                                    }.bind(this));
                                                                }.bind(this),
                                                            } },
                                                            { view: 'button', label: '取消', width: 90, on: {
                                                                'onItemClick': function(){
                                                                    $$(this._mainSiteAddWindowId).close();
                                                                }.bind(this),
                                                            } },
                                                            {},
                                                        ],
                                                    },
                                                ],
                                            }
                                        }).show();
                                    }
                                }.bind(this),
                                'onItemClick': function(ids, e, node){
                                    if(ids.column === 'action' && $(e.target).hasClass('delete')){
                                        this.confirm('确认删除？', function(confirm){
                                            if(!confirm) return;
                                            var datatable = $$(this._mainSiteWindowId).getBody().getChildViews()[0];
                                            var rowData = datatable.getItem(ids.row);
                                            this.ajax('del', this.Constant.serviceUrls.DELETE_MAIN_SITE+'/'+rowData.siteId, {}, function(){
                                                this.message(this.Constant.info.SUCCESS);
                                                datatable.remove(ids.row);
                                                this.trigger('DELETE_MAIN_SITE_SUCCESS');
                                            }.bind(this));
                                        }.bind(this));
                                    }
                                    else if(ids.column === 'siteCode' && $(e.target).hasClass('detail')){
                                        var datatable = $$(this._mainSiteWindowId).getBody().getChildViews()[0];
                                        var rowData = datatable.getItem(ids.row);
                                        this.trigger('DETAIL_CLICK', rowData);
                                        setTimeout(function(){ // 直接报错，原因不明
                                            $$(this._mainSiteWindowId).close();
                                        }.bind(this), 0);
                                    }
                                }.bind(this),
                            },
                        },
                        { height: 10 },
                        {
                            height: 30,
                            cols: [
                                {},
                                { view: 'button', label: '关闭', width: 90, on: {
                                    'onItemClick': function(){
                                        $$(this._mainSiteWindowId).close();
                                    }.bind(this),
                                } },
                                {},
                            ],
                        },
                    ],
                    
                }
            });
            view.show();
            view.resize();
        }
    };

    Main.prototype._showSearch = function(){
        if(!$$(this._searchWindowId)){
            var view = webix.ui({
                view: 'window',
                id: this._searchWindowId,
                head: '搜索',
                position: 'center',
                modal: true,
                body: {
                    view: 'form',
                    width: 400,
                    borderless: true,
                    rows: [
                        { name: 'keywords', view: 'text', label: '关键词', labelWidth: 120, },
                        {
                            height: 30,
                            cols: [
                                {},
                                { view: 'button', label: '搜索', width: 90, on: {
                                    'onItemClick': function(){
                                        this._doSearch();
                                        $$(this._searchWindowId).close();
                                    }.bind(this),
                                } },
                                { view: 'button', label: '取消', width: 90, on: {
                                    'onItemClick': function(){
                                        $$(this._searchWindowId).close();
                                    }.bind(this),
                                } },
                                {},
                            ],
                        },
                    ],
                    on: {
                        'onSubmit': function(){
                            this._doSearch();
                            $$(this._searchWindowId).close();
                        }.bind(this),
                    } 
                }
            });
            view.show();
            view.resize();
        }
    };

    Main.prototype._showLogin = function(obj, callback){
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
                                { view: 'button', label: '登录', width: 90, on: {
                                    'onItemClick': function(){
                                        this._doLogin(callback);
                                    }.bind(this),
                                } },
                                { view: 'button', label: '取消', width: 90, on: {
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
                            this._doLogin(callback);
                        }.bind(this),
                    } 
                }
            });
            view.show();
            view.resize();
        }
    };

    Main.prototype._doSearch = function(){
        var keywords = $$(this._searchWindowId).getBody().elements['keywords'].getValue();
        this.trigger('MAIN_SITE_CLICK', keywords);
    };

    Main.prototype._doLogin = function(callback){
        var form = $$(this._loginWindowId).getBody();
        var values = form.getValues();
        var userId = values.userId;
        var password = values.password;
        this.ajax('post', this.Constant.serviceUrls.LOGIN, {
            loginName: userId,
            password: password,
        }, function(data){
            this.message(this.Constant.info.SUCCESS);
            this._loginSuccess(callback, data);
        }.bind(this));
    };
    Main.prototype._doLogout = function(){
        this.ajax('get', this.Constant.serviceUrls.LOGOUT, {
        }, this._logoutSuccess.bind(this));
    };
    Main.prototype._doSaveSetting = function(){
        if(!this._settingData) return;
        var form = $$(this._settingWindowId).getBody();
        var values = form.getValues();
        var postData = {
            list: this._settingData.map(function(item){
                return {
                    configId: item.configId,
                    configValue: values[item.configCode],
                };
            }),
        };
        this.ajax('put', this.Constant.serviceUrls.SAVE_SETTING, postData, function(){
            this.message(this.Constant.info.SUCCESS);
            $$(this._settingWindowId).close();
        }.bind(this));
    };

    Main.prototype._loginSuccess = function(callback, data){
        $$(this._loginWindowId) && $$(this._loginWindowId).close();
        this.Model.getInstance().setValue('USER', data);
        if(callback) callback(data);
        this.trigger('LOGIN_SUCCESS', data);
    };
    Main.prototype._logoutSuccess = function(data){
        this.message(this.Constant.info.SUCCESS);
        this.Model.getInstance().deleteValue('USER');
        this.trigger('LOGOUT_SUCCESS', data);
    };

    Main.prototype.getContainer = function(){
        return $$(this._containerId);
    };

	return new Main();
});