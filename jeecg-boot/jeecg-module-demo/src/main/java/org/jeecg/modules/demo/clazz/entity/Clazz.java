package org.jeecg.modules.demo.clazz.entity;

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
 * @Description: 课程表
 * @Author: jeecg-boot
 * @Date:   2024-12-13
 * @Version: V1.0
 */
@Data
@TableName("clazz")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="clazz对象", description="课程表")
public class Clazz implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
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
	/**device id*/
	@Excel(name = "device id", width = 15, dictTable = "devices", dicText = "device_id", dicCode = "device_id")
	@Dict(dictTable = "devices", dicText = "device_id", dicCode = "device_id")
    @ApiModelProperty(value = "device id")
    private java.lang.String deviceId;
	/**day_of_week*/
	@Excel(name = "day_of_week", width = 15)
    @ApiModelProperty(value = "day_of_week")
    private java.lang.Integer dow;
	/**order*/
	@Excel(name = "order", width = 15)
    @ApiModelProperty(value = "order")
    private java.lang.Integer ord;
	/**class name*/
	@Excel(name = "class name", width = 15)
    @ApiModelProperty(value = "class name")
    private java.lang.String clName;
}
