package org.jeecg.modules.demo.alarm.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.alarm.entity.Alarm;
import org.jeecg.modules.demo.alarm.service.IAlarmService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 闹钟
 * @Author: jeecg-boot
 * @Date:   2024-12-08
 * @Version: V1.0
 */
@Api(tags="闹钟")
@RestController
@RequestMapping("/alarm/alarm")
@Slf4j
public class AlarmController extends JeecgController<Alarm, IAlarmService> {
	@Autowired
	private IAlarmService alarmService;
	
	/**
	 * 分页列表查询
	 *
	 * @param alarm
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "闹钟-分页列表查询")
	@ApiOperation(value="闹钟-分页列表查询", notes="闹钟-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Alarm>> queryPageList(Alarm alarm,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<Alarm> queryWrapper = QueryGenerator.initQueryWrapper(alarm, req.getParameterMap());
		Page<Alarm> page = new Page<Alarm>(pageNo, pageSize);
		IPage<Alarm> pageList = alarmService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param alarm
	 * @return
	 */
	@AutoLog(value = "闹钟-添加")
	@ApiOperation(value="闹钟-添加", notes="闹钟-添加")
	@RequiresPermissions("alarm:alarm:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Alarm alarm) {
		alarmService.save(alarm);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param alarm
	 * @return
	 */
	@AutoLog(value = "闹钟-编辑")
	@ApiOperation(value="闹钟-编辑", notes="闹钟-编辑")
	@RequiresPermissions("alarm:alarm:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Alarm alarm) {
		alarmService.updateById(alarm);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "闹钟-通过id删除")
	@ApiOperation(value="闹钟-通过id删除", notes="闹钟-通过id删除")
	@RequiresPermissions("alarm:alarm:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		alarmService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "闹钟-批量删除")
	@ApiOperation(value="闹钟-批量删除", notes="闹钟-批量删除")
	@RequiresPermissions("alarm:alarm:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.alarmService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "闹钟-通过id查询")
	@ApiOperation(value="闹钟-通过id查询", notes="闹钟-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Alarm> queryById(@RequestParam(name="id",required=true) String id) {
		Alarm alarm = alarmService.getById(id);
		if(alarm==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(alarm);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param alarm
    */
    @RequiresPermissions("alarm:alarm:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Alarm alarm) {
        return super.exportXls(request, alarm, Alarm.class, "闹钟");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("alarm:alarm:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Alarm.class);
    }

}
