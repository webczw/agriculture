package com.hjd.power.agriculture;

public interface Enums {
	public enum FailEnum {
		COM_HJD_POWER_00001("COM_HJD_POWER_00001", "user is null."), 
		COM_HJD_POWER_00002("COM_HJD_POWER_00002", "password error."), 
		COM_HJD_POWER_00003("COM_HJD_POWER_00003", "session is invalid,please login again!"), 
		COM_HJD_POWER_00004("COM_HJD_POWER_00004", "No permission operation."), 
		COM_HJD_POWER_00005("COM_HJD_POWER_00005", "session user is null.");

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

		FailEnum(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

	}

	public enum StatusEnum {
		FAULT(0, "故障"), NORMAL(1, "正常");
		private Integer code;
		private String name;

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		StatusEnum(Integer code, String name) {
			this.code = code;
			this.name = name;
		}

		public static String getName(Integer code) {
			for (StatusEnum ent : StatusEnum.values()) {
				if (ent.code.equals(code)) {
					return ent.name;
				}
			}
			return null;
		}
	}
	
	public enum ConfigEnum {
		MAIL_TO("agriculture.mail.to"),
		SYSTEM_NAME("agriculture.system.name"),
		TOTAL_EXCEL_HEADERS("agriculture.total.excel.headers"),
		TOTAL_EXCEL_FILENAME("agriculture.total.excel.fileName"),
		LIGHTHOUSE_EXCEL_HEADERS("agriculture.lighthouse.excel.headers"),
		SENSOR_EXCEL_HEADERS("agriculture.sensor.excel.headers");

		//
		private String code;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}


		ConfigEnum(String code) {
			this.code = code;
		}

	}
}
