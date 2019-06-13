package com.test.ismg.modular.busi.entity;
import java.io.Serializable;
import java.math.BigDecimal;
public class TypeTemplate implements Serializable {
		private static final long serialVersionUID = 1L;
    /**  */
    private Long id;
    /** 类型 */
    private String businessType;
    /** 模板编码 */
    private String tempCode;
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
    public String getTempCode() {
        return this.tempCode;
    }
    public void setTempCode(String tempCode) {
        this.tempCode=tempCode;
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

