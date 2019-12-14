package org.forbes.dal.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Table: f_pay_order
 */
@Data
@ApiModel(description="支付订单")
@EqualsAndHashCode(callSuper = false)
@TableName(value="f_pay_order")
public class PayOrder extends BaseEntity {
   
	private static final long serialVersionUID = -7101988720112462214L;

	/**
     * 支付订单号
     * Table:     f_pay_order
     * Column:    pay_order_id
     * Nullable:  false
     */
	 @ApiModelProperty(value="支付订单号")
    private String payOrderId;

    /**
     * 商户ID
     * Table:     f_pay_order
     * Column:    mch_id
     * Nullable:  false
     */
	 @ApiModelProperty(value="商户ID")
    private String mchId;

    /**
     * 商户订单号
     * Table:     f_pay_order
     * Column:    mch_order_no
     * Nullable:  false
     */
	 @ApiModelProperty(value="商户订单号")
    private String mchOrderNo;

    /**
     * 渠道ID
     * Table:     f_pay_order
     * Column:    channel_id
     * Nullable:  false
     */
	 @ApiModelProperty(value="渠道ID")
    private String channelId;

    /**
     * 支付金额,单位分
     * Table:     f_pay_order
     * Column:    amount
     * Nullable:  false
     */
	 @ApiModelProperty(value="支付金额,单位分",example="0.0")
    private Long amount;

    /**
     * 三位货币代码,人民币:cny
     * Table:     f_pay_order
     * Column:    currency
     * Nullable:  false
     */
	 @ApiModelProperty(value="三位货币代码,人民币:cny")
    private String currency;

    /**
     * 支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成
     * Table:     f_pay_order
     * Column:    status
     * Nullable:  false
     */
	 @ApiModelProperty(value="支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成",example="0")
    private Byte status;

    /**
     * 客户端IP
     * Table:     f_pay_order
     * Column:    client_ip
     * Nullable:  true
     */
	 @ApiModelProperty(value="客户端IP")
    private String clientIp;

    /**
     * 设备
     * Table:     f_pay_order
     * Column:    device
     * Nullable:  true
     */
	 @ApiModelProperty(value="设备")
    private String device;

    /**
     * 商品标题
     * Table:     f_pay_order
     * Column:    subject
     * Nullable:  false
     */
	 @ApiModelProperty(value="商品标题")
    private String subject;

    /**
     * 商品描述信息
     * Table:     f_pay_order
     * Column:    body
     * Nullable:  false
     */
	 @ApiModelProperty(value="商品描述信息")
    private String body;

    /**
     * 特定渠道发起时额外参数
     * Table:     f_pay_order
     * Column:    extra
     * Nullable:  true
     */
	 @ApiModelProperty(value="特定渠道发起时额外参数")
    private String extra;

    /**
     * 渠道商户ID
     * Table:     f_pay_order
     * Column:    channel_mch_id
     * Nullable:  false
     */
	 @ApiModelProperty(value="渠道商户ID")
    private String channelMchId;

    /**
     * 渠道订单号
     * Table:     f_pay_order
     * Column:    channel_order_no
     * Nullable:  true
     */
	 @ApiModelProperty(value="渠道订单号")
    private String channelOrderNo;

    /**
     * 渠道支付错误码
     * Table:     f_pay_order
     * Column:    err_code
     * Nullable:  true
     */
	 @ApiModelProperty(value="渠道支付错误码")
    private String errCode;

    /**
     * 渠道支付错误描述
     * Table:     f_pay_order
     * Column:    err_msg
     * Nullable:  true
     */
	 @ApiModelProperty(value="渠道支付错误描述")
    private String errMsg;

    /**
     * 扩展参数1
     * Table:     f_pay_order
     * Column:    param1
     * Nullable:  true
     */
	 @ApiModelProperty(value="展参数1")
    private String param1;

    /**
     * 扩展参数2
     * Table:     f_pay_order
     * Column:    param2
     * Nullable:  true
     */
	 @ApiModelProperty(value="扩展参数2")
    private String param2;

    /**
     * 通知地址
     * Table:     f_pay_order
     * Column:    notify_url
     * Nullable:  false
     */
	 @ApiModelProperty(value="通知地址")
    private String notifyUrl;

    /**
     * 通知次数
     * Table:     f_pay_order
     * Column:    notify_count
     * Nullable:  false
     */
	 @ApiModelProperty(value="通知次数",example="0")
    private Byte notifyCount;

    /**
     * 最后一次通知时间
     * Table:     f_pay_order
     * Column:    last_notify_time
     * Nullable:  true
     */
	@ApiModelProperty(value=" 最后一次通知时间",example="0")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Long lastNotifyTime;

    /**
     * 订单失效时间
     * Table:     f_pay_order
     * Column:    expire_time
     * Nullable:  true
     */
	 @ApiModelProperty(value="订单失效时间",example="0")
	 @JSONField(format="yyyy-MM-dd HH:mm:ss")
	 @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
     private Long expireTime;

    /**
     * 订单支付成功时间
     * Table:     f_pay_order
     * Column:    pay_succ_time
     * Nullable:  true
     */
	 @ApiModelProperty(value="订单支付成功时间",example="0")
	 @JSONField(format="yyyy-MM-dd HH:mm:ss")
	 @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Long paySuccTime;
}