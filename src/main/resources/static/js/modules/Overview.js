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
                    height: 20,
                    borderless: true,
                    template: function(){
                        var percents = 40;
                        return '<div class="loading" style="height:100%;"><i class="loaded"></i><i class="loaded"></i><i class="loaded"></i><i class="loaded"></i><i></i><i></i><i></i><i></i><i></i><i></i></div>';
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
                            width: 500,
                            autoheight: true,
                            scroll: false,
                            columns: [
                                { id: 'total', header: '总站点', fillspace: 1, },
                                { id: 'connected', header: '连接站点', fillspace: 1, },
                                { id: 'faulty', header: '故障站点', fillspace: 1, },
                                { id: 'toBeOpened', header: '待开通站点', fillspace: 1, },
                                { id: 'distributionRate', header: '全国分布率', fillspace: 1, },
                                { id: 'status', header: '连接状态', fillspace: 1, template: function(){
                                    var isConnected = 1;
                                    return '<i class="fas '+(isConnected?'fa-link normal':'fa-unlink')+'"></i>';
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
                                        { view: 'button', label: '导出', width: 90, type:"iconButton", icon: 'file', },
                                        { view: 'button', label: '连接', width: 90, type:"iconButton", icon: 'link', },
                                    ],
                                },
                                { cols: [
                                    {},
                                    { view: 'button', label: '登录', width: 90, type:"iconButton", icon: 'user', on: {
                                        'onItemClick': this._showLogin.bind(this),
                                    } },
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
        this.trigger('SETTING_CLICK');
    };

    Module.prototype._showLogin = function(){
        this.trigger('LOGIN_CLICK');
    };

    Module.prototype.ready = function(){
        //this.ajax('get');
        var data = [
            { total: '100', connected: '90', faulty: '100', toBeOpened: '10', distributionRate: '30%', status: '1', },
        ];
        $$(this._statusTableId).parse(data);
    };

    return Module;
});