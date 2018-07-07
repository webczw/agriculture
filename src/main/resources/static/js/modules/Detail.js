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
        this._siteStatusId = webix.uid();
        this._loadingId = webix.uid();
        this._gaugeId = webix.uid();
        this._datatableId = webix.uid();
        this._settingWindowId = webix.uid();
        this._propertyId = webix.uid();
        this._sensorListStatusId = webix.uid();
        this._addLightHouseWindowId = webix.uid();
        this._addSensorWindowId = webix.uid();
        this._lightHouseSettingWindowId = webix.uid();

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
                    { id: this._siteStatusId, view: 'label', width: 100, height: 30, borderless: true, template: function(obj){
                        var siteStatus = obj.siteStatus;
                        return '<div class="'+(siteStatus === 1? 'bg_normal': 'bg_error')+'" style="height:100%;text-align:center;color:#fff;">'+(siteStatus === 1? '正常': '故障')+'</div>';
                    } },
                    { width: 10 },
                    {
                        rows: [
                            {},
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
                        { header: '<i class="fas fa-plus-circle add"></i>', template: '<i class="fas fa-minus-circle delete"></i> <i class="fas fa-cog setting"></i>', width: 50, },
                        { id: 'linkStatus', header: '连接状态', fillspace: 1, template: function(obj, common, value){
                            return '<i class="fas fa-circle '+(value === 1? 'linked': 'error')+'"></i>';
                        } },
                        { id: 'siteNumber', header: '站点编号', fillspace: 1, },
                        { id: 'province', header: '省', fillspace: 0.8, },
                        { id: 'city', header: '市', fillspace: 0.8, },
                        { id: 'county', header: '县', fillspace: 0.8, },
                        { id: 'siteCode', header: '站点代号', fillspace: 1, },
                        { id: 'siteName', header: '站点名称', fillspace: 1, },
                        { id: 'dateTime', header: '时间/日期', fillspace: 1, format: function(value){
                            return value? webix.Date.dateToStr('%Y-%m-%d')(new Date(value)): '';
                        }, },
                        { id: 'temperature', header: '环境温度', fillspace: 1, template: function(obj, common, value){
                            return (value !== null && value !== '')? (value+'°C'): '';
                        } },
                        { id: 'voltage', header: '电池电压', fillspace: 1, template: function(obj, common, value){
                            return (value !== null && value !== '')? (value+'V'): '';
                        } },
                        { id: 'lightStatus', header: '灯状态', fillspace: 1, template: function(obj, common, value){
                            if(value === null || value === '') return '';
                            return value === 1? '正常': '故障';
                        } },
                        { id: 'photovoltaic', header: '光伏电压', fillspace: 1, template: function(obj, common, value){
                            return (value !== null && value !== '')? (value+'V'): '';
                        } },
                        { id: 'sensorStatus', header: '传感器状态', fillspace: 1.2, template: function(obj, common, value){
                            if(value === null || value === '') return '';
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
                                            'onItemClick': this._showSearch.bind(this),
                                        } },
                                        { view: 'button', label: '设置', width: 90, type:"iconButton", icon: 'cog', on: {
                                            'onItemClick': function(){
                                                this.checkLogin(this._showSetting.bind(this));
                                            }.bind(this),
                                        } },
                                        { view: 'button', label: '导出', width: 90, type:"iconButton", icon: 'file', on: {
                                            'onItemClick': function(){
                                                this.checkLogin(this._doExportMainSite.bind(this));
                                            }.bind(this),
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
        Module._super.ready.call(this);
        this.ajax('get', this.Constant.serviceUrls.GET_LIGHT_HOUSE_LIST, {siteId: this._mainSiteData.siteId}, function(data){
            var datatable = $$(this._datatableId);
            datatable.parse(data.lighthouseList || []);
            var firstId = datatable.getIdByIndex(0);
            if(!!firstId) datatable.select(firstId);
            
            this._mainSiteData = data;
            
        }.bind(this));

        var loading = $$(this._loadingId);
        var percents = 100;
        loading.setValues({percents:percents});
    };

    Module.prototype._refreshLoading = function(){
        var loading = $$(this._loadingId);
        var percents = 0;
        loading.setValues({percents:percents});
        loading.show();
        var refreshLoading = function(){
            var loading_st = setTimeout(function(){
                var loading = $$(this._loadingId);
                if(!loading) return;
                percents += Math.random() * (20 - 0) + 0; // 加0-20随机数
                loading.setValues({percents:percents});
                if(percents < 100) refreshLoading();
                //else loading.hide();
                else{
                }
            }.bind(this), Math.random() * (500 - 50) + 0); // 加50-500随机数
        }.bind(this);
        refreshLoading();
    };

    Module.prototype._headerClick = function(ids, e, node){
        if($(e.target).hasClass('add')){
            this.checkLogin(function(){
                this._addLightHouse();
            }.bind(this));
            return;
        }
    };

    Module.prototype._rowItemClick = function(ids, e, node){
        if($(e.target).hasClass('delete')){
            this.checkLogin(function(){
                this._deleteLightHouse(ids.row);
            }.bind(this));
            return;
        }
        else if($(e.target).hasClass('setting')){
            this.checkLogin(function(){
                this._settingLightHouse(ids.row);
            }.bind(this));
            return;
        }

        var datatable = $$(this._datatableId);
        var rowData = datatable.getItem(ids.row);
        var sensorList = rowData.sensorList || [];
        var popupDatatable = this._popup.getBody().getChildViews()[0];
        popupDatatable.clearAll();
        popupDatatable.parse(sensorList);
        this._popup.show(datatable.getItemNode(ids.row));
        this._refreshSensorListStatus();
    };

    Module.prototype._refreshSensorListStatus = function(){
        var sensorList = $$(this._datatableId).getSelectedItem().sensorList || [];
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
        var sensorData = rowData.sensorList && rowData.sensorList.filter(function(sensor){
            return sensor.linkStatus === 1;
        })[0] || {};

        // 属性
        var propertyData = {
            "mainSiteCode": rowData.mainSiteCode,
            "sensorCount": rowData.sensorList && rowData.sensorList.length || 0,
            "siteCode": rowData.siteCode !== null ? rowData.siteCode: '',
            "faultySites": rowData.sensorList && rowData.sensorList.filter(function(sensor){
                return sensor.linkStatus === 0;
            }).length || 0,
            "temperature": rowData.temperature !== null ? (rowData.temperature + '°C'): '',
            "voltage": rowData.voltage !== null ? (rowData.voltage + 'V'): '',
            "photovoltaic": rowData.photovoltaic !== null ? (rowData.photovoltaic + 'V'): '',
            "lightStatus": rowData.lightStatus !== null ? rowData.lightStatus: '',
            "sensorStatus": rowData.sensorStatus !== null ? rowData.sensorStatus: '',
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
                        scrollX: false,
                        columns: [
                            { header: '<i class="fas fa-plus-circle add"></i>', template: '<i class="fas fa-minus-circle delete"></i>', width:50, },
                            { id: 'linkStatus', header: '连接状态', fillspace: 1, template: function(obj, common, value){
                                return '<i class="fas fa-circle '+(value === 1? 'linked': 'error')+'"></i>';
                            } },
                            { id: 'number', header: '编号', fillspace: 1, },
                            { id: 'addressCode', header: '地址码', fillspace: 1, },
                            { id: 'photovoltaic', header: '光伏电压', fillspace: 1, template: function(obj, common, value){
                                return (value !== null && value !== '')? (value+'V'): '';
                            } },
                            { id: 'voltage', header: '电池电压', fillspace: 1, template: function(obj, common, value){
                                return (value !== null && value !== '')? (value+'V'): '';
                            } },
                            { id: 'temperature', header: '温度', fillspace: 1, template: function(obj, common, value){
                                return (value !== null && value !== '')? (value+'°C'): '';
                            } },
                            { id: 'humidity', header: '湿度', fillspace: 1, template: function(obj, common, value){
                                return (value !== null && value !== '')? (value+'%'): '';
                            } },
                            { id: 'phValue', header: 'PH值', fillspace: 1, },
                            { id: 'fault', header: '故障', fillspace: 1, },
                        ],
                        on: {
                            'onItemClick': function(ids, e, node){
                                var datatable = this._popup.getBody().getChildViews()[0];
                                var rowData = datatable.getItem(ids.row);

                                if($(e.target).hasClass('delete')){
                                    this.confirm('确认删除？', function(confirm){
                                        if(!confirm) return;
                                        this.ajax('del', this.Constant.serviceUrls.DELETE_SENSOR + '/' + rowData.sensorId, {}, function(){
                                            this.message(this.Constant.info.SUCCESS);
                                            var index = datatable.getIndexById(ids.row);
                                            datatable.remove(ids.row);
                                            $$(this._datatableId).getSelectedItem().sensorList.splice(index, 1);
                                            this._refreshSensorListStatus();
                                        }.bind(this));
                                    }.bind(this));
                                    return;
                                }
                            }.bind(this),
                            'onHeaderClick': function(ids, e, node){
                                if($(e.target).hasClass('add')){
                                    var view = webix.ui({
                                        id: this._addSensorWindowId,
                                        view: 'window',
                                        head: '增加传感器',
                                        position:"center",
                                        borderless: true,
                                        body: {
                                            view: 'form',
                                            width: 400,
                                            rows: [
                                                { name: 'addressCode', view: 'text', label: '地址码', labelWidth: 120, required: true, },
                                                {
                                                    height: 30,
                                                    cols: [
                                                        {},
                                                        { view: 'button', label: '确定', width: 90, on: {
                                                            'onItemClick': function(){
                                                                var addSensorWindow = $$(this._addSensorWindowId);
                                                                var addSensorForm = addSensorWindow.getBody();
                                                                if(!addSensorForm.validate()) return;
                                                                var values = addSensorForm.getValues();
                                                                values.siteId = this._mainSiteData.siteId;
                                                                values.lighthouseId = $$(this._datatableId).getSelectedItem().lighthouseId;
                                                                this.ajax('post', this.Constant.serviceUrls.ADD_SENSOR, values, function(data){
                                                                    this.message(this.Constant.info.SUCCESS);
                                                                    var datatable = this._popup.getBody().getChildViews()[0];
                                                                    datatable.add(data);
                                                                    $$(this._datatableId).getSelectedItem().sensorList.push(data);
                                                                    this._refreshSensorListStatus();
                                                                    addSensorWindow.close();
                                                                }.bind(this));
                                                            }.bind(this),
                                                        } },
                                                        { view: 'button', label: '取消', width: 90, on: {
                                                            'onItemClick': function(){
                                                                $$(this._addSensorWindowId).close();
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
                            }.bind(this),
                        },
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
                            { height: 20, },
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

    Module.prototype._showSearch = function(){
        this.trigger('SEARCH_CLICK');
    };

    Module.prototype._showSetting = function(){
        this.trigger('SETTING_CLICK');
    };

    Module.prototype._addLightHouse = function(){
        var view = webix.ui({
            id: this._addLightHouseWindowId,
            view: 'window',
            head: '增加灯塔',
            position:"center",
            borderless: true,
            body: {
                view: 'form',
                width: 400,
                rows: [
                    { name: 'siteCode', view: 'text', label: '站点代号', labelWidth: 120, required: true, },
                    {
                        height: 30,
                        cols: [
                            {},
                            { view: 'button', label: '确定', width: 90, on: {
                                'onItemClick': function(){
                                    var addLightHouseWindow = $$(this._addLightHouseWindowId);
                                    var addLightHouseForm = addLightHouseWindow.getBody();
                                    if(!addLightHouseForm.validate()) return;
                                    var values = addLightHouseForm.getValues();
                                    values.siteId = this._mainSiteData.siteId;
                                    this.ajax('post', this.Constant.serviceUrls.ADD_LIGHT_HOUSE, values, function(data){
                                        this.message(this.Constant.info.SUCCESS);
                                        data.sensorList = data.sensorList || []; // 可能sensorList为空的情况
                                        var datatable = $$(this._datatableId);
                                        datatable.add(data);
                                        this._mainSiteData.lighthouseList.push(data);
                                        addLightHouseWindow.close();
                                    }.bind(this));
                                }.bind(this),
                            } },
                            { view: 'button', label: '取消', width: 90, on: {
                                'onItemClick': function(){
                                    $$(this._addLightHouseWindowId).close();
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
    };

    Module.prototype._deleteLightHouse = function(id){
        var datatable = $$(this._datatableId);
        var rowData = datatable.getItem(id);
        this.confirm('确认删除？', function(confirm){
            if(!confirm) return;
            this.ajax('del', this.Constant.serviceUrls.DELETE_LIGHT_HOUSE + '/' + rowData.lighthouseId, {}, function(){
                this.message(this.Constant.info.SUCCESS);
                var index = datatable.getIndexById(id);
                datatable.remove(id);
                this._mainSiteData.lighthouseList.splice(index, 1);
            }.bind(this));
        }.bind(this));
    };

    Module.prototype._settingLightHouse = function(id){
        var datatable = $$(this._datatableId);
        var rowData = datatable.getItem(id);
        var view = webix.ui({
            id: this._lightHouseSettingWindowId,
            view: 'window',
            head: '灯塔设置',
            position:"center",
            borderless: true,
            body: {
                view: 'form',
                width: 500,
                rows: [
                    //{ name: 'refreshDate', view: 'datepicker', timepicker: true, stringResult: false, format: '%Y-%m-%d %H:%i:%s', label: '表头数据刷新时间', labelWidth: 150, },
                    { name: 'refreshDate', view: 'text', label: '灯塔数据上传周期', labelWidth: 180, },
                    { name: 'phone', view: 'text', label: '提示信息关连手机', labelWidth: 180, },
                    { name: 'onOffFlag', view: 'radio', label: '风机/灯开关设置', labelWidth: 180, options: [{id:'1',value:'开'},{id:'2',value:'关'},{id:'3',value:'他控'}] },
                    { name: 'bootDateDelay', view: 'text', label: '开关时间延时设置（4-10 小时）', labelWidth: 180, },
                    { name: 'delay', view: 'text', label: '他控延时设置（1-24 小时）', labelWidth: 180, },
                    {
                        height: 30,
                        cols: [
                            {},
                            { view: 'button', label: '确定', width: 90, on: {
                                'onItemClick': function(){
                                    var values = $$(this._lightHouseSettingWindowId).getBody().getValues();
                                    //values.siteId = this._mainSiteData.siteId;
                                    values.lighthouseId = rowData.lighthouseId;
                                    this.ajax('put', this.Constant.serviceUrls.SETTING_LIGHT_HOUSE, values, function(data){
                                        this.message(this.Constant.info.SUCCESS);
                                        webix.extend(rowData, values, true);
                                        datatable.refresh(rowData.id);
                                        $$(this._lightHouseSettingWindowId).close();
                                        this._refreshLoading();
                                    }.bind(this));
                                }.bind(this),
                            } },
                            { view: 'button', label: '取消', width: 90, on: {
                                'onItemClick': function(){
                                    $$(this._lightHouseSettingWindowId).close();
                                }.bind(this),
                            } },
                            {},
                        ],
                    },
                ],
            },
        });
        view.show();
        view.resize();
        view.getBody().setValues({
            refreshDate: rowData.refreshDate,
            phone: rowData.phone,
            onOffFlag: rowData.onOffFlag,
            bootDateDelay: rowData.bootDateDelay,
            delay: rowData.delay,
        });
    };

    Module.prototype._doExportMainSite = function(){
        window.open(this.Constant.serviceUrls.BASE_URL + this.Constant.serviceUrls.EXPORT_LIGHT_HOUSE + '?siteId=' + this._mainSiteData.siteId);
    };

    Module.prototype.destroy = function(){
        if(this._popup){
            this._popup.destructor();
            this._popup = null;
        }

        this._gauges.forEach(function(gauge){
            gauge.dispose();
        });
        this._gauges = [];

        Module._super.destroy.call(this);
    };

    return Module;
});