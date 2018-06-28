package com.hjd.power.agriculture.domain;

import java.io.Serializable;

public class LighthouseQueryVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5045813891905357911L;
	private Integer lighthouseId;// 主键表示
	private Integer siteId;

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

}
