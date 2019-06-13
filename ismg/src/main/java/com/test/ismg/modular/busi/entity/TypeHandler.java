package com.test.ismg.modular.busi.entity;
import java.io.Serializable;
import java.math.BigDecimal;
public class TypeHandler implements Serializable {
		private static final long serialVersionUID = 1L;
    /** id */
    private Long id;
    /** 类型 */
    private String businessType;
    /** 类型描述 */
    private String businessTypeDesc;
    /** 服务名 */
    private String serviceName;
    /** 模板内容 */
    private String tempDesc;
    /** 版本，默认v1 */
    private String version;
    /** 使用短信模板的版本，默认v1 */
    private String tempVersion;
    /** 1使用，0废弃 */
    private Integer status;
    /**花费（单位：分）*/
    private BigDecimal cost;
    
    public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id=id;
    }
    public String getBusinessType() {
        return this.businessType;
    }
    public void setBusinessType(String businessType) {
        this.businessType=businessType;
    }
    public String getBusinessTypeDesc() {
        return this.businessTypeDesc;
    }
    public void setBusinessTypeDesc(String businessTypeDesc) {
        this.businessTypeDesc=businessTypeDesc;
    }
    public String getServiceName() {
        return this.serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName=serviceName;
    }
    public String getTempDesc() {
        return this.tempDesc;
    }
    public void setTempDesc(String tempDesc) {
        this.tempDesc=tempDesc;
    }
    public String getVersion() {
        return this.version;
    }
    public void setVersion(String version) {
        this.version=version;
    }
    public String getTempVersion() {
        return this.tempVersion;
    }
    public void setTempVersion(String tempVersion) {
        this.tempVersion=tempVersion;
    }
    public Integer getStatus() {
        return this.status;
    }
    public void setStatus(Integer status) {
        this.status=status;
    }
}

