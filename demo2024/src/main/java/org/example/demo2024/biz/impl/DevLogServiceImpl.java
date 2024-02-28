package org.example.demo2024.biz.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.example.demo2024.biz.IDevLogService;
import org.example.demo2024.entity.DevLog;
import org.example.demo2024.mapper.DevLogMapper;
import org.springframework.stereotype.Service;

/**
 * @program: demo2024
 * @description: dev-log-biz-impl
 * @author: 作者名
 * @create: 2024/02/18
 */
@Service
public class DevLogServiceImpl extends ServiceImpl<DevLogMapper, DevLog>
        implements IDevLogService {


}
