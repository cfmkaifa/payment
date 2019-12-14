package org.forbes.provider;

import org.forbes.biz.IMchNotifyService;
import org.forbes.comm.constant.DataColumnConstant;
import org.forbes.comm.model.BasePageDto;
import org.forbes.comm.util.ConvertUtils;
import org.forbes.comm.vo.Result;
import org.forbes.dal.entity.MchNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/mch-notify")
@Api(tags={"商户通知"})
@Slf4j
@RestController
public class MchNotifyController {

	
	@Autowired
	IMchNotifyService  mchNotifyService;
	
	
	/***
     * selectPage方法慨述:分页查询渠道信息
     * @param pageDto
     * @return Result<IPage<PayChannel>>
     * @创建人 huanghy
     * @创建时间 2019年12月10日 下午1:48:45
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation("分页查询渠道信息")
    @ApiResponses(value={
            @ApiResponse(code=500,message= Result.PERMISSIONS_NOT_ERROR_MSG),
            @ApiResponse(code=200,message = Result.PERMISSIONS_MSG)
    })
    public Result<IPage<MchNotify>> selectPage(BasePageDto pageDto,MchNotify mchNotify){
        log.debug("传入参数为："+JSON.toJSONString(pageDto));
        Result<IPage<MchNotify>> result = new Result<>();
        QueryWrapper<MchNotify> qw = new QueryWrapper<MchNotify>();
        if (ConvertUtils.isNotEmpty(mchNotify)) {
            if(ConvertUtils.isNotEmpty(mchNotify.getMchOrderNo()) ){
                qw.eq(DataColumnConstant.MCH_ORDER_NO,mchNotify.getMchOrderNo());
            }
            if(ConvertUtils.isNotEmpty(mchNotify.getOrderId())){
                qw.like(DataColumnConstant.ORDER_ID,mchNotify.getOrderId());
            }
            if(ConvertUtils.isNotEmpty(mchNotify.getStatus())){
                qw.like(DataColumnConstant.STATUS,mchNotify.getStatus());
            }
        }
        IPage<MchNotify> page = new Page<MchNotify>(pageDto.getPageNo(),pageDto.getPageSize());
        IPage<MchNotify> pPayChannels = mchNotifyService.page(page,qw);
        result.setResult(pPayChannels);
        return result;
    }
}
