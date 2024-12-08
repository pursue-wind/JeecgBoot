package org.jeecg.modules.demo.alarm.entity;

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
 * @Description: 闹钟
 * @Author: jeecg-boot
 * @Date:   2024-12-08
 * @Version: V1.0
 */
@Data
@TableName("alarm")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="alarm对象", description="闹钟")
public class Alarm implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**device_id*/
	@Excel(name = "device_id", width = 15)
    @ApiModelProperty(value = "device_id")
    private java.lang.String deviceId;
	/**提醒时间*/
	@Excel(name = "提醒时间", width = 15)
    @ApiModelProperty(value = "提醒时间")
    private java.lang.String alarmTime;
	/**周一到周日分别对应 2 4 8 16 32 64 128*/
	@Excel(name = "周一到周日分别对应 2 4 8 16 32 64 128", width = 15)
    @ApiModelProperty(value = "周一到周日分别对应 2 4 8 16 32 64 128")
    private java.lang.Integer alarmDate;
	/**关联的类型*/
	@Excel(name = "关联的类型", width = 15, dicCode = "alarm_type")
	@Dict(dicCode = "alarm_type")
    @ApiModelProperty(value = "关联的类型")
    private java.lang.Integer relaType;
	/**关联类型对应的id*/
	@Excel(name = "关联类型对应的id", width = 15)
    @ApiModelProperty(value = "关联类型对应的id")
    private java.lang.String relaId;
	/**提醒对应的标题文本*/
	@Excel(name = "提醒对应的标题文本", width = 15)
    @ApiModelProperty(value = "提醒对应的标题文本")
    private java.lang.String title;
	/**是否重复*/
    @Excel(name = "是否重复", width = 15,replace = {"是_1","否_2"} )
    @ApiModelProperty(value = "是否重复")
    private java.lang.Integer isRepeat;
	/**是否启用*/
    @Excel(name = "是否启用", width = 15,replace = {"是_1","否_2"} )
    @ApiModelProperty(value = "是否启用")
    private java.lang.String isOpen;
	/**描述*/
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private java.lang.String descTxt;
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
}
