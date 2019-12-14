package org.forbes.biz.impl;

import org.forbes.biz.IRefundOrderService;
import org.forbes.dal.entity.RefundOrder;
import org.forbes.dal.mapper.RefundOrderMapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
@Service
public class RefundOrderServiceImpl extends ServiceImpl<RefundOrderMapper, RefundOrder> implements IRefundOrderService {
	
}
