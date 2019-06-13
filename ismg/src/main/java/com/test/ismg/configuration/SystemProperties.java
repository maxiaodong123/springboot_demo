package com.test.ismg.configuration;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 应用参数配置
 *
 * @author liuzg
 * @date 2017-05-24 20:37
 */
@Configuration
@ConfigurationProperties(prefix = SystemProperties.CONF_PREFIX)
public class SystemProperties {

	public static final String CONF_PREFIX = "sms";
	/**业务类型与模板*/
	private List<Map<String,String>> smsTemplate;
	/**业务类型与处理类的映射*/
	private Map<String,String> serviceName4BusinessType;
	/**发送通知时，阿里服务类中业务类型和模板的映射*/
	private Map<String,String> aliBusinessType2Template;

	public Map<String, String> getAliBusinessType2Template() {
		return aliBusinessType2Template;
	}

	public void setAliBusinessType2Template(Map<String, String> aliBusinessType2Template) {
		this.aliBusinessType2Template = aliBusinessType2Template;
	}

	public List<Map<String, String>> getSmsTemplate() {
		return smsTemplate;
	}

	public void setSmsTemplate(List<Map<String, String>> smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

	public Map<String,String> getServiceName4BusinessType() {
		return serviceName4BusinessType;
	}

	public void setServiceName4BusinessType(Map<String,String> serviceName4BusinessType) {
		this.serviceName4BusinessType = serviceName4BusinessType;
	}
	
}
