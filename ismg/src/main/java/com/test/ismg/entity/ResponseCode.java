package com.test.ismg.entity;

public enum ResponseCode {
	SUCCESS("200"), FAILED("500");
	private String code;

	ResponseCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
