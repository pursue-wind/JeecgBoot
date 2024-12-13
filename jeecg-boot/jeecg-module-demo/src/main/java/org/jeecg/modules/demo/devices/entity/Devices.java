package org.jeecg.modules.demo.devices.entity;

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
 * @Description: devices
 * @Author: jeecg-boot
 * @Date:   2024-12-12
 * @Version: V1.0
 */
@Data
@TableName("devices")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="devices对象", description="devices")
public class Devices implements Serializable {
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
	/**device id*/
	@Excel(name = "device id", width = 15)
    @ApiModelProperty(value = "device id")
    private java.lang.String deviceId;
	/**device type */
	@Excel(name = "device type ", width = 15)
    @ApiModelProperty(value = "device type ")
    private java.lang.String deviceType;
	/**wechat open id*/
	@Excel(name = "wechat open id", width = 15)
    @ApiModelProperty(value = "wechat open id")
    private java.lang.String openId;
	/**是否启用*/
    @Excel(name = "是否启用", width = 15,replace = {"是_1","否_2"} )
    @ApiModelProperty(value = "是否启用")
    private java.lang.Integer en;
}
