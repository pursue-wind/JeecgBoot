package org.jeecg.modules.demo.resources.controller;

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
import org.jeecg.modules.demo.resources.entity.Resources;
import org.jeecg.modules.demo.resources.service.IResourcesService;

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
 * @Description: 资源
 * @Author: jeecg-boot
 * @Date:   2024-12-11
 * @Version: V1.0
 */
@Api(tags="资源")
@RestController
@RequestMapping("/resources/resources")
@Slf4j
public class ResourcesController extends JeecgController<Resources, IResourcesService> {
	@Autowired
	private IResourcesService resourcesService;
	
	/**
	 * 分页列表查询
	 *
	 * @param resources
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "资源-分页列表查询")
	@ApiOperation(value="资源-分页列表查询", notes="资源-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Resources>> queryPageList(Resources resources,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<Resources> queryWrapper = QueryGenerator.initQueryWrapper(resources, req.getParameterMap());
		Page<Resources> page = new Page<Resources>(pageNo, pageSize);
		IPage<Resources> pageList = resourcesService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param resources
	 * @return
	 */
	@AutoLog(value = "资源-添加")
	@ApiOperation(value="资源-添加", notes="资源-添加")
	@RequiresPermissions("resources:resources:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Resources resources) {
		resourcesService.save(resources);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param resources
	 * @return
	 */
	@AutoLog(value = "资源-编辑")
	@ApiOperation(value="资源-编辑", notes="资源-编辑")
	@RequiresPermissions("resources:resources:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Resources resources) {
		resourcesService.updateById(resources);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "资源-通过id删除")
	@ApiOperation(value="资源-通过id删除", notes="资源-通过id删除")
	@RequiresPermissions("resources:resources:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		resourcesService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "资源-批量删除")
	@ApiOperation(value="资源-批量删除", notes="资源-批量删除")
	@RequiresPermissions("resources:resources:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.resourcesService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "资源-通过id查询")
	@ApiOperation(value="资源-通过id查询", notes="资源-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Resources> queryById(@RequestParam(name="id",required=true) String id) {
		Resources resources = resourcesService.getById(id);
		if(resources==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(resources);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param resources
    */
    @RequiresPermissions("resources:resources:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Resources resources) {
        return super.exportXls(request, resources, Resources.class, "资源");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("resources:resources:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Resources.class);
    }

}
