package com.test.ismg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.ismg.modular.busi.entity.TypeTemplate;
import com.test.ismg.service.SmsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.github.pagehelper.util.StringUtil;
import com.test.ismg.configuration.SystemProperties;
import com.test.ismg.entity.ResponseCode;
import com.test.ismg.entity.ResponseEntity;
import com.test.ismg.modular.busi.service.TypeTemplateService;
import com.test.ismg.util.BusinessTypeConstants;
import com.test.ismg.util.CommonUtil;

@Service
public class AliSmsService implements SmsService {

	private final Logger log = LoggerFactory.getLogger(AliSmsService.class);

	// 产品名称:云通信短信API产品,开发者无需替换
	private String product = "Dysmsapi";
	// 产品域名,开发者无需替换
	private String domain = "dysmsapi.aliyuncs.com";

	@Value("${sms.ali.accessKeyId}")
	private String accessKeyId;
	@Value("${sms.ali.accessKeySecret}")
	private String accessKeySecret;
	/** 是否开发模式 */
	@Value("${sms.isDevMode}")
	private boolean isDevMode;
	/** 客服电话 */
	@Value("${sms.customerServicePhone}")
	private String customerServicePhone;
	@Autowired
	SystemProperties systemProperties;
	@Autowired
	TypeTemplateService typeTemplateService;

	/**
	 * 向阿里发送请求
	 * 
	 * @param mobile
	 * @param templateId
	 * @param params
	 * @return
	 */
	private ResponseEntity send2ali(String mobile, String templateId, String params) {
		// 返回响应信息
		ResponseEntity resp = new ResponseEntity();

		try {
			// 可自助调整超时时间
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");

			// 初始化acsClient,暂不支持region化
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);

			// 组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();

			// 必填:待发送手机号
			request.setPhoneNumbers(mobile);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName("氢诺科技");

			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(templateId);
			request.setTemplateParam(params);

			// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
			// request.setSmsUpExtendCode("90997");
			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			// request.setOutId("yourOutId");
			log.info("the message send to ali :{}", CommonUtil.obj2json(request));
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			log.info("the message receive from ali :{}", CommonUtil.obj2json(sendSmsResponse));
			if ("OK".equals(sendSmsResponse.getCode())) {
				resp.setStatus(ResponseCode.SUCCESS.getCode());
				resp.setMsg("成功");
			} else {
				resp.setMsg(sendSmsResponse.getMessage());
				resp.setStatus(ResponseCode.FAILED.getCode());
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			resp.setStatus(ResponseCode.FAILED.getCode());
			if (e.getMessage() == null) {
				resp.setMsg("");
			} else {
				resp.setMsg(e.getMessage());
			}

		}
		return resp;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity sendMessage(String mobile, Map<String, Object> params) {
		// 返回响应信息
		ResponseEntity resp = new ResponseEntity();
		Map<String, Object> sqlParams = new HashMap<>();
		try {
			
			String businessType = (String) params.get("businessType");
			if(StringUtil.isEmpty(businessType)) {
				throw new Exception("参数businessType不能为空");
			}
				
			// 审核失败的通知
			if (BusinessTypeConstants.AUDIT_FAILED.equals(businessType)) {
				Map paramsDetail = (Map) params.get("params");
				// 参数传入客服电话就用传入的，没传入用默认配置文件的
				if (paramsDetail.get("phone") == null || paramsDetail.get("phone").toString().trim().equals("")) {
					paramsDetail.put("phone", customerServicePhone);
				}
			}

			// 获取业务参数
			String paramStr = CommonUtil.obj2json(params.get("params"));

			// 获取businesstype对应的handler
			sqlParams.put("businessType", params.get("businessType"));
			sqlParams.put("status", "1");
			if (params.get("tempVersion") == null) {
				sqlParams.put("version", "v1");
			} else {
				sqlParams.put("version", params.get("version"));
			}

			// 获取所有匹配的模板
			List<TypeTemplate> templateList = this.typeTemplateService.list(sqlParams);
			for (TypeTemplate typeTemplate : templateList) {
				String templateCode = typeTemplate.getTempCode();
				this.log.info("######## send message with template code:{}", templateCode);
				resp = this.send2ali(mobile, templateCode, paramStr);
				// 如果失败可以调用其它模板
				if (ResponseCode.FAILED.getCode().equals(resp.getStatus())) {// 失败后调用其它模板
					resp.setCost("0");
					continue;
				} else {// 调用成功
					resp.setCost(typeTemplate.getCost().toString());
					break;
				}
			}

		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			resp.setStatus(ResponseCode.FAILED.getCode());
			resp.setMsg(e.getMessage());
		}
		resp.setChannelId("4");// 阿里渠道
		return resp;
	}

	@Override
	public ResponseEntity sendCaptcha(String mobile, String captcha) {
		ResponseEntity responseEntity = null;
		if (StringUtils.isBlank(captcha)) {
			captcha = CommonUtil.generateCaptcha(6);
		}
		if (isDevMode) {// 开发模式
			responseEntity = new ResponseEntity();
			responseEntity.setStatus(ResponseCode.SUCCESS.getCode());
			responseEntity.setData(captcha);
			responseEntity.setMsg("成功");
		} else {
			responseEntity = this.send2ali(mobile, "SMS_105670057", "{\"code\":\"" + captcha + "\"}");
			responseEntity.setData(captcha);
		}
		return responseEntity;
	}

}
