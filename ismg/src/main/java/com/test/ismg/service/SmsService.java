package com.test.ismg.service;

import java.util.Map;

import com.test.ismg.entity.ResponseEntity;

public interface SmsService {

	/**
	 * 发送短消息
	 * 
	 * @param mobile
	 * @param message
	 * @return
	 */
	ResponseEntity sendMessage(String mobile, Map<String,Object> params);

	/**
	 * 发送短信验证码
	 * 
	 * @param mobile	手机号
	 * @param captcha 验证码（可为空，为空时网关生成）
	 * @return
	 */
	ResponseEntity sendCaptcha(String mobile,String captcha);

}
