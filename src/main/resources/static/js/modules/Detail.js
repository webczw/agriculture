define([
    'Oop',
    baseUrl+'js/oop/BaseModule.js',
    'echarts',
], function(
    Oop,
    BaseModule,
    echarts
){
    "use strict";

    Oop.extend(Module, BaseModule);

    function Module(){
        this._gaugeId = webix.uid();
        this._datatableId = webix.uid();
        this._settingWindowId = webix.uid();
        this._popup = null;
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
                            cols: [
                                { id: this._gaugeId, width: 800, height: 200, borderless: true, template: '<div class="gauge"><ul><li></li><li></li><li></li><li></li></ul></div>'},
                            ],
                        },
                        {},
                        {
                            view: 'property',
                            width: 300,
                            editable: false,
                            elements: [
                                { label:"当前站点", type:"text", id:"width",},
                                { label:"传感器数量", type:"text", id:"height"},
                                { label:"接入站点", type:"text", id:"pass"},
                                { label:"故障站点", type:"text", id:"aa"},
                                { label:"环境温度", type:"text", id:"url"},
                                { label:"电池电压1", type:"text", id:"type"},
                                { label:"电池电压2", type:"text", id:"position"},
                                { label:"光伏电压", type:"text", id:"date"},
                                { label:"灯状态", type:"text", id:"color"},
                                { label:"传感器状态", type:"text", id:"jsonp"},
                                { label:"时间日期", type:"text", id:"dates"},
                            ]
                        },
                    ],
                },
                { height: 5, },
                { cols: [
                    { view: 'label', width: 100, height: 30, borderless: true, template: '<div style="height:100%;text-align:center;background-color:#399;color:#fff;">正常</div>'},
                    {
                        rows: [
                            {},
                            {
                                height: 20,
                                borderless: true,
                                template: function(){
                                    var percents = 37;
                                    return '<div class="progress" style="height:100%;background-color:#999;"><div class="progress_percents" style="background-color:#228ae6; height:100%; width:'+percents+'%;"></div></div>';
                                },
                            },
                            {},
                        ]
                    },
                    {
                        view: 'label', width: 30, height: 30, template: '<div class="settings" style="text-align:center;"><i class="fas fa-cog"></i></div>',
                    },
                ] },
                
                { height: 30, },
                {
                    view: 'treetable',
                    id: this._datatableId,
                    autoheight: true,
                    scroll: false,
                    columns: [
                        { header: '', template: '{common.icon()}', width:50, },
                        { header: '<i class="fas fa-plus-circle"></i> <i class="fas fa-cog"></i>', template: '<i class="fas fa-minus-circle"></i>', width: 60, },
                        { id: 'connectionStatus', header: '连接状态', fillspace: 1, template: function(){
                            var isConnected = 1;
                            return '<i class="fas fa-circle '+(isConnected?'normal':'error')+'"></i>';
                        } },
                        { id: 'connected', header: '站点编号', fillspace: 1, },
                        { id: 'faulty', header: '省', fillspace: 1, },
                        { id: 'toBeOpened', header: '市', fillspace: 1, },
                        { id: 'distributionRate', header: '县', fillspace: 1, },
                        { id: 'status', header: '站点代号', fillspace: 1, },
                        { id: 'status', header: '客户名称', fillspace: 1, },
                        { id: 'status', header: '时间/日期', fillspace: 1, },
                        { id: 'status', header: '环境/温度', fillspace: 1, },
                        { id: 'status', header: '电池电压', fillspace: 1, },
                        { id: 'status', header: '灯状态', fillspace: 1, },
                        { id: 'status', header: '光伏电压', fillspace: 1, },
                        { id: 'status', header: '传感器状态', fillspace: 1, },
                    ],
                    on: {
                        'onItemClick': this._rowItemClick.bind(this),
                    },
                },
                { height: 30, },
                {
                    cols: [
                        {},
                        {
                            width: 200,
                            rows: [
                                {
                                    cols: [
                                        { view: 'button', label: '搜索', },
                                        { view: 'button', label: '设置', on: {
                                            'onItemClick': this._showSetting.bind(this),
                                        } },
                                        { view: 'button', label: '导出', },
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
        this._createRowPopup();
        setTimeout(function(){
            this._createGauge();
        }.bind(this), 0);
    };

    Module.prototype.ready = function(){
        var data = [
            { total: '100', connected: '90', faulty: '100', toBeOpened: '10', distributionRate: '30%', status: '1', data: [
                { total: '100', connected: '90' }
            ] },
        ];
        $$(this._datatableId).parse(data);
    };

    Module.prototype._createGauge = function(){
        var $nodes = $($$(this._gaugeId).getNode()).find('li');
        var option = {
            tooltip : {
                formatter: "{a} <br/>{b} : {c}%"
            },
            series: [
                {
                    name: '业务指标',
                    type: 'gauge',
                    detail: {formatter:'{value}%'},
                    data: [{value: 50, name: '完成率'}]
                }
            ]
        };
        $nodes.each(function(n, node){
            var gauge = echarts.init(node);
            gauge.setOption(option);
        });
        
    };


    Module.prototype._rowItemClick = function(ids, e, node){
        var datatable = $$(this._datatableId);
        var id = ids.row;
        this._popup.show(datatable.getItemNode(id));
    };

    Module.prototype._createRowPopup = function(){
        this._popup = webix.ui({
            view: 'popup',
            head: false,
            position:"center",
            width: 1280,
            body: {
                cols: [
                    {
                        view: 'datatable',
                        columns: [
                            { header: '<i class="fas fa-plus-circle"></i>', template: '<i class="fas fa-minus-circle"></i>', width:50, },
                            { header: '编号', fillspace: 1, },
                            { header: '地址码', fillspace: 1, },
                            { header: '光伏电压', fillspace: 1, },
                            { header: '电池电压', fillspace: 1, },
                            { header: '温度', fillspace: 1, },
                            { header: '湿度', fillspace: 1, },
                            { header: 'PH值', fillspace: 1, },
                            { header: '故障', fillspace: 1, },
                        ],
                    },
                    { width: 10 },
                    {
                        width: 150,
                        rows: [
                            { borderless: true, template: '<div class="sensorStatus"><ul><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li></ul></div>' }
                        ],
                    },
                ],
                
            },
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

    return Module;
});