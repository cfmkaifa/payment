package org.forbes.comm.enumm;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;

/***
 * PayChannelEnum概要说明：支付渠道
 * @author Huanghy
 */
public enum PayChannelEnum {

	
	PAY_CHANNEL_WX_JSAPI("WX_JSAPI","微信公众号支付","WX"),
	PAY_CHANNEL_WX_NATIVE("WX_NATIVE","微信原生扫码支付","WX"),
	PAY_CHANNEL_WX_APP("WX_APP","微信APP支付","WX"),
	PAY_CHANNEL_WX_MWEB("WX_MWEB","微信H5支付","WX"),
	PAY_CHANNEL_IAP("IAP","苹果应用内支付","IAP"),
	PAY_CHANNEL_ALIPAY_MOBILE("ALIPAY_MOBILE","支付宝移动支付","ALIPAY"),
	PAY_CHANNEL_ALIPAY_PC("ALIPAY_PC","支付宝PC支付","ALIPAY"),
	PAY_CHANNEL_ALIPAY_WAP("ALIPAY_WAP","支付宝WAP支付","ALIPAY"),
	PAY_CHANNEL_ALIPAY_QR("ALIPAY_QR","支付宝当面付之扫码支付","ALIPAY");
	
	private String code;
	private String name;
	private String groupCode;
	
	
	/***
	 * existsGroupCode方法慨述:验证分组
	 * @param code
	 * @param groupCode
	 * @return boolean
	 * @创建人 huanghy
	 * @创建时间 2019年12月13日 上午11:59:35
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	public static boolean existsGroupCode(String code,String groupCode){
		return Arrays.asList(PayChannelEnum.values()).stream()
				.filter(tEnum -> tEnum.getCode().equals(code)
						&&tEnum.getGroupCode().equalsIgnoreCase(groupCode)).count() > 0 ;
	}
	
	/***
	 * existsCode方法慨述:
	 * @param code
	 * @return boolean
	 * @创建人 huanghy
	 * @创建时间 2019年12月10日 上午10:17:17
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	public static boolean existsCode(String code){
		return Arrays.asList(PayChannelEnum.values()).stream()
		.filter(tEnum -> tEnum.getCode().equals(code)).count() > 0 ;
	}
	
	
	/***
	 * receEnums方法慨述:
	 * @return List<Map<String,String>>
	 * @创建人 huanghy
	 * @创建时间 2019年12月10日 上午10:18:04
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	public static List<Map<String,String>> receEnums(){
		return Arrays.asList(PayChannelEnum.values()).stream().map(tEnum -> {
			Map<String,String> reponseMap = Maps.newHashMap();
			reponseMap.put("code", tEnum.getCode());
			reponseMap.put("name", tEnum.getName());
			return reponseMap;
		}).collect(Collectors.toList());
	}

	/***
	 * 
	 * 构造函数:
	 * @param code
	 * @param name
	 */
	PayChannelEnum(String code,String name,String groupCode){
		this.code = code;
		this.name = name;
		this.groupCode  = groupCode;
	}

	/** 
	 * @return code 
	 */
	public String getCode() {
		return code;
	}

	/** 
	 * @return name 
	 */
	public String getName() {
		return name;
	}

	/** 
	 * @return groupCode 
	 */
	public String getGroupCode() {
		return groupCode;
	}
	
	
}
