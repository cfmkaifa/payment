package org.forbes.provider;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.forbes.biz.IMchInfoService;
import org.forbes.biz.IPayChannelService;
import org.forbes.biz.IPayOrderService;
import org.forbes.comm.constant.PayConstant;
import org.forbes.comm.constant.PayEnum;
import org.forbes.comm.util.MyBase64;
import org.forbes.dal.entity.MchInfo;
import org.forbes.dal.entity.PayChannel;
import org.forbes.dal.entity.PayOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
/***
 * PayChannel4WxController概要说明：支付渠道接口:微信
 * @author Huanghy
 */
@RestController
@Slf4j
public class PayChannel4WxController{

    @Autowired
    private IPayOrderService payOrderService;

    @Autowired
    private IPayChannelService payChannelService;

    @Autowired
    private IMchInfoService mchInfoService;

    @Resource
    private WxPayProperties wxPayProperties;

    /**
     * 发起微信支付(统一下单)
     * @param
     * @return
     */
    @RequestMapping(value = "/pay/channel/wx")
    public String doWxPayReq(@RequestParam String jsonParam) {
        try{
            JSONObject paramObj = JSON.parseObject(new String(MyBase64.decode(jsonParam)));
            PayOrder payOrder = paramObj.getObject("payOrder", PayOrder.class);
            String tradeType = paramObj.getString("tradeType");
            String logPrefix = "【微信支付统一下单】";
            String mchId = payOrder.getMchId();
            String channelId = payOrder.getChannelId();
            MchInfo mchInfo = mchInfoService.selectMchInfo(mchId);
            String resKey = mchInfo == null ? "" : mchInfo.getResKey();
            if("".equals(resKey)) return XXPayUtil.makeRetFail(XXPayUtil.makeRetMap(PayConstant.RETURN_VALUE_FAIL, "", PayConstant.RETURN_VALUE_FAIL, PayEnum.ERR_0001));
            PayChannel payChannel = payChannelService.selectPayChannel(channelId, mchId);
            WxPayConfig wxPayConfig = WxPayUtil.getWxPayConfig(payChannel.getParam(), tradeType, wxPayProperties.getCertRootPath(), wxPayProperties.getNotifyUrl());
            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(wxPayConfig);
            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = buildUnifiedOrderRequest(payOrder, wxPayConfig);
            String payOrderId = payOrder.getPayOrderId();
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult;
            try {
                wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
                log.info("{} >>> 下单成功", logPrefix);
                Map<String, Object> map = XXPayUtil.makeRetMap(PayConstant.RETURN_VALUE_SUCCESS, "", PayConstant.RETURN_VALUE_SUCCESS, null);
                map.put("payOrderId", payOrderId);
                map.put("prepayId", wxPayUnifiedOrderResult.getPrepayId());
                int result = payOrderService.updateStatus4Ing(payOrderId, wxPayUnifiedOrderResult.getPrepayId());
                log.info("更新第三方支付订单号:payOrderId={},prepayId={},result={}", payOrderId, wxPayUnifiedOrderResult.getPrepayId(), result);
                switch (tradeType) {
                    case PayConstant.WxConstant.TRADE_TYPE_NATIVE : {
                        map.put("codeUrl", wxPayUnifiedOrderResult.getCodeURL());   // 二维码支付链接
                        break;
                    }
                    case PayConstant.WxConstant.TRADE_TYPE_APP : {
                        Map<String, String> payInfo = new HashMap<>();
                        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                        String nonceStr = String.valueOf(System.currentTimeMillis());
                        // APP支付绑定的是微信开放平台上的账号，APPID为开放平台上绑定APP后发放的参数
                        String appId = wxPayConfig.getAppId();
                        Map<String, String> configMap = new HashMap<>();
                        // 此map用于参与调起sdk支付的二次签名,格式全小写，timestamp只能是10位,格式固定，切勿修改
                        String partnerId = wxPayConfig.getMchId();
                        configMap.put("prepayid", wxPayUnifiedOrderResult.getPrepayId());
                        configMap.put("partnerid", partnerId);
                        String packageValue = "Sign=WXPay";
                        configMap.put("package", packageValue);
                        configMap.put("timestamp", timestamp);
                        configMap.put("noncestr", nonceStr);
                        configMap.put("appid", appId);
                        // 此map用于客户端与微信服务器交互
                        payInfo.put("sign", SignUtils.createSign(configMap, wxPayConfig.getMchKey(), null));
                        payInfo.put("prepayId", wxPayUnifiedOrderResult.getPrepayId());
                        payInfo.put("partnerId", partnerId);
                        payInfo.put("appId", appId);
                        payInfo.put("packageValue", packageValue);
                        payInfo.put("timeStamp", timestamp);
                        payInfo.put("nonceStr", nonceStr);
                        map.put("payParams", payInfo);
                        break;
                    }
                    case PayConstant.WxConstant.TRADE_TYPE_JSPAI : {
                        Map<String, String> payInfo = new HashMap<>();
                        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                        String nonceStr = String.valueOf(System.currentTimeMillis());
                        payInfo.put("appId", wxPayUnifiedOrderResult.getAppid());
                        // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                        payInfo.put("timeStamp", timestamp);
                        payInfo.put("nonceStr", nonceStr);
                        payInfo.put("package", "prepay_id=" + wxPayUnifiedOrderResult.getPrepayId());
                        payInfo.put("signType", WxPayConstants.SignType.MD5);
                        payInfo.put("paySign", SignUtils.createSign(payInfo, wxPayConfig.getMchKey(), null));
                        map.put("payParams", payInfo);
                        break;
                    }
                    case PayConstant.WxConstant.TRADE_TYPE_MWEB : {
                        map.put("payUrl", wxPayUnifiedOrderResult.getMwebUrl());    // h5支付链接地址
                        break;
                    }
                }
                return XXPayUtil.makeRetData(map, resKey);
            } catch (WxPayException e) {
                log.error(e, "下单失败");
                //出现业务错误
                log.info("{}下单返回失败", logPrefix);
                log.info("err_code:{}", e.getErrCode());
                log.info("err_code_des:{}", e.getErrCodeDes());
                return XXPayUtil.makeRetData(XXPayUtil.makeRetMap(PayConstant.RETURN_VALUE_SUCCESS, "", PayConstant.RETURN_VALUE_FAIL, "0111", "调用微信支付失败," + e.getErrCode() + ":" + e.getErrCodeDes()), resKey);
            }
        }catch (Exception e) {
            log.error(e, "微信支付统一下单异常");
            return XXPayUtil.makeRetFail(XXPayUtil.makeRetMap(PayConstant.RETURN_VALUE_FAIL, "", PayConstant.RETURN_VALUE_FAIL, PayEnum.ERR_0001));
        }
    }

