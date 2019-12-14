package org.forbes.provider;

import org.forbes.biz.IRefundOrderService;
import org.forbes.comm.constant.DataColumnConstant;
import org.forbes.comm.model.BasePageDto;
import org.forbes.comm.util.ConvertUtils;
import org.forbes.comm.vo.Result;
import org.forbes.dal.entity.RefundOrder;
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

@RequestMapping("/refund-order")
@Api(tags={"退款订单"})
@Slf4j
@RestController
public class RefundOrderController {


	@Autowired
	IRefundOrderService refundOrderService;
	
	/***
     * selectPage方法慨述:分页查询转账订单
     * @param pageDto
     * @return Result<IPage<PayChannel>>
     * @创建人 huanghy
     * @创建时间 2019年12月10日 下午1:48:45
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation("分页查询转账订单")
    @ApiResponses(value={
            @ApiResponse(code=500,message= Result.PERMISSIONS_NOT_ERROR_MSG),
            @ApiResponse(code=200,message = Result.PERMISSIONS_MSG)
    })
    public Result<IPage<RefundOrder>> selectPage(BasePageDto pageDto,RefundOrder refundOrder){
        log.debug("传入参数为："+JSON.toJSONString(pageDto));
        Result<IPage<RefundOrder>> result = new Result<>();
        QueryWrapper<RefundOrder> qw = new QueryWrapper<RefundOrder>();
        if (ConvertUtils.isNotEmpty(refundOrder)) {
            if(ConvertUtils.isNotEmpty(refundOrder.getRefundOrderId()) ){
                qw.eq(DataColumnConstant.REFUND_ORDER_ID,refundOrder.getRefundOrderId());
            }
            if(ConvertUtils.isNotEmpty(refundOrder.getMchId())){
                qw.like(DataColumnConstant.MCH_ID,refundOrder.getMchId());
            }
            if(ConvertUtils.isNotEmpty(refundOrder.getStatus())){
                qw.like(DataColumnConstant.STATUS,refundOrder.getStatus());
            }
        }
        IPage<RefundOrder> page = new Page<RefundOrder>(pageDto.getPageNo(),pageDto.getPageSize());
        IPage<RefundOrder> refundOrders = refundOrderService.page(page,qw);
        result.setResult(refundOrders);
        return result;
    }
}
