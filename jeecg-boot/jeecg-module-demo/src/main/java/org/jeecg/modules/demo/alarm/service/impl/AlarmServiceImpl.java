package org.jeecg.modules.demo.alarm.service.impl;

import org.jeecg.modules.demo.alarm.entity.Alarm;
import org.jeecg.modules.demo.alarm.mapper.AlarmMapper;
import org.jeecg.modules.demo.alarm.service.IAlarmService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 闹钟
 * @Author: jeecg-boot
 * @Date:   2024-12-08
 * @Version: V1.0
 */
@Service
public class AlarmServiceImpl extends ServiceImpl<AlarmMapper, Alarm> implements IAlarmService {

}
