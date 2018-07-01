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

    function Module(mainSiteData){
        this._gaugeId = webix.uid();
        this._datatableId = webix.uid();
        this._settingWindowId = webix.uid();
        this._propertyId = webix.uid();
        this._sensorListStatusId = webix.uid();

        this._popup = null;
        this._gauges = [];

        this._mainSiteData = mainSiteData || {};

        Module._super.constructor.call(this);
    }
    
    Module.prototype.ui = function(){
        Module._super.ui.call(this);
        var view = webix.ui({
            id: this.viewId,
            rows: [
                { height: 25, },
                {
                    cols: [
                        {
                            rows: [
                                {},
                                { id: this._gaugeId, width: 800, height: 200, borderless: true, template: '<div class="gauge"><ul><li></li><li></li><li></li><li></li></ul></div>'},
                                {},
                            ],
                        },
                        {},
                        {
                            rows: [
                                {},
                                {
                                    id: this._propertyId,
                                    view: 'property',
                                    width: 200,
                                    height: 200,
                                    editable: false,
                                    borderless: true,
                                    elements: [
                                        { id:"mainSiteCode", label:"当前站点", type:"text",},
                                        { id:"sensorCount", label:"传感器数量", type:"text",},
                                        { id:"siteCode", label:"接入塔点", type:"text",},
                                        { id:"faultySites", label:"故障塔点数量", type:"text",},
                                        { id:"temperature", label:"环境温度", type:"text",},
                                        { id:"voltage", label:"电池电压", type:"text",},
                                        { id:"photovoltaic", label:"光伏电压", type:"text",},
                                        { id:"lightStatus", label:"灯状态", type:"text",},
                                        { id:"sensorStatus", label:"传感器状态", type:"text",},
                                        { id:"datetime", label:"时间日期", type:"text",},
                                    ]
                                },
                                {},
                            ],
                        },
                    ],
                },
                { height: 45, },
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
                                    return '<div class="loading" style="height:100%;"><i class="loaded"></i><i class="loaded"></i><i class="loaded"></i><i class="loaded"></i><i class="loaded"></i><i class="loaded"></i><i class="loaded"></i><i class="loaded"></i><i class="loaded"></i><i class="loaded"></i></div>';
                                },
                            },
                            {},
                        ]
                    },
                ] },
                
                { height: 30, },
                {
                    view: 'treetable',
                    id: this._datatableId,
                    css: 'no_border',
                    autoheight: true,
                    scroll: false,
                    select: 'row',
                    columns: [
                        //{ header: '', template: '{common.icon()}', width:50, },
                        { header: '<i class="fas fa-plus-circle add"></i>', template: '<i class="fas fa-minus-circle delete"></i> <i class="fas fa-cog setting"></i>', width: 60, },
                        { id: 'linkStatus', header: '连接状态', fillspace: 1, template: function(obj, common, value){
                            return '<i class="fas fa-circle '+(value === 1? 'linked': 'error')+'"></i>';
                        } },
                        { id: 'siteNumber', header: '站点编号', fillspace: 1, },
                        { id: 'province', header: '省', fillspace: 1, },
                        { id: 'city', header: '市', fillspace: 1, },
                        { id: 'county', header: '县', fillspace: 1, },
                        { id: 'siteCode', header: '站点代号', fillspace: 1, },
                        { id: 'siteName', header: '站点名称', fillspace: 1, },
                        { id: 'dateTime', header: '时间/日期', fillspace: 1, format: function(value){
                            return webix.Date.dateToStr('%Y-%m-%d')(new Date(value));
                        }, },
                        { id: 'temperature', header: '环境温度', fillspace: 1, template: function(obj, common, value){
                            return value+'°C';
                        } },
                        { id: 'voltage', header: '电池电压', fillspace: 1, template: function(obj, common, value){
                            return value+'V';
                        } },
                        { id: 'lightStatus', header: '灯状态', fillspace: 1, template: function(obj, common, value){
                            return value === 1? '正常': '故障';
                        } },
                        { id: 'photovoltaic', header: '光伏电压', fillspace: 1, template: function(obj, common, value){
                            return value+'V';
                        } },
                        { id: 'sensorStatus', header: '传感器状态', fillspace: 1, template: function(obj, common, value){
                            return value === 1? '正常': '故障';
                        } },
                    ],
                    on: {
                        'onItemClick': this._rowItemClick.bind(this),
                        'onSelectChange': this._rowItemSelect.bind(this),
                        'onHeaderClick': this._headerClick.bind(this),
                    },
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
                                            'onItemClick': function(){
                                                this.trigger('SEARCH_CLICK');
                                            }.bind(this),
                                        } },
                                        { view: 'button', label: '设置', width: 90, type:"iconButton", icon: 'cog', on: {
                                            'onItemClick': function(){
                                                this.trigger('SETTING_CLICK');
                                            }.bind(this),
                                        } },
                                        { view: 'button', label: '导出', width: 90, type:"iconButton", icon: 'file', on: {
                                            'onItemClick': this._doExport.bind(this),
                                        } },
                                        { view: 'button', label: '返回', width: 90, type:"iconButton", icon: 'long-arrow-left', on: {
                                            'onItemClick': function(){
                                                this.trigger('BACK_OVERVIEW_CLICK');
                                            }.bind(this),
                                        } },
                                    ],
                                },
                            ],
                        },
                        {},
                    ],
                },
                { height: 30, },
            ],
        });
        this._createRowPopup();
        setTimeout(function(){
            this._createGauge();
        }.bind(this), 0);
    };

    Module.prototype.addListners = function(){
        Module._super.addListners.call(this);
    };

    Module.prototype.ready = function(){
        this.ajax('get', this.Constant.serviceUrls.GET_LIGHT_HOUSE_LIST, {siteId: this._mainSiteData.siteId}, function(data){
            var datatable = $$(this._datatableId);
            datatable.parse(data);
            datatable.select(datatable.getIdByIndex(0));
            if(data && data.length > 0){
                this._mainSiteData.siteId = data[0].siteId;
                this._mainSiteData.siteCode = data[0].mainSiteCode;
            }
        }.bind(this));
    };


    Module.prototype._headerClick = function(ids, e, node){
        if($(e.target).hasClass('add')){
            webix.ui({
                view: 'window',
                head: '增加灯塔',
                position:"center",
                borderless: true,
                body: {
                    view: 'form',
                    rows: [
                        { id: 'siteCode', header: '站点代号' },
                        {
                            height: 30,
                            cols: [
                                {},
                                { view: 'button', label: '确定', width: 90, on: {
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
                }
            }).show();
        }
    };

    Module.prototype._rowItemClick = function(ids, e, node){
        var datatable = $$(this._datatableId);
        var rowData = datatable.getItem(ids.row);

        if($(e.target).hasClass('delete')){
            this.confirm('确认删除？', function(confirm){
                if(!confirm) return;
                this.ajax(this.Constant.serviceUrls.DELETE_LIGHT_HOUSE + '/' + rowData.lighthouseId, {}, function(){
                    this.message(this.Constant.info.SUCCESS);
                    datatable.remove(ids.row);
                }.bind(this));
            }.bind(this));
            return;
        }


        var sensorList = rowData.sensorList;
        var popupDatatable = this._popup.getBody().getChildViews()[0];
        popupDatatable.clearAll();
        popupDatatable.parse(sensorList);
        this._popup.show(datatable.getItemNode(ids.row));
        $$(this._sensorListStatusId).setValues({
            sensorListStatus: sensorList.map(function(sensor){
                return sensor.linkStatus;
            }),
        });
    };

    Module.prototype._rowItemSelect = function(){
        var datatable = $$(this._datatableId);
        var id = datatable.getSelectedId();
        var rowData = datatable.getItem(id);
        var sensorData = rowData.sensorList.filter(function(sensor){
            return sensor.linkStatus === 1;
        })[0] || {};

        // 属性
        var propertyData = {
            "mainSiteCode": rowData.mainSiteCode,
            "sensorCount": rowData.sensorList.length,
            "siteCode": rowData.siteCode,
            "faultySites": rowData.sensorList.filter(function(sensor){
                return sensor.linkStatus === 0;
            }).length,
            "temperature": rowData.temperature + '°C',
            "voltage": rowData.voltage + 'V',
            "photovoltaic": rowData.photovoltaic + 'V',
            "lightStatus": rowData.lightStatus,
            "sensorStatus": rowData.sensorStatus,
            "datetime": webix.Date.dateToStr('%Y-%m-%d')(new Date()),
        };
        $$(this._propertyId).parse(propertyData);

        // 仪表盘
        var gaugeData = [
            rowData.voltage,
            sensorData.temperature || 0,
            sensorData.humidity || 0,
            sensorData.phValue || 0,
        ];
        this._gauges.forEach(function(gauge, g){
            gauge.setOption({
                series: [{
                    data: [{
                        value: gaugeData[g],
                    }],
                }],
            });
        });
    };

    Module.prototype._createRowPopup = function(){
        this._popup = webix.ui({
            view: 'popup',
            css: 'has_background',
            head: false,
            position:"center",
            width: this.Constant.default.BODY_WIDTH,
            padding: 0, margin: 0, 
            borderless: true,
            body: {
                cols: [
                    {
                        view: 'datatable',
                        css: 'no_border',
                        columns: [
                            { header: '<i class="fas fa-plus-circle add"></i>', template: '<i class="fas fa-minus-circle delete"></i>', width:50, },
                            { id: 'linkStatus', header: '连接状态', fillspace: 1, template: function(obj, common, value){
                                return '<i class="fas fa-circle '+(value === 1? 'linked': 'error')+'"></i>';
                            } },
                            { id: 'number', header: '编号', fillspace: 1, },
                            { id: 'addressCode', header: '地址码', fillspace: 1, },
                            { id: 'photovoltaic', header: '光伏电压', fillspace: 1, template: function(obj, common, value){
                                return value+'V';
                            } },
                            { id: 'voltage', header: '电池电压', fillspace: 1, template: function(obj, common, value){
                                return value+'V';
                            } },
                            { id: 'temperature', header: '温度', fillspace: 1, template: function(obj, common, value){
                                return value+'°C';
                            } },
                            { id: 'humidity', header: '湿度', fillspace: 1, template: function(obj, common, value){
                                return value+'%';
                            } },
                            { id: 'phValue', header: 'PH值', fillspace: 1, },
                            { id: 'fault', header: '故障', fillspace: 1, },
                        ],
                    },
                    {
                        rows: [
                            { height: 20, },
                            { cols: [
                                { width: 20, },
                                { id: this._sensorListStatusId, borderless: true, width: 150, height: 200, padding:20, template: function(values){
                                    var sensorListStatus = values.sensorListStatus || [];
                                    var html = '';
                                    for(var i=0; i<12; i++){
                                        if(sensorListStatus[i] === undefined) html += '<li><i class="fas fa-square"></i></li>';
                                        else html += '<li><i class="fas fa-square '+(sensorListStatus[i] === 1? 'linked': 'error')+'"></i></li>';
                                    }
                                    return '<div class="sensorStatus"><ul>' + html + '</ul></div>';
                                } },
                                { width: 20, },
                            ] },
                            {},
                        ],
                    },
                ],
                
            },
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
            this._gauges.push(gauge);
        }.bind(this));
        
    };

    Module.prototype._doExport = function(){
        window.open(this.Constant.serviceUrls.BASE_URL + this.Constant.serviceUrls.EXPORT_LIGHT_HOUSE + '?siteId=' + this._mainSiteData.siteId);
    };

    Module.prototype.destroy = function(){
        this._popup.destructor();
        this._gauges.forEach(function(gauge){
            gauge.dispose();
        });
        this._gauges = [];
        Module._super.destroy.call(this);
    };

    return Module;
});