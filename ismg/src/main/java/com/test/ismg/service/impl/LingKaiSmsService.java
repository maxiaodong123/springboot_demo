package com.test.ismg.service.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.ismg.entity.ResponseCode;
import com.test.ismg.entity.ResponseEntity;
import com.test.ismg.modular.busi.entity.TypeHandler;
import com.test.ismg.modular.busi.service.TypeHandlerService;
import com.test.ismg.service.SmsService;
import com.test.ismg.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LingKaiSmsService implements SmsService {
	
	private final Logger log = LoggerFactory.getLogger(LingKaiSmsService.class);

    @Value("${sms.lingkai.accessKeyId}")
    private String accessKeyId;
    @Value("${sms.lingkai.accessKeySecret}")
    private String accessKeySecret;
    @Value("${sms.customerServicePhone}")
    private String customerServicePhone;
	@Autowired
	private TypeHandlerService typeHandlerService;
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public ResponseEntity sendMessage(String mobile, Map<String,Object> params) {
        ResponseEntity response = new ResponseEntity();
		//获取businesstype对应的handler
        Map<String,Object> sqlParams = new HashMap<>();
		sqlParams.put("businessType", params.get("businessType"));
		sqlParams.put("status", "1");
		if(params.get("version")==null) {
			sqlParams.put("version", "v1");
		}else {
			sqlParams.put("version", params.get("version"));
		}
		List<TypeHandler> typeHandlerList = this.typeHandlerService.list(sqlParams);
		
		for(TypeHandler handler : typeHandlerList) {
			//获取模板内容
			String message = handler.getTempDesc();
			this.log.info("######## send message with temp desc :{}",message);
			
			//替换模板中的参数
			Map<String,Object> businessParam = (Map<String,Object>)params.get("params");
			for(String key : businessParam.keySet()) {
				message = message.replace("${"+key+"}", businessParam.get(key).toString());
			}
			message = message.replace("{customerServicePhone}", customerServicePhone);
			response = this.send(mobile, message);
			
			//如果失败可以调用其它服务类
			if(ResponseCode.FAILED.getCode().equals(response.getStatus())) {
		        response.setCost("0");
		        response.setChannelId("5");
				continue;
			}else {
		        response.setCost(handler.getCost().toString());
		        response.setChannelId("5");
				break;
			}
		}
		return response;
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
	        
	    		String message = "您正在获取验证码，验证码："+captcha+"，请在120秒内输入该验证码！为了保证您的账户安全，请勿泄露！";
	
	    		resp = this.send(mobile, message);
	    		resp.setData(captcha);
	    			
        }catch(Throwable e) {
        		log.error(e.getMessage(),e);
        		resp.setStatus(ResponseCode.FAILED.getCode());
			resp.setMsg(e.getMessage());
        }
        return resp;
	}
	
	public ResponseEntity send(String mobile,String message) {
		// 返回响应信息
        ResponseEntity resp = new ResponseEntity();
        String res = "";
        try {
//        		message +="【小氢快贷】";
	    		String sendContent = URLEncoder.encode(message, "GBK");// 发送内容
	
	    		String strUrl = "https://sdk2.028lk.com/sdk2/BatchSend2.aspx";
	    		String param = "CorpID=" + accessKeyId + "&Pwd=" + accessKeySecret + "&Mobile=" + mobile
	    				+ "&Content=" + sendContent + "&Cell=&SendTime=";
	    		log.info("send notice with lingka request content:{}",message);
	    		log.info("send notice with lingka request param:{}",param);
	    		res = CommonUtil.sendPost(strUrl, param);
	    		log.info("send notice with lingka response message:{}",res);
    			if(res!=null && Integer.parseInt(res)>=0) {
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
