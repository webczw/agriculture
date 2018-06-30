package com.hjd.power.agriculture.domain;

public class SensorVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -155354627258031311L;
	private Integer sensorId;// 主键标识
	private Integer lighthouseId;// 灯塔站点ID
	private Integer siteId;// 主站点ID
	private String number;// 编号
	private String addressCode;// 地址码
	private String photovoltaic;// 光伏电压
	private String voltage;// 电池电压
	private String humidity;// 湿度
	private String temperature;// 温度
	private Double phValue;// PH值
	private String fault;// 故障

	public Integer getLighthouseId() {
		return lighthouseId;
	}

	public void setLighthouseId(Integer lighthouseId) {
		this.lighthouseId = lighthouseId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
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

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public Double getPhValue() {
		return phValue;
	}

	public void setPhValue(Double phValue) {
		this.phValue = phValue;
	}

	public String getFault() {
		return fault;
	}

	public void setFault(String fault) {
		this.fault = fault;
	}

	public Integer getSensorId() {
		return sensorId;
	}

	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

}
