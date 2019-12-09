package org.forbes.dal.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Table: f_iap_receipt
 */
@Data
@ApiModel(description="支付凭证")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value="f_iap_receipt")
public class IapReceipt extends BaseEntity {
    
	private static final long serialVersionUID = -4394204341529957007L;

	/**
     * 支付订单号
     * Table:     f_iap_receipt
     * Column:    pay_order_id
     * Nullable:  false
     */
    private String payOrderId;

    /**
     * 商户ID
     * Table:     f_iap_receipt
     * Column:    mch_id
     * Nullable:  false
     */
    private String mchId;

    /**
     * IAP业务号
     * Table:     f_iap_receipt
     * Column:    transaction_id
     * Nullable:  false
     */
    private String transactionId;

    /**
     * 处理状态:0-未处理,1-处理成功,-1-处理失败
     * Table:     f_iap_receipt
     * Column:    status
     * Nullable:  false
     */
    private Byte status;

    /**
     * 处理次数
     * Table:     f_iap_receipt
     * Column:    handle_count
     * Nullable:  false
     */
    private Byte handleCount;

    /**
     * 渠道ID
     * Table:     f_iap_receipt
     * Column:    receipt_data
     * Nullable:  false
     */
    private String receiptData;
}