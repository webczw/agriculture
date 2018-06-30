define([], function(){
    "use strict";
    
    var model = null;
    
	function Model(){
		this.data = null;
	}

	Model.prototype.clearValues = function(){
		this.data = null;
	};

	Model.prototype.setValue = function(prop, value){
		this.data = this.data || {};
		this.data[prop] = value;
	};
	Model.prototype.getValue = function(prop){
		return this.data && this.data[prop];
	};
	Model.prototype.deleteValue = function(prop){
		this.data && delete this.data[prop];
	};

	Model.getInstance = function(){
		if(!model){
			model = new Model();
		}
		return model;
    };
    
	return Model;
});