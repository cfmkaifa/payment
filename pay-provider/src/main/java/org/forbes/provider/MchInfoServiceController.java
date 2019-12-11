package org.forbes.provider;

import org.apache.commons.lang.StringUtils;
import org.forbes.biz.IMchInfoService;
import org.forbes.comm.constant.Constant;
import org.forbes.comm.constant.DataColumnConstant;
import org.forbes.comm.util.MyBase64;
import org.forbes.dal.entity.MchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;
/***
 * MchInfoServiceController概要说明：商户信息接口
 * @author Huanghy
 */
@RestController
@Slf4j
public class MchInfoServiceController {

    @Autowired
    private IMchInfoService mchInfoService;

    /***
     * selectMchInfo方法慨述:参数校验
     * @param jsonParam
     * @return String
     * @创建人 huanghy
     * @创建时间 2019年12月11日 下午1:28:09
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/mch-info/select")
    public String selectMchInfo(@RequestParam String jsonParam) {
        // 参数校验
        String param = new String(MyBase64.decode(jsonParam));
        JSONObject paramObj = JSON.parseObject(param);
        String mchId = paramObj.getString(Constant.MCH_ID);
        MchInfo mchInfo = mchInfoService.getOne(new QueryWrapper<MchInfo>()
        		.eq(DataColumnConstant.MCH_ID, mchId));
        JSONObject retObj = new JSONObject();
        retObj.put("code", "0000");
        if(StringUtils.isBlank(jsonParam)) {
            retObj.put("code", "0001"); // 参数错误
            retObj.put("msg", "缺少参数");
            return retObj.toJSONString();
        }
        if(mchInfo == null) {
            retObj.put("code", "0002");
            retObj.put("msg", "数据对象不存在");
            return retObj.toJSONString();
        }
        retObj.put("result", JSON.toJSON(mchInfo));
        log.info("result:{}", retObj.toJSONString());
        return retObj.toJSONString();
    }



}
