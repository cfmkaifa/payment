package org.forbes.server;

import org.apache.commons.lang.StringUtils;
import org.forbes.biz.IPayOrderService;
import org.forbes.comm.constant.DataColumnConstant;
import org.forbes.comm.constant.PayConstant;
import org.forbes.comm.util.MyBase64;
import org.forbes.dal.entity.PayOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;
/***
 * PayOrderServiceController概要说明：支付订单接口
 * @author Huanghy
 */
@RestController
@Slf4j
public class PayOrderServiceController extends Notify4BasePay {

    @Autowired
    private IPayOrderService payOrderService;

    /***
     * createPayOrder方法慨述:创建订单
     * @param jsonParam
     * @return String
     * @创建人 huanghy
     * @创建时间 2019年12月12日 下午5:23:01
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/pay/create")
    public String createPayOrder(@RequestParam String jsonParam) {
        log.info("接收创建支付订单请求,jsonParam={}", jsonParam);
        JSONObject retObj = new JSONObject();
        retObj.put("code", "0000");
        if(StringUtils.isBlank(jsonParam)) {
            retObj.put("code", "0001");
            retObj.put("msg", "缺少参数");
            return retObj.toJSONString();
        }
        try {
            PayOrder payOrder = JSON.parseObject(new String(MyBase64.decode(jsonParam)), PayOrder.class);
            boolean result = payOrderService.save(payOrder);
            retObj.put("result", result);
        }catch (Exception e) {
            retObj.put("code", "9999"); // 系统错误
            retObj.put("msg", "系统错误");
        }
        return retObj.toJSONString();
    }

    @RequestMapping(value = "/pay/query")
    public String queryPayOrder(@RequestParam String jsonParam) {
        log.info("selectPayOrder << {}", jsonParam);
        JSONObject retObj = new JSONObject();
        retObj.put("code", "0000");
        if(StringUtils.isBlank(jsonParam)) {
            retObj.put("code", "0001"); // 参数错误
            retObj.put("msg", "缺少参数");
            return retObj.toJSONString();
        }
        JSONObject paramObj = JSON.parseObject(new String(MyBase64.decode(jsonParam)));
        String mchId = paramObj.getString("mchId");
        String payOrderId = paramObj.getString("payOrderId");
        String mchOrderNo = paramObj.getString("mchOrderNo");
        PayOrder payOrder;
        if(StringUtils.isNotBlank(payOrderId)) {
            payOrder = payOrderService.getOne(new QueryWrapper<PayOrder>()
            		.eq(DataColumnConstant.MCH_ID, mchId)
            		.eq(DataColumnConstant.PAY_ORDER_ID, payOrderId));
        }else {
            payOrder = payOrderService.getOne(new QueryWrapper<PayOrder>()
            		.eq(DataColumnConstant.MCH_ID, mchId)
            		.eq(DataColumnConstant.MCH_ORDER_NO, mchOrderNo));
        }
        if(payOrder == null) {
            retObj.put("code", "0002");
            retObj.put("msg", "支付订单不存在");
            return retObj.toJSONString();
        }

        //
        boolean executeNotify = paramObj.getBooleanValue("executeNotify");
        // 如果选择回调且支付状态为支付成功,则回调业务系统
        if(executeNotify && payOrder.getStatus() == PayConstant.PAY_STATUS_SUCCESS) {
            this.doNotify(payOrder);
        }
        retObj.put("result", JSON.toJSON(payOrder));
        log.info("selectPayOrder >> {}", retObj);
        return retObj.toJSONString();
    }

}
