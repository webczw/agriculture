package com.hjd.power.agriculture.domain;

public class ConfigVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1379275614534473476L;
	private Integer configId;// 配置主键标识
	private String configCode;// 配置项编码
	private String configValue;// 配置项值
	private String description;// 描述

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
