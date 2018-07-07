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
	private String mainSiteCode;
	private String siteName;
	private String province;// 省
	private String city;// 市
	private String county;// 县
	private String siteCode;// 站点代号
	private String clientName;// 客户名称
	private Date dateTime;// 时间日期
	private Double temperature;// 环境温度
	private Double voltage;// 电池电压
	private Integer lightStatus;// 灯状态
	private Double photovoltaic;// 光伏电压
	private Integer sensorStatus;// 传感器状态
	private Integer refreshDate;// 灯塔数据上传周期
	private String phone;// 关连手机
	private String fanFlag;// 风机开关 ON/OFF
	private String lightFlag;// 灯开开关 ON/OFF
	private Integer bootDateDelay;// 开关时间延时设置 （4-10 小时）
	private Date deliveryDate;// 下发日期时间
	private Integer onOffFlag;// 开关设置1开,2关,3他控
	private Integer delay;// 他控延时设置：1-24 小时

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

	public Integer getRefreshDate() {
		return refreshDate;
	}

	public void setRefreshDate(Integer refreshDate) {
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

	public String getMainSiteCode() {
		return mainSiteCode;
	}

	public void setMainSiteCode(String mainSiteCode) {
		this.mainSiteCode = mainSiteCode;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
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

	public Integer getLightStatus() {
		return lightStatus;
	}

	public void setLightStatus(Integer lightStatus) {
		this.lightStatus = lightStatus;
	}

	public String getFanFlag() {
		return fanFlag;
	}

	public void setFanFlag(String fanFlag) {
		this.fanFlag = fanFlag;
	}

	public String getLightFlag() {
		return lightFlag;
	}

	public void setLightFlag(String lightFlag) {
		this.lightFlag = lightFlag;
	}

	public Integer getBootDateDelay() {
		return bootDateDelay;
	}

	public void setBootDateDelay(Integer bootDateDelay) {
		this.bootDateDelay = bootDateDelay;
	}

	public List<SensorVO> getSensorList() {
		return sensorList;
	}

	public void setSensorList(List<SensorVO> sensorList) {
		this.sensorList = sensorList;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getVoltage() {
		return voltage;
	}

	public void setVoltage(Double voltage) {
		this.voltage = voltage;
	}

	public Double getPhotovoltaic() {
		return photovoltaic;
	}

	public void setPhotovoltaic(Double photovoltaic) {
		this.photovoltaic = photovoltaic;
	}

	public Integer getSensorStatus() {
		return sensorStatus;
	}

	public void setSensorStatus(Integer sensorStatus) {
		this.sensorStatus = sensorStatus;
	}

	public Integer getOnOffFlag() {
		return onOffFlag;
	}

	public void setOnOffFlag(Integer onOffFlag) {
		this.onOffFlag = onOffFlag;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

}
