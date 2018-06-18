package com.hjd.power.agriculture.domain;

public class IighthouseVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -155354627258031311L;
	private Integer lighthouse_id;// 主键标识
	private Integer main_id;// 主站点ID
	private String number;// 编号
	private String address_code;// 地址码
	private String photovoltaic;// 光伏电压
	private String voltage;// 电池电压
	private String temperature;// 温度
	private Double ph_value;// PH值
	private String fault;// 故障

	public Integer getLighthouse_id() {
		return lighthouse_id;
	}

	public void setLighthouse_id(Integer lighthouse_id) {
		this.lighthouse_id = lighthouse_id;
	}

	public Integer getMain_id() {
		return main_id;
	}

	public void setMain_id(Integer main_id) {
		this.main_id = main_id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAddress_code() {
		return address_code;
	}

	public void setAddress_code(String address_code) {
		this.address_code = address_code;
	}

	public String getPhotovoltaic() {
		return photovoltaic;
	}

	public void setPhotovoltaic(String photovoltaic) {
		this.photovoltaic = photovoltaic;
	}

	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public Double getPh_value() {
		return ph_value;
	}

	public void setPh_value(Double ph_value) {
		this.ph_value = ph_value;
	}

	public String getFault() {
		return fault;
	}

	public void setFault(String fault) {
		this.fault = fault;
	}

}
