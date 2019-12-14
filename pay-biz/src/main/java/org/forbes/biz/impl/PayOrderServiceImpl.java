package org.forbes.biz.impl;

import org.forbes.biz.IPayOrderService;
import org.forbes.comm.constant.DataColumnConstant;
import org.forbes.comm.constant.PayConstant;
import org.forbes.dal.entity.PayOrder;
import org.forbes.dal.mapper.PayOrderMapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {

	
	
	
	
	/***
	 * updateStatus4Ing方法慨述:更新支付状态 
	 * @param payOrderId
	 * @param channelOrderNo
	 * @return boolean
	 * @创建人 huanghy
	 * @创建时间 2019年12月13日 上午9:16:45
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	public int updateStatus4Ing(String payOrderId, String channelOrderNo){
		PayOrder payOrder = new PayOrder();
        payOrder.setStatus(PayConstant.PAY_STATUS_PAYING);
        if(channelOrderNo != null) payOrder.setChannelOrderNo(channelOrderNo);
        payOrder.setPaySuccTime(System.currentTimeMillis());
        return baseMapper.update(payOrder, new QueryWrapper<PayOrder>()
        		.eq(DataColumnConstant.PAY_ORDER_ID, payOrderId)
        		.eq(DataColumnConstant.STATUS, PayConstant.PAY_STATUS_INIT));
	}
	
	
	/***
	 * updateStatus4Success方法慨述:跟新状态
	 * @param payOrderId
	 * @return int
	 * @创建人 huanghy
	 * @创建时间 2019年12月13日 上午9:37:07
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	public int updateStatus4Success(String payOrderId){
		 PayOrder payOrder = new PayOrder();
         payOrder.setPayOrderId(payOrderId);
         payOrder.setStatus(PayConstant.PAY_STATUS_SUCCESS);
         payOrder.setPaySuccTime(System.currentTimeMillis());
         return baseMapper.update(payOrder, new QueryWrapper<PayOrder>()
         		.eq(DataColumnConstant.PAY_ORDER_ID, payOrderId)
         		.eq(DataColumnConstant.STATUS, PayConstant.PAY_STATUS_PAYING));
	}
}
