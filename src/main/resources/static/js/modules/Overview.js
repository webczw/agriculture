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
        this._loadingId = webix.uid();
        this._statusTableId = webix.uid();
        this._loginBtnId = webix.uid();
        this._logoutBtnId = webix.uid();

        this._map = null;
        this._linkStatus = null;
        
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
                                        this.trigger('DETAIL_CLICK');
                                    }.bind(this),
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
                { height: 25, },
                {
                    height: 20,
                    rows: [
                        {
                            id: this._loadingId,
                            height: 20,
                            borderless: true,
                            template: function(values){
                                var percents = values && values.percents || 0;
                                var html = '';
                                for(var i=0; i<10; i++){
                                    if(percents/10 >= i) html += '<i class="loaded"></i>';
                                    else html += '<i></i>';
                                }
                                return '<div class="loading" style="height:100%;">'+html+'</div>';
                            },
                        },
                    ],
                },
                {
                    height: 30,
                    borderless: true,
                    template: '<div class="spacer"></div><div style="text-align:center;line-height:30px;">BDN001REV1020180610</div>',
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
                                { id: 'linkStatus', header: '连接状态', fillspace: 1, template: function(obj, common, value){
                                    return '<i class="fas '+(value === 1?'fa-link linked':'fa-unlink error')+'"></i>';
                                } },
                            ],
                        },
                        {},
                    ],
                },
                {},
                { height: 30, },
                {
                    cols: [
                        {},
                        {
                            rows: [
                                {
                                    cols: [
                                        { view: 'button', label: '搜索', width: 90, type:"iconButton", icon: 'search', on: {
                                            'onItemClick': this._showSearch.bind(this),
                                        }  },
                                        { view: 'button', label: '设置', width: 90, type:"iconButton", icon: 'cog', on: {
                                            'onItemClick': function(){
                                                this.checkLogin(this._showSetting.bind(this));
                                            }.bind(this),
                                        } },
                                        { view: 'button', label: '导出', width: 90, type:"iconButton", icon: 'file', on: {
                                            'onItemClick': function(){
                                                this.checkLogin(this._doExport.bind(this));
                                            }.bind(this),
                                        } },
                                        { view: 'button', label: '连接', width: 90, type:"iconButton", icon: 'link', on: {
                                            'onItemClick': this._doConnect.bind(this),
                                        } },
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
                { height: 30, },
            ],
        });
        
    };

    Module.prototype._showSearch = function(){
        this.trigger('SEARCH_CLICK');
    };

    Module.prototype._showSetting = function(){
        this.trigger('SETTING_CLICK');
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
    Module.prototype._doConnect = function(){
        this._refreshLoading();
        this.ajax('get', this.Constant.serviceUrls.CONNECT, {});
    };

    Module.prototype.addListners = function(){
        Module._super.addListners.call(this);
        this.on('LOGIN_SUCCESS', this._loginSuccess.bind(this));
        this.on('LOGOUT_SUCCESS', this._logoutSuccess.bind(this));
        this.on('ADD_MAIN_SITE_SUCCESS', this._getTotalData.bind(this));
        this.on('DELETE_MAIN_SITE_SUCCESS', this._getTotalData.bind(this));
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
        //data[0].linkStatus = 1;
        this._linkStatus = data[0] && data[0].linkStatus || 0;
        var table = $$(this._statusTableId);
        if(!table) return;
        table.clearAll();
        table.parse(data);
    };

    Module.prototype._getMapSuccess = function(data){
        this._map && this._map.setValues(data);
    };

    Module.prototype._refreshLoading = function(){
        var loading = $$(this._loadingId);
        var percents = 0;
        loading.setValues({percents:percents});
        loading.show();
        var table = $$(this._statusTableId);
        var totalData = table.serialize();
        if(totalData[0]){
            totalData[0].linkStatus = 0;
            table.refresh(totalData[0].id);
        }
        var refreshLoading = function(){
            var loading_st = setTimeout(function(){
                var loading = $$(this._loadingId);
                if(!loading) return;
                percents += Math.random() * (20 - 0) + 0; // 加0-20随机数
                loading.setValues({percents:percents});
                if(percents < 100) refreshLoading();
                //else loading.hide();
                else{
                    var totalData = table.serialize();
                    if(totalData[0]){
                        totalData[0].linkStatus = this._linkStatus;
                        table.refresh(totalData[0].id);
                    }
                }
            }.bind(this), Math.random() * (500 - 50) + 0); // 加50-500随机数
        }.bind(this);
        refreshLoading();
    };

    Module.prototype._getTotalData = function(){
        this.ajax('get', this.Constant.serviceUrls.GET_TOTAL, {}, this._getTotalSuccess.bind(this));
    };

    Module.prototype.ready = function(){
        if(!this.Model.getInstance().getValue('USER')){
            this.trigger('GET_USER');
        }
        else{
            this._loginSuccess();
        }
        this._getTotalData();
        this.ajax('get', this.Constant.serviceUrls.GET_PROVINCE, {}, this._getMapSuccess.bind(this));
        setTimeout(function(){
            this._map = new Map($$(this._mapId).getNode().children[0].children[0]);
        }.bind(this), 0);

        // 刷新loading条
        this._refreshLoading();
    };

    Module.prototype.destroy = function(){
        this._map.destroy();
        this._map = null;
        Module._super.destroy.call(this);
    };

    return Module;
});