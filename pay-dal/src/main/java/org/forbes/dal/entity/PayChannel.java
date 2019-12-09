package org.forbes.dal.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Table: f_pay_channel
 */
@Data
@ApiModel(description="支付渠道")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value="f_pay_channel")
public class PayChannel extends BaseEntity {
    
	
	private static final long serialVersionUID = 3457784957375241607L;

    /**
     * 渠道ID
     * Table:     f_pay_channel
     * Column:    channel_id
     * Nullable:  false
     */
    private String channelId;

    /**
     * 渠道名称,如:alipay,wechat
     * Table:     f_pay_channel
     * Column:    channel_name
     * Nullable:  false
     */
    private String channelName;

    /**
     * 渠道商户ID
     * Table:     f_pay_channel
     * Column:    channel_mch_id
     * Nullable:  false
     */
    private String channelMchId;

    /**
     * 商户ID
     * Table:     f_pay_channel
     * Column:    mch_id
     * Nullable:  false
     */
    private String mchId;

    /**
     * 渠道状态,0-停止使用,1-使用中
     * Table:     f_pay_channel
     * Column:    state
     * Nullable:  false
     */
    private Byte state;

    /**
     * 配置参数,json字符串
     * Table:     f_pay_channel
     * Column:    param
     * Nullable:  false
     */
    private String param;

    /**
     * 备注
     * Table:     f_pay_channel
     * Column:    remark
     * Nullable:  true
     */
    private String remark;
}