package com.hjd.power.agriculture.domain;

import java.util.Date;
import java.util.List;

public class LighthouseVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2620365619464442126L;
	private Integer lighthouseId;// 主键表示
	private Integer siteId;
	private Integer linkStatus;// 连接状态
	private String siteNumber;// 站点编号
	private String province;// 省
	private String city;// 市
	private String county;// 县
	private String siteCode;// 站点代号
	private String clientName;// 客户名称
	private Date dateTime;// 时间日期
	private String temperature;// 环境温度
	private String voltage;// 电池电压
	private Integer lightStatus;// 灯状态
	private String photovoltaic;// 光伏电压
	private String sensorStatus;// 传感器状态
	private Date refreshDate;// 表头数据刷新时间
	private String phone;// 关连手机
	private List<SensorVO> sensorList;

	public Integer getLighthouseId() {
		return lighthouseId;
	}

	public void setLighthouseId(Integer lighthouseId) {
		this.lighthouseId = lighthouseId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Date getRefreshDate() {
		return refreshDate;
	}

	public void setRefreshDate(Date refreshDate) {
		this.refreshDate = refreshDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getLinkStatus() {
		return linkStatus;
	}

	public void setLinkStatus(Integer linkStatus) {
		this.linkStatus = linkStatus;
	}

	public String getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(String siteNumber) {
		this.siteNumber = siteNumber;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}

	public Integer getLightStatus() {
		return lightStatus;
	}

	public void setLightStatus(Integer lightStatus) {
		this.lightStatus = lightStatus;
	}

	public String getPhotovoltaic() {
		return photovoltaic;
	}

	public void setPhotovoltaic(String photovoltaic) {
		this.photovoltaic = photovoltaic;
	}

	public String getSensorStatus() {
		return sensorStatus;
	}

	public void setSensorStatus(String sensorStatus) {
		this.sensorStatus = sensorStatus;
	}

	public List<SensorVO> getSensorList() {
		return sensorList;
	}

	public void setSensorList(List<SensorVO> sensorList) {
		this.sensorList = sensorList;
	}

}
