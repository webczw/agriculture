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
	private Double photovoltaic;// 光伏电压
	private Double voltage;// 电池电压
	private Double temperature;// 温度
	private Double humidity;// 湿度
	private Double phValue;// PH值
	private String fault;// 故障
	private Integer linkStatus;// 连接状态

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

	public Double getPhotovoltaic() {
		return photovoltaic;
	}

	public void setPhotovoltaic(Double photovoltaic) {
		this.photovoltaic = photovoltaic;
	}

	public Double getVoltage() {
		return voltage;
	}

	public void setVoltage(Double voltage) {
		this.voltage = voltage;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
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

	public Integer getLinkStatus() {
		return linkStatus;
	}

	public void setLinkStatus(Integer linkStatus) {
		this.linkStatus = linkStatus;
	}

}
