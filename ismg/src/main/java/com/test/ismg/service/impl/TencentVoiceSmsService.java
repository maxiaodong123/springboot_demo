package com.test.ismg.service.impl;

import java.util.Map;

import com.test.ismg.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.test.ismg.entity.ResponseCode;
import com.test.ismg.entity.ResponseEntity;
import com.test.ismg.util.CommonUtil;
import com.github.qcloudsms.*;

@Service
public class TencentVoiceSmsService implements SmsService {
	
	private final Logger log = LoggerFactory.getLogger(TencentVoiceSmsService.class);
	
   	@Value("${sms.tencent.accessKeyId}")
    private Integer accessKeyId;
    @Value("${sms.tencent.accessKeySecret}")
    private String accessKeySecret;

	@Override
	public ResponseEntity sendMessage(String mobile, Map<String,Object> params) {
		return null;
	}

	@Override
	public ResponseEntity sendCaptcha(String mobile,String captcha) {
		// 返回响应信息
        ResponseEntity resp = new ResponseEntity();
        
        try {
            //生成6位验证码
            if(captcha==null || captcha.trim().equals("") || captcha.trim().equals("null")) {
            		captcha = CommonUtil.generateCaptcha(6);
            }
            log.info("generate captcha:{}",captcha);
            
            SmsVoiceVerifyCodeSender smsVoiceVerifyCodeSender = new SmsVoiceVerifyCodeSender(accessKeyId,accessKeySecret);
            SmsVoiceVerifyCodeSenderResult result = smsVoiceVerifyCodeSender.send("86", mobile, captcha, 2, "");
            
       		if(result.result==0) {
	    			resp.setData(captcha);
	    			resp.setStatus(ResponseCode.SUCCESS.getCode());
	    			resp.setMsg("成功");
	    		}else {
	    			resp.setStatus(ResponseCode.FAILED.getCode());
	    			resp.setMsg("失败");
	    		}
            
        }catch(Throwable e) {
        		log.error(e.getMessage(),e);
        		resp.setStatus(ResponseCode.FAILED.getCode());
			resp.setMsg(e.getMessage());
        }
        
        return resp;
    
	}

}
