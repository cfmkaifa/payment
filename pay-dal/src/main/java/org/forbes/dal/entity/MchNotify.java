package org.forbes.dal.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Table: f_mch_notify
 */
@Data
@ApiModel(description="商家通知")
@EqualsAndHashCode(callSuper = false)
@TableName(value="f_mch_notify")
public class MchNotify extends BaseEntity {
   
	private static final long serialVersionUID = -4312413177729360981L;

	/**
     * 订单ID
     * Table:     f_mch_notify
     * Column:    order_id
     * Nullable:  false
     */
	@ApiModelProperty(value="订单ID")
    private String orderId;

    /**
     * 商户ID
     * Table:     f_mch_notify
     * Column:    mch_id
     * Nullable:  false
     */
	@ApiModelProperty(value="商户ID")
    private String mchId;

    /**
     * 商户订单号
     * Table:     f_mch_notify
     * Column:    mch_order_no
     * Nullable:  false
     */
	@ApiModelProperty(value="商户订单号")
    private String mchOrderNo;

    /**
     * 订单类型:1-支付,2-转账,3-退款
     * Table:     f_mch_notify
     * Column:    order_type
     * Nullable:  false
     */
	@ApiModelProperty(value="订单类型")
    private String orderType;

    /**
     * 通知地址
     * Table:     f_mch_notify
     * Column:    notify_url
     * Nullable:  false
     */
	@ApiModelProperty(value="通知地址")
    private String notifyUrl;

    /**
     * 通知次数
     * Table:     f_mch_notify
     * Column:    notify_count
     * Nullable:  false
     */
	@ApiModelProperty(value="通知次数",example="0")
    private Byte notifyCount;

    /**
     * 通知响应结果
     * Table:     f_mch_notify
     * Column:    result
     * Nullable:  true
     */
	@ApiModelProperty(value="通知响应结果")
    private String result;

    /**
     * 通知状态,1-通知中,2-通知成功,3-通知失败
     * Table:     f_mch_notify
     * Column:    status
     * Nullable:  false
     */
	@ApiModelProperty(value="通知状态")
    private Byte status;

    /**
     * 最后一次通知时间
     * Table:     f_mch_notify
     * Column:    last_notify_time
     * Nullable:  true
     */
	@ApiModelProperty(value="最后一次通知时间")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastNotifyTime;
}