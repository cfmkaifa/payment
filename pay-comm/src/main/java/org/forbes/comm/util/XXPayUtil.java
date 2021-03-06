package org.forbes.comm.util;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.forbes.comm.constant.PayConstant;
import org.forbes.comm.enumm.PayEnum;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
/***
 * XXPayUtil概要说明：支付工具类
 * @author Huanghy
 */
@Slf4j
public class XXPayUtil {


	/***
	 * makeRetMap方法慨述:
	 * @param retCode
	 * @param retMsg
	 * @param resCode
	 * @param errCode
	 * @param errCodeDesc
	 * @return Map<String,Object>
	 * @创建人 huanghy
	 * @创建时间 2019年12月13日 上午9:07:57
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
    public static Map<String, Object> makeRetMap(String retCode, String retMsg, String resCode, String errCode, String errCodeDesc) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        if(retCode != null) retMap.put(PayConstant.RETURN_PARAM_RETCODE, retCode);
        if(retMsg != null) retMap.put(PayConstant.RETURN_PARAM_RETMSG, retMsg);
        if(resCode != null) retMap.put(PayConstant.RESULT_PARAM_RESCODE, resCode);
        if(errCode != null) retMap.put(PayConstant.RESULT_PARAM_ERRCODE, errCode);
        if(errCodeDesc != null) retMap.put(PayConstant.RESULT_PARAM_ERRCODEDES, errCodeDesc);
        return retMap;
    }

    /***
     * makeRetMap方法慨述:
     * @param retCode
     * @param retMsg
     * @param resCode
     * @param payEnum
     * @return Map<String,Object>
     * @创建人 huanghy
     * @创建时间 2019年12月13日 上午9:07:51
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    public static Map<String, Object> makeRetMap(String retCode, String retMsg, String resCode, PayEnum payEnum) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        if(retCode != null) retMap.put(PayConstant.RETURN_PARAM_RETCODE, retCode);
        if(retMsg != null) retMap.put(PayConstant.RETURN_PARAM_RETMSG, retMsg);
        if(resCode != null) retMap.put(PayConstant.RESULT_PARAM_RESCODE, resCode);
        if(payEnum != null) {
            retMap.put(PayConstant.RESULT_PARAM_ERRCODE, payEnum.getCode());
            retMap.put(PayConstant.RESULT_PARAM_ERRCODEDES, payEnum.getMessage());
        }
        return retMap;
    }

    
    /***
     * makeRetData方法慨述:
     * @param retMap
     * @param resKey
     * @return String
     * @创建人 huanghy
     * @创建时间 2019年12月13日 上午9:07:44
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    public static String makeRetData(Map<String,Object> retMap, String resKey) {
        if(retMap.get(PayConstant.RETURN_PARAM_RETCODE).equals(PayConstant.RETURN_VALUE_SUCCESS)) {
            String sign = PayDigestUtil.getSign(retMap, resKey, "payParams");
            retMap.put(PayConstant.RESULT_PARAM_SIGN, sign);
        }
        log.info("生成响应数据:{}", retMap);
        return JSON.toJSONString(retMap);
    }

    /***
     * makeRetFail方法慨述:
     * @param retMap
     * @return String
     * @创建人 huanghy
     * @创建时间 2019年12月13日 上午9:06:50
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    public static String makeRetFail(Map<String,Object> retMap) {
    	log.info("生成响应数据:{}", retMap);
        return JSON.toJSONString(retMap);
    }

    /**
     * 验证支付中心签名
     * @param params
     * @return
     */
    public static boolean verifyPaySign(Map<String,Object> params, String key) {
        String sign = (String)params.get("sign"); // 签名
        params.remove("sign");	// 不参与签名
        String checkSign = PayDigestUtil.getSign(params, key);
        if (!checkSign.equalsIgnoreCase(sign)) {
            return false;
        }
        return true;
    }

    /**
     * 验证VV平台支付中心签名
     * @param params
     * @return
     */
    public static boolean verifyPaySign(Map<String,Object> params, String key, String... noSigns) {
        String sign = (String)params.get("sign"); // 签名
        params.remove("sign");	// 不参与签名
        if(noSigns != null && noSigns.length > 0) {
            for (String noSign : noSigns) {
                params.remove(noSign);
            }
        }
        String checkSign = PayDigestUtil.getSign(params, key);
        if (!checkSign.equalsIgnoreCase(sign)) {
            return false;
        }
        return true;
    }

    public static String genUrlParams(Map<String, Object> paraMap) {
        if(paraMap == null || paraMap.isEmpty()) return "";
        StringBuffer urlParam = new StringBuffer();
        Set<String> keySet = paraMap.keySet();
        int i = 0;
        for(String key:keySet) {
            urlParam.append(key).append("=").append(paraMap.get(key));
            if(++i == keySet.size()) break;
            urlParam.append("&");
        }
        return urlParam.toString();
    }

    /**
     * 发起HTTP/HTTPS请求(method=POST)
     * @param url
     * @return
     */
    public static String call4Post(String url) {
        try {
            URL url1 = new URL(url);
            if("https".equals(url1.getProtocol())) {
                return HttpClient.callHttpsPost(url);
            }else if("http".equals(url1.getProtocol())) {
                return HttpClient.callHttpPost(url);
            }else {
                return "";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }

}
