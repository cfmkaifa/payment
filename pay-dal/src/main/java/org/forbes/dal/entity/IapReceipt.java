package org.forbes.dal.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Table: f_iap_receipt
 */
@Data
@ApiModel(description="支付凭证")
@EqualsAndHashCode(callSuper = false)
@TableName(value="f_iap_receipt")
public class IapReceipt extends BaseEntity {
    
	private static final long serialVersionUID = -4394204341529957007L;

	/**
     * 支付订单号
     * Table:     f_iap_receipt
     * Column:    pay_order_id
     * Nullable:  false
     */
	@ApiModelProperty(value="支付订单号")
    private String payOrderId;

    /**
     * 商户ID
     * Table:     f_iap_receipt
     * Column:    mch_id
     * Nullable:  false
     */
	@ApiModelProperty(value="商户ID")
    private String mchId;

    /**
     * IAP业务号
     * Table:     f_iap_receipt
     * Column:    transaction_id
     * Nullable:  false
     */
	@ApiModelProperty(value="IAP业务号")
    private String transactionId;

    /**
     * 处理状态:0-未处理,1-处理成功,-1-处理失败
     * Table:     f_iap_receipt
     * Column:    status
     * Nullable:  false
     */
	@ApiModelProperty(value="处理状态:0-未处理,1-处理成功,-1-处理失败")
    private Byte status;

    /**
     * 处理次数
     * Table:     f_iap_receipt
     * Column:    handle_count
     * Nullable:  false
     */
	@ApiModelProperty(value="处理次数")
    private Byte handleCount;

    /**
     * 渠道ID
     * Table:     f_iap_receipt
     * Column:    receipt_data
     * Nullable:  false
     */
	@ApiModelProperty(value="渠道ID")
    private String receiptData;
}