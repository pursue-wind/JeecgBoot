package org.jeecg.modules.demo.favorite.service.impl;

import org.jeecg.modules.demo.favorite.entity.Favorite;
import org.jeecg.modules.demo.favorite.mapper.FavoriteMapper;
import org.jeecg.modules.demo.favorite.service.IFavoriteService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: favorite
 * @Author: jeecg-boot
 * @Date:   2024-12-23
 * @Version: V1.0
 */
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements IFavoriteService {

}
