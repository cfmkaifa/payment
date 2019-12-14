package org.forbes.biz;

import org.forbes.dal.entity.PayOrder;

import com.baomidou.mybatisplus.extension.service.IService;
/***
 * IPayOrderService概要说明：支付订单
 * @author Huanghy
 */
public interface IPayOrderService extends IService<PayOrder> {

	
	
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
	int updateStatus4Ing(String payOrderId, String channelOrderNo);
	
	
	/***
	 * updateStatus4Success方法慨述:跟新状态
	 * @param payOrderId
	 * @return int
	 * @创建人 huanghy
	 * @创建时间 2019年12月13日 上午9:37:07
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	int updateStatus4Success(String payOrderId);
}
