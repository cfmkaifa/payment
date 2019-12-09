package org.forbes.dal.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Table: f_mch_info
 */
@Data
@ApiModel(description="商家信息")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value="f_mch_info")
public class MchInfo extends BaseEntity {

	private static final long serialVersionUID = 8005076290994128477L;

	/**
     * 商户ID
     * Table:     f_mch_info
     * Column:    mch_id
     * Nullable:  false
     */
    private String mchId;

    /**
     * 名称
     * Table:     f_mch_info
     * Column:    name
     * Nullable:  false
     */
    private String name;

    /**
     * 类型
     * Table:     f_mch_info
     * Column:    type
     * Nullable:  false
     */
    private String type;

    /**
     * 请求私钥
     * Table:     f_mch_info
     * Column:    req_key
     * Nullable:  false
     */
    private String reqKey;

    /**
     * 响应私钥
     * Table:     f_mch_info
     * Column:    res_key
     * Nullable:  false
     */
    private String resKey;

    /**
     * 商户状态,0-停止使用,1-使用中
     * Table:     f_mch_info
     * Column:    state
     * Nullable:  false
     */
    private Byte state;
}