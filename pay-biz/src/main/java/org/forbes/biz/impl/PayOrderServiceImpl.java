package org.forbes.biz.impl;

import org.forbes.biz.IPayOrderService;
import org.forbes.dal.entity.PayOrder;
import org.forbes.dal.mapper.PayOrderMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {

}
