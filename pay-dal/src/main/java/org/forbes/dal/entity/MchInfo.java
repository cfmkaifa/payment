package org.forbes.dal.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Table: f_mch_info
 */
@Data
@ApiModel(description="商家信息")
@EqualsAndHashCode(callSuper = false)
@TableName(value="f_mch_info")
public class MchInfo extends BaseEntity {

	private static final long serialVersionUID = 8005076290994128477L;

	/**
     * 商户ID
     * Table:     f_mch_info
     * Column:    mch_id
     * Nullable:  false
     */
	@ApiModelProperty(value="商家ID",required = true)
	@NotEmpty(message="商家ID为空")
    private String mchId;

    /**
     * 名称
     * Table:     f_mch_info
     * Column:    name
     * Nullable:  false
     */
	@ApiModelProperty(value="商家名称",required = true)
	@NotEmpty(message="商家名称为空")
    private String name;

    /**
     * 类型
     * Table:     f_mch_info
     * Column:    type
     * Nullable:  false
     */
	@ApiModelProperty(value="商户类型",required = true)
	@NotEmpty(message="商户类型为空")
    private String type;

    /**
     * 请求私钥
     * Table:     f_mch_info
     * Column:    req_key
     * Nullable:  false
     */
	@ApiModelProperty(value="请求私钥",required = true)
	@NotEmpty(message="请求私钥为空")
    private String reqKey;

    /**
     * 响应私钥
     * Table:     f_mch_info
     * Column:    res_key
     * Nullable:  false
     */
	@ApiModelProperty(value="响应私钥",required = true)
	@NotEmpty(message="响应私钥为空")
    private String resKey;

    /**
     * 商户状态,0-停止使用,1-使用中
     * Table:     f_mch_info
     * Column:    state
     * Nullable:  false
     */
	@ApiModelProperty(value="商户状态",required = true,example="0")
	@NotNull(message="商户状态为空")
    private Byte state;
}