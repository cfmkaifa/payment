package org.forbes.dal.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Table: f_pay_channel
 */
@Data
@ApiModel(description="支付渠道")
@EqualsAndHashCode(callSuper = false)
@TableName(value="f_pay_channel")
public class PayChannel extends BaseEntity {
    
	
	private static final long serialVersionUID = 3457784957375241607L;

    /**
     * 渠道ID
     * Table:     f_pay_channel
     * Column:    channel_id
     * Nullable:  false
     */
	@ApiModelProperty(value="渠道ID",required=true)
	@NotEmpty(message="渠道ID为空")
    private String channelId;

    /**
     * 渠道名称,如:alipay,wechat
     * Table:     f_pay_channel
     * Column:    channel_name
     * Nullable:  false
     */
	@ApiModelProperty(value="渠道名称",required=true)
	@NotEmpty(message="渠道名称为空")
    private String channelName;

    /**
     * 渠道商户ID
     * Table:     f_pay_channel
     * Column:    channel_mch_id
     * Nullable:  false
     */
	@ApiModelProperty(value="渠道商户ID",required=true)
	@NotEmpty(message="渠道商户ID为空")
    private String channelMchId;

    /**
     * 商户ID
     * Table:     f_pay_channel
     * Column:    mch_id
     * Nullable:  false
     */
	@ApiModelProperty(value="商户ID",required=true)
	@NotEmpty(message="商户ID为空")
    private String mchId;

    /**
     * 渠道状态,0-停止使用,1-使用中
     * Table:     f_pay_channel
     * Column:    state
     * Nullable:  false
     */
	@ApiModelProperty(value="渠道状态",required=true)
	@NotNull(message="渠道状态为空")
    private Byte state;

    /**
     * 配置参数,json字符串
     * Table:     f_pay_channel
     * Column:    param
     * Nullable:  false
     */
	@ApiModelProperty(value="配置参数,json字符串")
    private String param;

    /**
     * 备注
     * Table:     f_pay_channel
     * Column:    remark
     * Nullable:  true
     */
	@ApiModelProperty(value="备注")
    private String remark;
}