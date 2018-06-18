package com.hjd.power.agriculture.domain;

public class TotalVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4965032666429135351L;
	private Integer totalId;// 主键标识
	private Integer totalSite;// 总站点
	private Integer linkSite;// 连接站点
	private Integer faultSite;// 故障站点
	private Integer waitPpen;// 待开通站点
	private Double distributedRate;// 全国分布率
	private Integer linkStatus;// 连接状态

	public Integer getTotalId() {
		return totalId;
	}

	public void setTotalId(Integer totalId) {
		this.totalId = totalId;
	}

	public Integer getTotalSite() {
		return totalSite;
	}

	public void setTotalSite(Integer totalSite) {
		this.totalSite = totalSite;
	}

	public Integer getLinkSite() {
		return linkSite;
	}

	public void setLinkSite(Integer linkSite) {
		this.linkSite = linkSite;
	}

	public Integer getFaultSite() {
		return faultSite;
	}

	public void setFaultSite(Integer faultSite) {
		this.faultSite = faultSite;
	}

	public Integer getWaitPpen() {
		return waitPpen;
	}

	public void setWaitPpen(Integer waitPpen) {
		this.waitPpen = waitPpen;
	}

	public Double getDistributedRate() {
		return distributedRate;
	}

	public void setDistributedRate(Double distributedRate) {
		this.distributedRate = distributedRate;
	}

	public Integer getLinkStatus() {
		return linkStatus;
	}

	public void setLinkStatus(Integer linkStatus) {
		this.linkStatus = linkStatus;
	}

}
