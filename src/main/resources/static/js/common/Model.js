define([], function(){
    "use strict";
    
    var model = null;
    
	function Model(){
	}

	Model.getInstance = function(){
		if(!model){
			model = new Model();
		}
		return model;
    };
    
	return Model;
});