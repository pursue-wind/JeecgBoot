package org.jeecg.modules.demo.devices.service.impl;

import org.jeecg.modules.demo.devices.entity.Devices;
import org.jeecg.modules.demo.devices.mapper.DevicesMapper;
import org.jeecg.modules.demo.devices.service.IDevicesService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date:   2024-12-08
 * @Version: V1.0
 */
@Service
public class DevicesServiceImpl extends ServiceImpl<DevicesMapper, Devices> implements IDevicesService {

}
