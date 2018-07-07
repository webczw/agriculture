package com.hjd.power.agriculture.domain;

import java.util.List;

public class SiteVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2858876602921515395L;
	private Integer siteId;// 站点ID
	private String siteCode;// 站点编码
	private String siteName;// 站点名称
	private String province;// 省
	private String city;// 市
	private String county;// 县
	private Integer linkStatus;// 连接状态，0连接，1故障，2待开通
	private Integer siteStatus;// 站点状态
	private List<LighthouseVO> lighthouseList;

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
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

	public List<LighthouseVO> getLighthouseList() {
		return lighthouseList;
	}

	public void setLighthouseList(List<LighthouseVO> lighthouseList) {
		this.lighthouseList = lighthouseList;
	}

	public Integer getLinkStatus() {
		return linkStatus;
	}

	public void setLinkStatus(Integer linkStatus) {
		this.linkStatus = linkStatus;
	}

	public Integer getSiteStatus() {
		return siteStatus;
	}

	public void setSiteStatus(Integer siteStatus) {
		this.siteStatus = siteStatus;
	}

}
