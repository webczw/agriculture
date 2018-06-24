package com.hjd.power.agriculture;

public interface Enums {
	public enum FailEnum {
		COM_HJD_POWER_00001("COM_HJD_POWER_00001","user is null."),
		COM_HJD_POWER_00002("COM_HJD_POWER_00002","password error."),
		COM_HJD_POWER_00003("COM_HJD_POWER_00003","session is invalid,please login again!"),
		COM_HJD_POWER_00004("COM_HJD_POWER_00004","No permission operation."),
		COM_HJD_POWER_00005("COM_HJD_POWER_00005","session user is null.");
		
		//
		private String code;
		private String msg;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		FailEnum(String code,String msg){
			this.code = code;
			this.msg = msg;
		}
		
	}
}
