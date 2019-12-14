package org.forbes.biz.impl;

import org.forbes.biz.ITransOrderService;
import org.forbes.dal.entity.TransOrder;
import org.forbes.dal.mapper.TransOrderMapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
@Service
public class TransOrderServiceImpl extends ServiceImpl<TransOrderMapper, TransOrder> implements ITransOrderService {}
