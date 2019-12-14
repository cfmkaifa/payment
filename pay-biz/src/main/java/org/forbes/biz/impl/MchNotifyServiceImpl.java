package org.forbes.biz.impl;
import org.forbes.biz.IMchNotifyService;
import org.forbes.dal.entity.MchNotify;
import org.forbes.dal.mapper.MchNotifyMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
@Service
public class MchNotifyServiceImpl extends ServiceImpl<MchNotifyMapper, MchNotify> implements IMchNotifyService {
	
}
