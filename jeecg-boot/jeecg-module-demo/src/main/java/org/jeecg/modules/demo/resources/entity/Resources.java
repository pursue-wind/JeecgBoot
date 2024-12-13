package org.jeecg.modules.demo.resources.entity;

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
 * @Description: 资源
 * @Author: jeecg-boot
 * @Date:   2024-12-11
 * @Version: V1.0
 */
@Data
@TableName("resources")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="resources对象", description="资源")
public class Resources implements Serializable {
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
	/**分类ID*/
	@Excel(name = "分类ID", width = 15, dictTable = "sys_category", dicText = "name", dicCode = "id")
	@Dict(dictTable = "sys_category", dicText = "name", dicCode = "id")
    @ApiModelProperty(value = "分类ID")
    private java.lang.String cid;
	/**资源名*/
	@Excel(name = "资源名", width = 15)
    @ApiModelProperty(value = "资源名")
    private java.lang.String name;
	/**img url*/
	@Excel(name = "img url", width = 15)
    @ApiModelProperty(value = "img url")
    private java.lang.String img;
	/**gif url*/
	@Excel(name = "gif url", width = 15)
    @ApiModelProperty(value = "gif url")
    private java.lang.String gif;
	/**mp3 url*/
	@Excel(name = "mp3 url", width = 15)
    @ApiModelProperty(value = "mp3 url")
    private java.lang.String au;
	/**资源详情*/
	@Excel(name = "资源详情", width = 15)
    @ApiModelProperty(value = "资源详情")
    private java.lang.String txt;
}
