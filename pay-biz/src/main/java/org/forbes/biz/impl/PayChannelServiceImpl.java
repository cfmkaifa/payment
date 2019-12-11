package org.forbes.biz.impl;

import org.forbes.biz.IPayChannelService;
import org.forbes.dal.entity.PayChannel;
import org.forbes.dal.mapper.PayChannelMapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
@Service
public class PayChannelServiceImpl extends ServiceImpl<PayChannelMapper, PayChannel> implements IPayChannelService {
	
	
}
