package org.forbes.dal.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Table: f_trans_order
 */
@Data
@ApiModel(description="交易订单")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value="f_trans_order")
public class TransOrder extends BaseEntity {


	private static final long serialVersionUID = 3194828952197963890L;


	/**
     * 转账订单号
     * Table:     f_trans_order
     * Column:    trans_order_id
     * Nullable:  false
     */
    private String transOrderId;

    /**
     * 商户ID
     * Table:     f_trans_order
     * Column:    mch_id
     * Nullable:  false
     */
    private String mchId;

    /**
     * 商户转账单号
     * Table:     f_trans_order
     * Column:    mch_trans_no
     * Nullable:  false
     */
    private String mchTransNo;

    /**
     * 渠道ID
     * Table:     f_trans_order
     * Column:    channel_id
     * Nullable:  false
     */
    private String channelId;

    /**
     * 转账金额,单位分
     * Table:     f_trans_order
     * Column:    amount
     * Nullable:  false
     */
    private Long amount;

    /**
     * 三位货币代码,人民币:cny
     * Table:     f_trans_order
     * Column:    currency
     * Nullable:  false
     */
    private String currency;

    /**
     * 转账状态:0-订单生成,1-转账中,2-转账成功,3-转账失败,4-业务处理完成
     * Table:     f_trans_order
     * Column:    status
     * Nullable:  false
     */
    private Byte status;

    /**
     * 转账结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败
     * Table:     f_trans_order
     * Column:    result
     * Nullable:  false
     */
    private Byte result;

    /**
     * 客户端IP
     * Table:     f_trans_order
     * Column:    client_ip
     * Nullable:  true
     */
    private String clientIp;

    /**
     * 设备
     * Table:     f_trans_order
     * Column:    device
     * Nullable:  true
     */
    private String device;

    /**
     * 备注
     * Table:     f_trans_order
     * Column:    remark_info
     * Nullable:  true
     */
    private String remarkInfo;

    /**
     * 渠道用户标识,如微信openId,支付宝账号
     * Table:     f_trans_order
     * Column:    channel_user
     * Nullable:  true
     */
    private String channelUser;

    /**
     * 用户姓名
     * Table:     f_trans_order
     * Column:    user_name
     * Nullable:  true
     */
    private String userName;

    /**
     * 渠道商户ID
     * Table:     f_trans_order
     * Column:    channel_mch_id
     * Nullable:  false
     */
    private String channelMchId;

    /**
     * 渠道订单号
     * Table:     f_trans_order
     * Column:    channel_order_no
     * Nullable:  true
     */
    private String channelOrderNo;

    /**
     * 渠道错误码
     * Table:     f_trans_order
     * Column:    channel_err_code
     * Nullable:  true
     */
    private String channelErrCode;

    /**
     * 渠道错误描述
     * Table:     f_trans_order
     * Column:    channel_err_msg
     * Nullable:  true
     */
    private String channelErrMsg;

    /**
     * 特定渠道发起时额外参数
     * Table:     f_trans_order
     * Column:    extra
     * Nullable:  true
     */
    private String extra;

    /**
     * 通知地址
     * Table:     f_trans_order
     * Column:    notify_url
     * Nullable:  false
     */
    private String notifyUrl;

    /**
     * 扩展参数1
     * Table:     f_trans_order
     * Column:    param1
     * Nullable:  true
     */
    private String param1;

    /**
     * 扩展参数2
     * Table:     f_trans_order
     * Column:    param2
     * Nullable:  true
     */
    private String param2;

    /**
     * 订单失效时间
     * Table:     f_trans_order
     * Column:    expire_time
     * Nullable:  true
     */
    private Date expireTime;

    /**
     * 订单转账成功时间
     * Table:     f_trans_order
     * Column:    trans_succ_time
     * Nullable:  true
     */
    private Date transSuccTime;
}