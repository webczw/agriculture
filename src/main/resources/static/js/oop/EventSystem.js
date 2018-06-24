define(['webix','jquery'], function(){
    "use strict";
    
    var eventBus = $({});
    
	function EventSystem(){
        this._evts = {};
	}

	EventSystem.prototype.on = function(evt, handler){
        eventBus.on(evt, handler);
        this._evts[evt] = this._evts[evt] || [];
        this._evts[evt].push({
            handler: handler,
        });
    };

    EventSystem.prototype.trigger = function(evt){
        var args = Array.prototype.slice.call(arguments);
        args = args.splice(1);
		eventBus.trigger(evt, args);
    };

    EventSystem.prototype.off = function(){
        var evts = this._evts;
        for(var evt in evts){
            if(evts.hasOwnerPrototy(evt)){
                evts[evt].forEach(function(obj){
                    eventBus.off(evt, obj.handler);
                });
		        evts[evt] = [];
            }
        }
        this._evts = {};
    };
    
	return EventSystem;
});