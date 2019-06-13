package com.test.ismg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.test.ismg.modular.busi.entity.TypeHandler;
import com.test.ismg.modular.busi.service.TypeHandlerService;
import com.test.ismg.service.SmsService;
import com.test.ismg.service.impl.AliSmsService;
import com.test.ismg.service.impl.TencentVoiceSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.ismg.ApplicationContextUtil;
import com.test.ismg.configuration.SystemProperties;
import com.test.ismg.entity.ResponseCode;
import com.test.ismg.entity.ResponseEntity;
import com.test.ismg.service.impl.LingKaiSmsService;
import com.test.ismg.service.impl.TencentSmsService;
import com.test.ismg.util.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sms")
@Api(description = "短信网关接口API")
@RefreshScope
public class SmsController {
	
	private final Logger log = LoggerFactory.getLogger(SmsController.class);
	
	/** 验证码默认使用渠道	 */
	@Value("${sms.default.captchaChannel}")
	private String captchaChannel;
	
	/** 通知默认使用渠道 */
	@Value("${sms.default.messageChannel}")
	private String messageChannel;
	
	/** 短信可用渠道 */
	@Value("${sms.captchaChannellist}")
	private String captchaChannellist;
	private String[] captchaChannelArray = null;
	
	@Autowired
	private SystemProperties systemProperties;
	@Autowired
	private TypeHandlerService typeHandlerService;
	
	@PostConstruct
	private void initConfig() {
		captchaChannelArray = captchaChannellist.split(",");
	}
	
