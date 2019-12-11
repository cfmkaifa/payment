package org.forbes.dal.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Table: f_pay_order
 */
@Data
@ApiModel(description="支付订单")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value="f_pay_order")
public class PayOrder extends BaseEntity {
   
	private static final long serialVersionUID = -7101988720112462214L;

	/**
     * 支付订单号
     * Table:     f_pay_order
     * Column:    pay_order_id
     * Nullable:  false
     */
    private String payOrderId;

    /**
     * 商户ID
     * Table:     f_pay_order
     * Column:    mch_id
     * Nullable:  false
     */
    private String mchId;

    /**
     * 商户订单号
     * Table:     f_pay_order
     * Column:    mch_order_no
     * Nullable:  false
     */
    private String mchOrderNo;

    /**
     * 渠道ID
     * Table:     f_pay_order
     * Column:    channel_id
     * Nullable:  false
     */
    private String channelId;

    /**
     * 支付金额,单位分
     * Table:     f_pay_order
     * Column:    amount
     * Nullable:  false
     */
    private Long amount;

    /**
     * 三位货币代码,人民币:cny
     * Table:     f_pay_order
     * Column:    currency
     * Nullable:  false
     */
    private String currency;

    /**
     * 支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成
     * Table:     f_pay_order
     * Column:    status
     * Nullable:  false
     */
    private Byte status;

    /**
     * 客户端IP
     * Table:     f_pay_order
     * Column:    client_ip
     * Nullable:  true
     */
    private String clientIp;

    /**
     * 设备
     * Table:     f_pay_order
     * Column:    device
     * Nullable:  true
     */
    private String device;

    /**
     * 商品标题
     * Table:     f_pay_order
     * Column:    subject
     * Nullable:  false
     */
    private String subject;

    /**
     * 商品描述信息
     * Table:     f_pay_order
     * Column:    body
     * Nullable:  false
     */
    private String body;

    /**
     * 特定渠道发起时额外参数
     * Table:     f_pay_order
     * Column:    extra
     * Nullable:  true
     */
    private String extra;

    /**
     * 渠道商户ID
     * Table:     f_pay_order
     * Column:    channel_mch_id
     * Nullable:  false
     */
    private String channelMchId;

    /**
     * 渠道订单号
     * Table:     f_pay_order
     * Column:    channel_order_no
     * Nullable:  true
     */
    private String channelOrderNo;

    /**
     * 渠道支付错误码
     * Table:     f_pay_order
     * Column:    err_code
     * Nullable:  true
     */
    private String errCode;

    /**
     * 渠道支付错误描述
     * Table:     f_pay_order
     * Column:    err_msg
     * Nullable:  true
     */
    private String errMsg;

    /**
     * 扩展参数1
     * Table:     f_pay_order
     * Column:    param1
     * Nullable:  true
     */
    private String param1;

    /**
     * 扩展参数2
     * Table:     f_pay_order
     * Column:    param2
     * Nullable:  true
     */
    private String param2;

    /**
     * 通知地址
     * Table:     f_pay_order
     * Column:    notify_url
     * Nullable:  false
     */
    private String notifyUrl;

    /**
     * 通知次数
     * Table:     f_pay_order
     * Column:    notify_count
     * Nullable:  false
     */
    private Byte notifyCount;

    /**
     * 最后一次通知时间
     * Table:     f_pay_order
     * Column:    last_notify_time
     * Nullable:  true
     */
    private Long lastNotifyTime;

    /**
     * 订单失效时间
     * Table:     f_pay_order
     * Column:    expire_time
     * Nullable:  true
     */
    private Long expireTime;

    /**
     * 订单支付成功时间
     * Table:     f_pay_order
     * Column:    pay_succ_time
     * Nullable:  true
     */
    private Long paySuccTime;
}