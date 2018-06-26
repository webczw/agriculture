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
        this._propertyId = webix.uid();
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
                            id: this._propertyId,
                            view: 'property',
                            width: 200,
                            editable: false,
                            borderless: true,
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
                    { width: 10 },
                    {
                        rows: [
                            {},
                            {
                                height: 20,
                                borderless: true,
                                template: function(){
                                    var percents = 37;
                                    return '<div class="loading" style="height:100%;"><div class="loaded" style="height:100%; width:'+percents+'%;"></div></div>';
                                },
                            },
                            {},
                        ]
                    },
                    { width: 10 },
                    {
                        view: 'label', width: 30, height: 30, template: '<div class="settings" style="text-align:center;"><i class="fas fa-cog"></i></div>',
                    },
                ] },
                
                { height: 30, },
                {
                    view: 'treetable',
                    id: this._datatableId,
                    css: 'no_border',
                    autoheight: true,
                    scroll: false,
                    columns: [
                        //{ header: '', template: '{common.icon()}', width:50, },
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
                                        { view: 'button', label: '返回', on: {
                                            'onItemClick': this._goBack.bind(this),
                                        } },
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
            { total: '100', connected: '90', faulty: '100', toBeOpened: '10', distributionRate: '30%', status: '1', },
            { total: '100', connected: '90', faulty: '100', toBeOpened: '10', distributionRate: '30%', status: '1', },
            { total: '100', connected: '90', faulty: '100', toBeOpened: '10', distributionRate: '30%', status: '1', },
            { total: '100', connected: '90', faulty: '100', toBeOpened: '10', distributionRate: '30%', status: '1', },
            { total: '100', connected: '90', faulty: '100', toBeOpened: '10', distributionRate: '30%', status: '1', },
        ];
        $$(this._datatableId).parse(data);
        $$(this._propertyId).parse({
            width: '1108-01',
            height: '08',
            pass: '1100',
            aa: '1',
            url: '30',
        });
    };

    Module.prototype._createGauge = function(){
        var $nodes = $($$(this._gaugeId).getNode()).find('li');
        var option = [
            {
                series: [
                    {
                        title: '',
                        type: 'gauge',
                        radius: 75,
                        min: 10, max: 35, // 最小/大的数据值
                        splitNumber: 5, // 仪表盘刻度的分割段数
                        // 刻度样式
                        axisTick:{length:5,lineStyle:{color:'#fff'}},
                        // 轴线
                        axisLine: {show:true,lineStyle:{width:10,color:[[0.4, '#f00'], [0.8, '#399'], [1, '#fff']]}},
                        // 分隔线样式
                        splitLine: {length: 15},
                        pointer: {width: 5},
                        detail: {formatter:'V', fontSize:14, padding: [55,0,0,0] },
                        data: [{value: 21}]
                    }
                ]
            },
            {
                series: [
                    {
                        title: '',
                        type: 'gauge',
                        radius: 75,
                        min: -20, max: 100, // 最小/大的数据值
                        splitNumber: 12, // 仪表盘刻度的分割段数
                        // 刻度样式
                        axisTick:{length:5,lineStyle:{color:'#fff'}},
                        // 轴线
                        axisLine: {show:true,lineStyle:{width:10,color:[[1, '#eee']]}},
                        // 分隔线样式
                        splitLine: {length: 15},
                        pointer: {width: 5},
                        detail: {formatter:'温度: ℃', fontSize:14, padding: [55,0,0,0] },
                        data: [{value: 30}]
                    }
                ]
            },
            {
                series: [
                    {
                        title: '',
                        type: 'gauge',
                        radius: 75,
                        min: 0, max: 100, // 最小/大的数据值
                        splitNumber: 10, // 仪表盘刻度的分割段数
                        // 刻度样式
                        axisTick:{length:5,lineStyle:{color:'#fff'}},
                        // 轴线
                        axisLine: {show:true,lineStyle:{width:10,color:[[1, '#eee']]}},
                        // 分隔线样式
                        splitLine: {length: 15},
                        pointer: {width: 5},
                        detail: {formatter:'RH: %', fontSize:14, padding: [55,0,0,0] },
                        data: [{value: 52}]
                    }
                ]
            },
            {
                series: [
                    {
                        title: '',
                        type: 'gauge',
                        radius: 75,
                        min: 4, max: 9, // 最小/大的数据值
                        splitNumber: 10, // 仪表盘刻度的分割段数
                        // 刻度样式
                        axisTick:{length:5,lineStyle:{color:'#fff'}},
                        // 轴线
                        axisLine: {show:true,lineStyle:{width:10,color:[[0.3, '#f00'], [0.7, '#399'], [1, '#0000ff']]}},
                        // 分隔线样式
                        splitLine: {length: 15},
                        pointer: {width: 5},
                        detail: {formatter:'PH值', fontSize:14, padding: [55,0,0,0] },
                        data: [{value: 4.5}]
                    }
                ]
            },
        ];
        $nodes.each(function(n, node){
            var gauge = echarts.init(node);
            gauge.setOption(option[n]);
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
            css: 'has_background',
            head: false,
            position:"center",
            width: 1000,
            padding: 0, margin: 0, 
            borderless: true,
            body: {
                cols: [
                    {
                        view: 'datatable',
                        columns: [
                            { header: '<i class="fas fa-plus-circle"></i>', template: '<i class="fas fa-minus-circle"></i>', width:50, },
                            { id: 'no', header: '编号', fillspace: 1, },
                            { id: 'no', header: '地址码', fillspace: 1, },
                            { id: 'no', header: '光伏电压', fillspace: 1, },
                            { id: 'no', header: '电池电压', fillspace: 1, },
                            { id: 'no', header: '温度', fillspace: 1, },
                            { id: 'no', header: '湿度', fillspace: 1, },
                            { id: 'ph', header: 'PH值', fillspace: 1, },
                            { id: 'error', header: '故障', fillspace: 1, },
                        ],
                    },
                    { width: 10 },
                    {
                        width: 150,
                        rows: [
                            { height: 10 },
                            { borderless: true, height: 200, template: '<div class="sensorStatus"><ul><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li><li><i class="fas fa-square"></i></li></ul></div>' },
                            {},
                            { height: 10 },
                        ],
                    },
                ],
                
            },
        });
        var data = [
            {no:1,ph:5,error:1},
            {no:1,ph:5,error:1},
            {no:1,ph:5,error:1},
            {no:1,ph:5,error:1},
            {no:1,ph:5,error:1},
            {no:1,ph:5,error:1},
            {no:1,ph:5,error:1},
            {no:1,ph:5,error:1},
        ];
        this._popup.getBody().getChildViews()[0].parse(data);
    };

    Module.prototype._showSetting = function(){
        this.trigger('SETTING_CLICK');
    };

    Module.prototype._goBack = function(){
        this.trigger('BACK_OVERVIEW_CLICK');
    };

    return Module;
});