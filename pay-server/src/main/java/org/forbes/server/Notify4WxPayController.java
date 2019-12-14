package org.forbes.server;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.forbes.biz.IPayChannelService;
import org.forbes.biz.IPayOrderService;
import org.forbes.comm.constant.DataColumnConstant;
import org.forbes.comm.constant.PayConstant;
import org.forbes.config.channel.wechat.WxPayUtil;
import org.forbes.dal.entity.PayChannel;
import org.forbes.dal.entity.PayOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;

import lombok.extern.slf4j.Slf4j;
/***
 * Notify4WxPayController概要说明：接收处理微信通知
 * @author Huanghy
 */
@RestController
@Slf4j
public class Notify4WxPayController extends Notify4BasePay {

	@Autowired
	private IPayOrderService payOrderService;

	@Autowired
	private IPayChannelService payChannelService;

	/**
	 * 微信支付(统一下单接口)后台通知响应
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
     */
	@RequestMapping("/pay/wxPayNotifyRes.htm")
	@ResponseBody
	public String wxPayNotifyRes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return doWxPayRes(request, response);
	}

	/***
	 * doWxPayRes方法慨述:
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException String
	 * @创建人 huanghy
	 * @创建时间 2019年12月13日 上午9:46:11
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	public String doWxPayRes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String logPrefix = "【微信支付回调通知】";
		log.info("====== 开始接收微信支付回调通知 ======");
		try {
			String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
			WxPayService wxPayService = new WxPayServiceImpl();
			WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlResult);
			Map<String, Object> payContext = new HashMap<String, Object>();
			payContext.put("parameters", result);
			// 验证业务数据是否正确,验证通过后返回PayOrder和WxPayConfig对象
			if(!verifyWxPayParams(payContext)) {
				return WxPayNotifyResponse.fail((String) payContext.get("retMsg"));
			}
			PayOrder payOrder = (PayOrder) payContext.get("payOrder");
			WxPayConfig wxPayConfig = (WxPayConfig) payContext.get("wxPayConfig");
			wxPayService.setConfig(wxPayConfig);
			// 这里做了签名校验(这里又做了一次xml转换对象,可以考虑优化)
			wxPayService.parseOrderNotifyResult(xmlResult);
			// 处理订单
			byte payStatus = payOrder.getStatus(); // 0：订单生成，1：支付中，-1：支付失败，2：支付成功，3：业务处理完成，-2：订单过期
			if (payStatus != PayConstant.PAY_STATUS_SUCCESS && payStatus != PayConstant.PAY_STATUS_COMPLETE) {
				int updatePayOrderRows = payOrderService.updateStatus4Success(payOrder.getPayOrderId());
				if (updatePayOrderRows != 1) {
					log.error("{}更新支付状态失败,将payOrderId={},更新payStatus={}失败", logPrefix, payOrder.getPayOrderId(), PayConstant.PAY_STATUS_SUCCESS);
					return WxPayNotifyResponse.fail("处理订单失败");
				}
				log.error("{}更新支付状态成功,将payOrderId={},更新payStatus={}成功", logPrefix, payOrder.getPayOrderId(), PayConstant.PAY_STATUS_SUCCESS);
				payOrder.setStatus(PayConstant.PAY_STATUS_SUCCESS);
			}
			// 业务系统后端通知
			doNotify(payOrder);
			log.info("====== 完成接收微信支付回调通知 ======");
			return WxPayNotifyResponse.success("处理成功");
		} catch (WxPayException e) {
			//出现业务错误
			log.trace("微信回调结果异常,异常原因",e);
			log.info("{}请求数据result_code=FAIL", logPrefix);
			log.info("err_code:", e.getErrCode());
			log.info("err_code_des:", e.getErrCodeDes());
			return WxPayNotifyResponse.fail(e.getMessage());
		} catch (Exception e) {
			log.error("微信回调结果异常,异常原因",e);
			return WxPayNotifyResponse.fail(e.getMessage());
		}
	}
	
	/**
	 * 验证微信支付通知参数
	 * @return
	 */
	public boolean verifyWxPayParams(Map<String, Object> payContext) {
		WxPayOrderNotifyResult params = (WxPayOrderNotifyResult)payContext.get("parameters");

		//校验结果是否成功
		if (!PayConstant.RETURN_VALUE_SUCCESS.equalsIgnoreCase(params.getResultCode())
				&& !PayConstant.RETURN_VALUE_SUCCESS.equalsIgnoreCase(params.getReturnCode())) {
			log.error("returnCode={},resultCode={},errCode={},errCodeDes={}", params.getReturnCode(), params.getResultCode(), params.getErrCode(), params.getErrCodeDes());
			payContext.put("retMsg", "notify data failed");
			return false;
		}

		Integer total_fee = params.getTotalFee();   			// 总金额
		String out_trade_no = params.getOutTradeNo();			// 商户系统订单号

		// 查询payOrder记录
		String payOrderId = out_trade_no;
		PayOrder payOrder = payOrderService.getOne(new QueryWrapper<PayOrder>()
				.eq(DataColumnConstant.PAY_ORDER_ID, payOrderId));
		if (payOrder==null) {
			log.error("Can't found payOrder form db. payOrderId={}, ", payOrderId);
			payContext.put("retMsg", "Can't found payOrder");
			return false;
		}

		// 查询payChannel记录
		String mchId = payOrder.getMchId();
		String channelId = payOrder.getChannelId();
		PayChannel payChannel = payChannelService
				.getOne(new QueryWrapper<PayChannel>()
						.eq(DataColumnConstant.CHANNEL_ID, channelId)
						.eq(DataColumnConstant.MCH_ID, mchId));
		if(payChannel == null) {
			log.error("Can't found payChannel form db. mchId={} channelId={}, ", payOrderId, mchId, channelId);
			payContext.put("retMsg", "Can't found payChannel");
			return false;
		}
		payContext.put("wxPayConfig", WxPayUtil.getWxPayConfig(payChannel.getParam()));

		// 核对金额
		long wxPayAmt = new BigDecimal(total_fee).longValue();
		long dbPayAmt = payOrder.getAmount().longValue();
		if (dbPayAmt != wxPayAmt) {
			log.error("db payOrder record payPrice not equals total_fee. total_fee={},payOrderId={}", total_fee, payOrderId);
			payContext.put("retMsg", "total_fee is not the same");
			return false;
		}

		payContext.put("payOrder", payOrder);
		return true;
	}

}
