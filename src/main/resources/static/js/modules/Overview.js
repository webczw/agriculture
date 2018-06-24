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
        this._settingWindowId = webix.uid();
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
                                template: '<div style="font-size:48px;"><i class="fas fa-angle-right"></i><i class="fas fa-angle-right"></i><i class="fas fa-angle-right"></i><i class="fas fa-angle-right"></i></div>',
                            },
                            {},
                        ] },
                        {},
                        { width: 500, height: 400, id: this._mapId, borderless: true, template: '<div class="map" style="height:100%;"></div>'}
                    ],
                },
                { height: 5, },
                {
                    height: 20,
                    borderless: true,
                    template: function(){
                        var percents = 37;
                        return '<div class="progress" style="height:100%;background-color:#999;"><div class="progress_percents" style="background-color:#228ae6; height:100%; width:'+percents+'%;"></div></div>';
                    },
                },
                {
                    height: 30,
                    borderless: true,
                    template: '<div style="text-align:center;">2018-06-10 ---- BDN001 ---- REV10</div>',
                },
                { height: 30, },
                {
                    view: 'datatable',
                    id: this._statusTableId,
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
                            return '<i class="fas '+(isConnected?'fa-link active':'fa-unlink')+'"></i>';
                        } },
                    ],
                },
                { height: 30, },
                {
                    cols: [
                        {},
                        {
                            width: 200,
                            rows: [
                                { view: 'button', label: '搜索', },
                                {
                                    cols: [
                                        { view: 'button', label: '设置', on: {
                                            'onItemClick': this._showSetting.bind(this),
                                        } },
                                        { view: 'button', label: '导出', },
                                        { view: 'button', label: '连接', },
                                    ],
                                }
                            ],
                        },
                        {},
                    ],
                },
                {},
                { height: 30, },
            ],
        });
    };

    Module.prototype._showSetting = function(){
        if(!$$(this._settingWindowId)){
            var view = webix.ui({
                view: 'window',
                id: this._settingWindowId,
                head: '设置',
                position: 'center',
                modal: true,
                body: {
                    view: 'form',
                    width: 400, height: 400,
                    borderless: true,
                    rows: [
                        { view: 'text', label: '自动更新时间', labelWidth: 120, },
                        { view: 'combo', label: '导出文件格式', labelWidth: 120, options: [{id:'pdf',value:'PDF'},{id:'word',value:'WORD'},] },
                        { view: 'text', label: '导出文件路径', labelWidth: 120, },
                        { view: 'text', label: '自动存储时间', labelWidth: 120, },
                        { view: 'text', label: '可进行数据存储的站点', labelWidth: 120, },
                        { view: 'text', label: '常备的存储文件', labelWidth: 120, },
                        {},
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

    Module.prototype.ready = function(){
        //this.ajax('get');
        var data = [
            { total: '100', connected: '90', faulty: '100', toBeOpened: '10', distributionRate: '30%', status: '1', },
        ];
        $$(this._statusTableId).parse(data);
        new Map($$(this._mapId).getNode().children[0].children[0]);
    };

    return Module;
});