	/**
	 * 短信验证码渠道修改
	 * @param channel	渠道；ali:阿里,ten:腾讯,tenVoice:腾读语音,lk:凌凯
	 * @return
	 */
	@ApiOperation(value = "短信验证码渠道修改")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "form", name = "channel", value = "渠道；ali:阿里,ten:腾讯,tenVoice:腾读语音,lk:凌凯", required = true, dataType = "String"),
		
	})
	@RequestMapping(value = "/changeCaptchaChannel", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public synchronized Object changeCaptchaChannel(String channel) {
		this.log.info("invoke changeCaptchaChannel changeChannel:{}",channel);
		this.captchaChannel = channel;
		return this.captchaChannel;
	}
	
	/**
	 * 获取短信模板列表
	 * @return
	 */
	@ApiOperation(value = "获取短信模板列表")
	@RequestMapping(value = "/getTempList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object getTempList() {
		this.log.info("invoke getTempList");
		Map<String, Object> res = new HashMap<>();
		try {
			res.put("results", this.systemProperties.getSmsTemplate());
			res.put("status", 0);
			res.put("msg", "操作成功");
		} catch (Throwable e) {
			this.log.error(e.getMessage(), e);
			res.put("status", 1);
			res.put("msg", e.toString());
		}
		return res;
	}
	
	/**
	 * 短信信息渠道修改
	 * @param channel	渠道；ali:阿里,ten:腾讯,tenVoice:腾读语音,lk:凌凯
	 * @return
	 */
	@ApiOperation(value = "短信信息渠道修改")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "form", name = "channel", value = "渠道；ali:阿里,ten:腾讯,tenVoice:腾读语音,lk:凌凯", required = true, dataType = "String"),
		
	})
	@RequestMapping(value = "/changeMessageChannel", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object changeMessageChannel(String channel) {
		this.log.info("invoke changeMessageChannel channel:{}",channel);
		this.messageChannel = channel;
		return this.messageChannel;
	}
	
	/**
	 * 发送信息
	 * @param mobile 手机号
	 * @param businessType 业务类型
	 * @param message 信息内容
	 * @return
	 */
	@ApiOperation(value = "发送短消息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "requestBody", value = "请求参数", required = true, dataType = "String"),
		
	})
	@RequestMapping(value = "/sendMessage", method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity sendMessage(@RequestBody String requestBody) {
		this.log.info("invoke sendMessage params >> requestBody:{}",	requestBody);
		SmsService smsService = null;
		String mobile = null;
		Map<String,Object> params = null;
		Map<String,Object> sqlParams = new HashMap<>();
		ResponseEntity response = null;
		try {
			params = CommonUtil.json2map(requestBody);
			mobile = params.get("mobile").toString();
			params.remove("mobile");
			
			//获取businesstype对应的handler
			sqlParams.put("businessType", params.get("businessType"));
			sqlParams.put("status", "1");
			if(params.get("version")==null) {
				sqlParams.put("version", "v1");
			}else {
				sqlParams.put("version", params.get("version"));
			}
			List<TypeHandler> typeHandlerList = this.typeHandlerService.list(sqlParams);//主要是获取所有的服务名
			
			for(TypeHandler handler : typeHandlerList) {
				//根据业务类型获取对应的服务类名称
				String serviceName  = handler.getServiceName();
				//获取服务模板的版本
				if(handler.getTempVersion()==null) {
					params.put("tempVersion", "v1");
				}else {
					params.put("tempVersion", handler.getTempVersion());
				}
				this.log.info("######## send message with service name:{}",serviceName);
				//根据服务类名称获取实例
				if(serviceName==null) {
					ResponseEntity resp = new ResponseEntity();
					resp.setStatus(ResponseCode.FAILED.getCode());
					resp.setMsg("渠道类型错误");
				}else {
					smsService = (SmsService)ApplicationContextUtil.getApplicationContext().getBean(serviceName);
				}
				response = smsService.sendMessage(mobile,params);
				
				//如果失败可以调用其它服务类
				if(ResponseCode.FAILED.getCode().equals(response.getStatus())) {
					continue;
				}else {
					break;
				}
			}
			
		} catch (Throwable e) {
			this.log.error(e.getMessage(),e);
			ResponseEntity resp = new ResponseEntity();
			resp.setStatus(ResponseCode.FAILED.getCode());
			resp.setMsg(e.getMessage());
		}
		
		return response;
	}

	/**
	 * 发送短信验证码
	 * @param 手机号
	 * @param captcha 验证码（可为空，为空时网关生成验证码）
	 * @param useVoice 是否使用语音短信
	 * @return
	 */
	@ApiOperation(value = "发送验证码")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "form", name = "mobile", value = "手机号", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "form", name = "captcha", value = "验证码（可为空，为空时网关生成验证码）", required = false, dataType = "String"),
		@ApiImplicitParam(paramType = "form", name = "useVoice", value = "是否使用语音短信", required = true, dataType = "boolean")
		
	})
	@RequestMapping(value = "/sendCaptcha", method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity sendCaptcha(String mobile,String captcha,boolean useVoice) {
		this.log.info("invoke sendCaptcha request param >> captchaChannel:{},mobile:{},captcha:{},useVoice:{}",
				captchaChannel,mobile,captcha,useVoice);
		SmsService smsService = null;
		ResponseEntity res = null;
		String channelId = "4";
		String cost = "4.5";
		if(useVoice) {//腾讯云语音短信
			smsService = ApplicationContextUtil.getApplicationContext().getBean(TencentVoiceSmsService.class);
		}else {
			for(String channel : this.captchaChannelArray) {
				if("ali".equals(channel)) {//阿里云
					smsService = ApplicationContextUtil.getApplicationContext().getBean(AliSmsService.class);
					channelId = "4";
					cost = "4.5";
				}else if("ten".equals(channel)) {//腾讯云
					smsService = ApplicationContextUtil.getApplicationContext().getBean(TencentSmsService.class);
					channelId = "12";
					 cost = "4.5";
				}else if("lk".equals(channel)) {//凌凯
					smsService = ApplicationContextUtil.getApplicationContext().getBean(LingKaiSmsService.class);
					channelId = "5";
					 cost = "5";
				}else {
					ResponseEntity resp = new ResponseEntity();
					resp.setStatus(ResponseCode.FAILED.getCode());
					resp.setMsg("渠道类型错误");
				}
				res = smsService.sendCaptcha(mobile,captcha);
				res.setChannelId(channelId);
				res.setCost(cost);
				log.info("invoke sendCaptcha value >> channel:{},status:{},data:{},msg:{}",channel,res.getStatus(),res.getData(),res.getMsg());
				if(ResponseCode.FAILED.getCode().equals(res.getStatus())) {
					continue;
				}else {
					break;
				}
			}
		}
		
		log.info("invoke sendCaptcha response value >> status:{},data:{},msg:{}",res.getStatus(),res.getData(),res.getMsg());
		return res;
	}

}
