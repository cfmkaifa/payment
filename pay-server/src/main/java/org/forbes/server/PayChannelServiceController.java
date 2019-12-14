package org.forbes.server;

import org.apache.commons.lang.StringUtils;
import org.forbes.biz.IPayChannelService;
import org.forbes.comm.constant.DataColumnConstant;
import org.forbes.comm.util.MyBase64;
import org.forbes.dal.entity.PayChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;
/***
 * PayChannelServiceController概要说明：支付渠道接口
 * @author Huanghy
 */
@RestController
@Slf4j
public class PayChannelServiceController {

    @Autowired
    private IPayChannelService payChannelService;

    /***
     * selectPayChannel方法慨述:查询渠道信息
     * @param jsonParam
     * @return String
     * @创建人 huanghy
     * @创建时间 2019年12月13日 上午8:20:28
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/pay-channel/select")
    public String selectPayChannel(@RequestParam String jsonParam) {
        log.info("selectPayChannel << {}", jsonParam);
        JSONObject retObj = new JSONObject();
        retObj.put("code", "0000");
        if(StringUtils.isBlank(jsonParam)) {
            retObj.put("code", "0001"); // 参数错误
            retObj.put("msg", "缺少参数");
            return retObj.toJSONString();
        }
        JSONObject paramObj = JSON.parseObject(new String(MyBase64.decode(jsonParam)));
        String channelId = paramObj.getString("channelId");
        String mchId = paramObj.getString("mchId");
        PayChannel payChannel = payChannelService.getOne(new QueryWrapper<PayChannel>()
        		.eq(DataColumnConstant.CHANNEL_ID, channelId)
        		.eq(DataColumnConstant.MCH_ID, mchId));
        if(payChannel == null) {
            retObj.put("code", "0002");
            retObj.put("msg", "数据对象不存在");
            return retObj.toJSONString();
        }
        retObj.put("result", JSON.toJSON(payChannel));
        log.info("selectPayChannel >> {}", retObj);
        return retObj.toJSONString();
    }


}
