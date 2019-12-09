package org.forbes.dal.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Table: f_mch_notify
 */
@Data
@ApiModel(description="商家通知")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value="f_mch_notify")
public class MchNotify extends BaseEntity {
   
	private static final long serialVersionUID = -4312413177729360981L;

	/**
     * 订单ID
     * Table:     f_mch_notify
     * Column:    order_id
     * Nullable:  false
     */
    private String orderId;

    /**
     * 商户ID
     * Table:     f_mch_notify
     * Column:    mch_id
     * Nullable:  false
     */
    private String mchId;

    /**
     * 商户订单号
     * Table:     f_mch_notify
     * Column:    mch_order_no
     * Nullable:  false
     */
    private String mchOrderNo;

    /**
     * 订单类型:1-支付,2-转账,3-退款
     * Table:     f_mch_notify
     * Column:    order_type
     * Nullable:  false
     */
    private String orderType;

    /**
     * 通知地址
     * Table:     f_mch_notify
     * Column:    notify_url
     * Nullable:  false
     */
    private String notifyUrl;

    /**
     * 通知次数
     * Table:     f_mch_notify
     * Column:    notify_count
     * Nullable:  false
     */
    private Byte notifyCount;

    /**
     * 通知响应结果
     * Table:     f_mch_notify
     * Column:    result
     * Nullable:  true
     */
    private String result;

    /**
     * 通知状态,1-通知中,2-通知成功,3-通知失败
     * Table:     f_mch_notify
     * Column:    status
     * Nullable:  false
     */
    private Byte status;

    /**
     * 最后一次通知时间
     * Table:     f_mch_notify
     * Column:    last_notify_time
     * Nullable:  true
     */
    private Date lastNotifyTime;
}