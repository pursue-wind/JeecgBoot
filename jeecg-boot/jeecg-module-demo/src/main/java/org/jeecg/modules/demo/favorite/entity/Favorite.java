package org.jeecg.modules.demo.favorite.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: favorite
 * @Author: jeecg-boot
 * @Date:   2024-12-23
 * @Version: V1.0
 */
@Data
@TableName("favorite")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="favorite对象", description="favorite")
public class Favorite implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**device_id*/
	@Excel(name = "device_id", width = 15)
    @ApiModelProperty(value = "device_id")
    private java.lang.String deviceId;
	/**关联的类型*/
	@Excel(name = "关联的类型", width = 15, dicCode = "fav_type")
	@Dict(dicCode = "fav_type")
    @ApiModelProperty(value = "关联的类型")
    private java.lang.Integer favType;
	/**标题*/
	@Excel(name = "标题", width = 15)
    @ApiModelProperty(value = "标题")
    private java.lang.String title;
	/**描述*/
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private java.lang.String txt;
	/**是否启用*/
    @Excel(name = "是否启用", width = 15,replace = {"是_1","否_2"} )
    @ApiModelProperty(value = "是否启用")
    private java.lang.Integer isOpen;
}
