package com.hjd.power.agriculture.utils;

import java.io.Serializable;

import com.hjd.power.agriculture.Enums.FailEnum;

public class ResutUtils<T> implements Serializable {
	private static final long serialVersionUID = 3003123836774338320L;
	private int status;
	private String errorCode;
	private String message;
	private T data;
	public final static int successStatus = 0;
	public final static int failStatus = 1;

	public static <T> ResutUtils<T> fail(FailEnum failEnum) {
		ResutUtils<T> obj = new ResutUtils<T>();
		obj.setStatus(failStatus);
		obj.setErrorCode(failEnum.getCode());
		obj.setMessage(failEnum.getMsg());
		return obj;
	}

	public static <T> ResutUtils<T> success(T data) {
		ResutUtils<T> obj = new ResutUtils<T>();
		obj.setStatus(successStatus);
		obj.setData(data);
		return obj;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
