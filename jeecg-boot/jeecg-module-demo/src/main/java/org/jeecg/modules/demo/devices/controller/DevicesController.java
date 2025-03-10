package org.jeecg.modules.demo.devices.controller;

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
import org.jeecg.modules.demo.devices.entity.Devices;
import org.jeecg.modules.demo.devices.service.IDevicesService;

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
 * @Description: devices
 * @Author: jeecg-boot
 * @Date:   2024-12-12
 * @Version: V1.0
 */
@Api(tags="devices")
@RestController
@RequestMapping("/devices/devices")
@Slf4j
public class DevicesController extends JeecgController<Devices, IDevicesService> {
	@Autowired
	private IDevicesService devicesService;
	
	/**
	 * 分页列表查询
	 *
	 * @param devices
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "devices-分页列表查询")
	@ApiOperation(value="devices-分页列表查询", notes="devices-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Devices>> queryPageList(Devices devices,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<Devices> queryWrapper = QueryGenerator.initQueryWrapper(devices, req.getParameterMap());
		Page<Devices> page = new Page<Devices>(pageNo, pageSize);
		IPage<Devices> pageList = devicesService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param devices
	 * @return
	 */
	@AutoLog(value = "devices-添加")
	@ApiOperation(value="devices-添加", notes="devices-添加")
	@RequiresPermissions("devices:devices:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Devices devices) {
		devicesService.save(devices);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param devices
	 * @return
	 */
	@AutoLog(value = "devices-编辑")
	@ApiOperation(value="devices-编辑", notes="devices-编辑")
	@RequiresPermissions("devices:devices:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Devices devices) {
		devicesService.updateById(devices);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "devices-通过id删除")
	@ApiOperation(value="devices-通过id删除", notes="devices-通过id删除")
	@RequiresPermissions("devices:devices:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		devicesService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "devices-批量删除")
	@ApiOperation(value="devices-批量删除", notes="devices-批量删除")
	@RequiresPermissions("devices:devices:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.devicesService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "devices-通过id查询")
	@ApiOperation(value="devices-通过id查询", notes="devices-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Devices> queryById(@RequestParam(name="id",required=true) String id) {
		Devices devices = devicesService.getById(id);
		if(devices==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(devices);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param devices
    */
    @RequiresPermissions("devices:devices:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Devices devices) {
        return super.exportXls(request, devices, Devices.class, "devices");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("devices:devices:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Devices.class);
    }

}
