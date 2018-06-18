package com.hjd.power.agriculture.domain;

import java.io.Serializable;
import java.util.Date;

public class BaseVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6570272161654965617L;
	private Date createDate;
	private Date lastUpdateDate;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

}
