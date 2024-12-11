package org.jeecg.modules.demo.resources.service.impl;

import org.jeecg.modules.demo.resources.entity.Resources;
import org.jeecg.modules.demo.resources.mapper.ResourcesMapper;
import org.jeecg.modules.demo.resources.service.IResourcesService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 资源
 * @Author: jeecg-boot
 * @Date:   2024-12-11
 * @Version: V1.0
 */
@Service
public class ResourcesServiceImpl extends ServiceImpl<ResourcesMapper, Resources> implements IResourcesService {

}
