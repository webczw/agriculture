package com.hjd.power.agriculture.domain;

import java.io.Serializable;
import java.util.List;

public class ListVO<T> implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private List<T> list;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
