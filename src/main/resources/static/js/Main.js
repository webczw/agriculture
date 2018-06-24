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
                    width: 1280,
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
                            template: '<div id="footer" style="text-align:center;background-color:#eee;">Copyright 湖南保的农业科技有限公司</div>',
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

    Main.prototype.getContainer = function(){
        return $$(this._containerId);
    };

	return new Main();
});