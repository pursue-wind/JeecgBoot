package org.jeecg.modules.demo.app_info.controller;

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
import org.jeecg.modules.demo.app_info.entity.AppInfo;
import org.jeecg.modules.demo.app_info.service.IAppInfoService;

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
 * @Description: app_info
 * @Author: jeecg-boot
 * @Date:   2024-12-24
 * @Version: V1.0
 */
@Api(tags="app_info")
@RestController
@RequestMapping("/app_info/appInfo")
@Slf4j
public class AppInfoController extends JeecgController<AppInfo, IAppInfoService> {
	@Autowired
	private IAppInfoService appInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param appInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "app_info-分页列表查询")
	@ApiOperation(value="app_info-分页列表查询", notes="app_info-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<AppInfo>> queryPageList(AppInfo appInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<AppInfo> queryWrapper = QueryGenerator.initQueryWrapper(appInfo, req.getParameterMap());
		Page<AppInfo> page = new Page<AppInfo>(pageNo, pageSize);
		IPage<AppInfo> pageList = appInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param appInfo
	 * @return
	 */
	@AutoLog(value = "app_info-添加")
	@ApiOperation(value="app_info-添加", notes="app_info-添加")
	@RequiresPermissions("app_info:app_info:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody AppInfo appInfo) {
		appInfoService.save(appInfo);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param appInfo
	 * @return
	 */
	@AutoLog(value = "app_info-编辑")
	@ApiOperation(value="app_info-编辑", notes="app_info-编辑")
	@RequiresPermissions("app_info:app_info:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody AppInfo appInfo) {
		appInfoService.updateById(appInfo);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "app_info-通过id删除")
	@ApiOperation(value="app_info-通过id删除", notes="app_info-通过id删除")
	@RequiresPermissions("app_info:app_info:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		appInfoService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "app_info-批量删除")
	@ApiOperation(value="app_info-批量删除", notes="app_info-批量删除")
	@RequiresPermissions("app_info:app_info:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.appInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "app_info-通过id查询")
	@ApiOperation(value="app_info-通过id查询", notes="app_info-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<AppInfo> queryById(@RequestParam(name="id",required=true) String id) {
		AppInfo appInfo = appInfoService.getById(id);
		if(appInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(appInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param appInfo
    */
    @RequiresPermissions("app_info:app_info:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AppInfo appInfo) {
        return super.exportXls(request, appInfo, AppInfo.class, "app_info");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("app_info:app_info:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, AppInfo.class);
    }

}