    /**
     * 构建微信统一下单请求数据
     * @param payOrder
     * @param wxPayConfig
     * @return
     */
    WxPayUnifiedOrderRequest buildUnifiedOrderRequest(PayOrder payOrder, WxPayConfig wxPayConfig) {
        String tradeType = wxPayConfig.getTradeType();
        String payOrderId = payOrder.getPayOrderId();
        Integer totalFee = payOrder.getAmount().intValue();// 支付金额,单位分
        String deviceInfo = payOrder.getDevice();
        String body = payOrder.getBody();
        String detail = null;
        String attach = null;
        String outTradeNo = payOrderId;
        String feeType = "CNY";
        String spBillCreateIP = payOrder.getClientIp();
        String timeStart = null;
        String timeExpire = null;
        String goodsTag = null;
        String notifyUrl = wxPayConfig.getNotifyUrl();
        String productId = null;
        if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_NATIVE)) productId = JSON.parseObject(payOrder.getExtra()).getString("productId");
        String limitPay = null;
        String openId = null;
        if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_JSPAI)) openId = JSON.parseObject(payOrder.getExtra()).getString("openId");
        String sceneInfo = null;
        if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_MWEB)) sceneInfo = JSON.parseObject(payOrder.getExtra()).getString("sceneInfo");
        // 微信统一下单请求对象
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setDeviceInfo(deviceInfo);
        request.setBody(body);
        request.setDetail(detail);
        request.setAttach(attach);
        request.setOutTradeNo(outTradeNo);
        request.setFeeType(feeType);
        request.setTotalFee(totalFee);
        request.setSpbillCreateIp(spBillCreateIP);
        request.setTimeStart(timeStart);
        request.setTimeExpire(timeExpire);
        request.setGoodsTag(goodsTag);
        request.setNotifyURL(notifyUrl);
        request.setTradeType(tradeType);
        request.setProductId(productId);
        request.setLimitPay(limitPay);
        request.setOpenid(openId);
        request.setSceneInfo(sceneInfo);

        return request;
    }
}
