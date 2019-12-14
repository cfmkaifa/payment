package org.forbes.comm.enumm;
/***
 * BizResultEnum概要说明：业务系统错误代码
 * @author Huanghy
 */
public enum BizResultEnum {
	/***
	 * 006-支付系统
	 * 功能暂无-表示通用异常
	 * 001-为空判断
	 */
	ENTITY_EMPTY("006001001","对象不存在",""),
	MCH_ID_EXISTS("006001001","商家ID存在","%s对应商家已经存在"),
	MCH_STATE_NO_EXISTS("006001002","商户状态不存在","暂无%s对应商户状态"),
	MCH_TYPE_NO_EXISTS("006001003","商户类型不存在","暂无%s对应商户类型"),
	CHANNEL_ID_EXISTS("006002001","渠道ID已存在","%s对应商家已有%s对应渠道"),
	CHANNEL_ID_NO_EXISTS("006002002","渠道ID不存在","系统暂为开放%s对应渠道"),
	CHANNEL_ID_GROUP_NO_EXISTS("006002003","渠道分组下暂无对应渠道","%s下暂无%s对应渠道");

	
	/**错误编码业务系统代码+功能编码+错误代码**/
	private String bizCode;
	/**错误描述****/
	private String bizMessage;
	/**带格式错误描述****/
	private String bizFormateMessage;

	/***
	 * 构造函数:
	 * @param bizCode
	 * @param bizMessage
	 * @param bizFormateMessage
	 */
	BizResultEnum(String bizCode,String bizMessage,String bizFormateMessage){
		this.bizCode = bizCode;
		this.bizMessage = bizMessage;
		this.bizFormateMessage = bizFormateMessage;
	}

	/** 
	 * @return bizCode 
	 */
	public String getBizCode() {
		return bizCode;
	}

	/** 
	 * @param bizCode 要设置的 bizCode 
	 */
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	/** 
	 * @return bizMessage 
	 */
	public String getBizMessage() {
		return bizMessage;
	}

	/** 
	 * @param bizMessage 要设置的 bizMessage 
	 */
	public void setBizMessage(String bizMessage) {
		this.bizMessage = bizMessage;
	}

	/** 
	 * @return bizFormateMessage 
	 */
	public String getBizFormateMessage() {
		return bizFormateMessage;
	}

	/** 
	 * @param bizFormateMessage 要设置的 bizFormateMessage 
	 */
	public void setBizFormateMessage(String bizFormateMessage) {
		this.bizFormateMessage = bizFormateMessage;
	}
}
