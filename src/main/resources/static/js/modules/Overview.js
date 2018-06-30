define([
    'Oop',
    baseUrl+'js/oop/BaseModule.js',
    baseUrl+'js/modules/Map.js',
], function(
    Oop,
    BaseModule,
    Map
){
    "use strict";

    Oop.extend(Module, BaseModule);

    function Module(){
        this._linkId = webix.uid();
        this._mapId = webix.uid();
        this._statusTableId = webix.uid();
        this._loginBtnId = webix.uid();
        this._logoutBtnId = webix.uid();
        Module._super.constructor.call(this);
    }
    
    Module.prototype.ui = function(){
        Module._super.ui.call(this);
        var view = webix.ui({
            id: this.viewId,
            rows: [
                { height: 5, },
                {
                    cols: [
                        {
                            rows: [
                                {},
                                { id: this._linkId, view: 'label', width: 300, height: 300, borderless: true, template: '<div class="link"><ul><li></li><li></li><li></li><li></li></ul></div>', on: {
                                    'onItemClick': function(){
                                        this.trigger('LINK_CLICK');
                                    }.bind(this)
                                } },
                                {},
                            ],
                        },
                        {},
                        { width: 100, rows: [
                            {},
                            {
                                height: 50,
                                borderless: true,
                                template: '<div style="font-size:60px;"><i class="fas fa-angle-right"></i><i class="fas fa-angle-right"></i><i class="fas fa-angle-right"></i><i class="fas fa-angle-right"></i></div>',
                            },
                            {},
                        ] },
                        { width: 20 },
                        { width: 500, height: 400, id: this._mapId, borderless: true, template: '<div class="map" style="height:100%;"></div>'}
                    ],
                },
                { height: 5, },
                {
                    height: 25,
                    borderless: true,
                    template: function(){
                        var percents = 40;
                        return '<div class="loading" style="height:100%;"><div><i class="loaded"></i><i class="loaded"></i><i class="loaded"></i><i class="loaded"></i><i></i><i></i><i></i><i></i><i></i><i></i></div><div class="spacer"></div></div>';
                    },
                },
                {
                    height: 30,
                    borderless: true,
                    template: '<div style="text-align:center;">2018-06-10 ---- BDN001 ---- REV10</div>',
                },
                { height: 30, },
                {
                    cols: [
                        {},
                        {
                            view: 'datatable',
                            id: this._statusTableId,
                            width: 600,
                            autoheight: true,
                            scroll: false,
                            columns: [
                                { id: 'totalSite', header: '总站点', fillspace: 1, },
                                { id: 'linkSite', header: '连接站点', fillspace: 1, },
                                { id: 'faultSite', header: '故障站点', fillspace: 1, },
                                { id: 'waitPpen', header: '待开通站点', fillspace: 1, },
                                { id: 'distributedRate', header: '全国分布率', fillspace: 1, template: function(obj, common, value){
                                    return value + '%';
                                } },
                                { id: 'linkStatus', header: '连接状态', fillspace: 1, template: function(){
                                    var isConnected = 1;
                                    return '<i class="fas '+(isConnected?'fa-link normal':'fa-unlink error')+'"></i>';
                                } },
                            ],
                        },
                        {},
                    ],
                },
                
                { height: 30, },
                {
                    cols: [
                        {},
                        {
                            rows: [
                                {
                                    cols: [
                                        { view: 'button', label: '搜索', width: 90, type:"iconButton", icon: 'search' },
                                        { view: 'button', label: '设置', width: 90, type:"iconButton", icon: 'cog', on: {
                                            'onItemClick': this._showSetting.bind(this),
                                        } },
                                        { view: 'button', label: '导出', width: 90, type:"iconButton", icon: 'file', on: {
                                            'onItemClick': this._doExport.bind(this),
                                        } },
                                        { view: 'button', label: '连接', width: 90, type:"iconButton", icon: 'link', },
                                    ],
                                },
                                { cols: [
                                    {},
                                    { view: 'button', id: this._loginBtnId, label: '登录', width: 90, type:"iconButton", icon: 'user', on: {
                                        'onItemClick': this._showLogin.bind(this),
                                    } },
                                    { view: 'button', id: this._logoutBtnId, label: '退出', width: 90, type:"iconButton", icon: 'user', on: {
                                        'onItemClick': this._doLogout.bind(this),
                                    }, hidden: true },
                                    {},
                                ] },
                            ],
                        },
                        {},
                    ],
                },
                {},
                { height: 30, },
            ],
        });
        setTimeout(function(){
            new Map($$(this._mapId).getNode().children[0].children[0]);
        }.bind(this), 0);
    };

    Module.prototype._showSetting = function(){
        if(this.checkLogin()){
            this.trigger('SETTING_CLICK');
        }
    };

    Module.prototype._showLogin = function(){
        this.trigger('LOGIN_CLICK');
    };
    Module.prototype._doLogout = function(){
        this.trigger('LOGOUT_CLICK');
    };
    Module.prototype._doExport = function(){
        window.open(this.Constant.serviceUrls.BASE_URL + this.Constant.serviceUrls.EXPORT_TOTAL);
    };

    Module.prototype.addListners = function(){
        this.on('LOGIN_SUCCESS', this._loginSuccess.bind(this));
        this.on('LOGOUT_SUCCESS', this._logoutSuccess.bind(this));
    };

    Module.prototype._loginSuccess = function(){
        $$(this._loginBtnId).hide();
        $$(this._logoutBtnId).show();
    };
    Module.prototype._logoutSuccess = function(){
        $$(this._logoutBtnId).hide();
        $$(this._loginBtnId).show();
    };

    Module.prototype._getTotalSuccess = function(data){
        var table = $$(this._statusTableId);
        table.clearAll();
        table.parse(data);
    };

    Module.prototype.ready = function(){
        this.ajax('get', this.Constant.serviceUrls.GET_TOTAL, this._getTotalSuccess.bind(this));
    };

    return Module;
});