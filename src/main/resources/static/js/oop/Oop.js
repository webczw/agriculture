
define([], function(){
	"use strict";

	function Oop(){};

	Oop.extend = function(SubClass,SuperClass){
		var F = function(){};
		F.prototype = SuperClass.prototype;
		SubClass.prototype = new F();
		SubClass.prototype.constructor = SubClass;
		SubClass._super = SuperClass.prototype;
		if(SuperClass.prototype.constructor == Object.prototype.constructor){
			SuperClass.prototype.constructor = SuperClass;
		}
	};

	return Oop;
});
