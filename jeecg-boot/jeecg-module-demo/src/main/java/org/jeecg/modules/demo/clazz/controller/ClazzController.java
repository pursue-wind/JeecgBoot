package org.jeecg.modules.demo.clazz.controller;

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
import org.jeecg.modules.demo.clazz.entity.Clazz;
import org.jeecg.modules.demo.clazz.service.IClazzService;

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
 * @Description: 课程表
 * @Author: jeecg-boot
 * @Date:   2024-12-13
 * @Version: V1.0
 */
@Api(tags="课程表")
@RestController
@RequestMapping("/clazz/clazz")
@Slf4j
public class ClazzController extends JeecgController<Clazz, IClazzService> {
	@Autowired
	private IClazzService clazzService;
	
	/**
	 * 分页列表查询
	 *
	 * @param clazz
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "课程表-分页列表查询")
	@ApiOperation(value="课程表-分页列表查询", notes="课程表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Clazz>> queryPageList(Clazz clazz,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<Clazz> queryWrapper = QueryGenerator.initQueryWrapper(clazz, req.getParameterMap());
		Page<Clazz> page = new Page<Clazz>(pageNo, pageSize);
		IPage<Clazz> pageList = clazzService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param clazz
	 * @return
	 */
	@AutoLog(value = "课程表-添加")
	@ApiOperation(value="课程表-添加", notes="课程表-添加")
	@RequiresPermissions("clazz:clazz:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Clazz clazz) {
		clazzService.save(clazz);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param clazz
	 * @return
	 */
	@AutoLog(value = "课程表-编辑")
	@ApiOperation(value="课程表-编辑", notes="课程表-编辑")
	@RequiresPermissions("clazz:clazz:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Clazz clazz) {
		clazzService.updateById(clazz);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "课程表-通过id删除")
	@ApiOperation(value="课程表-通过id删除", notes="课程表-通过id删除")
	@RequiresPermissions("clazz:clazz:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		clazzService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "课程表-批量删除")
	@ApiOperation(value="课程表-批量删除", notes="课程表-批量删除")
	@RequiresPermissions("clazz:clazz:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.clazzService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "课程表-通过id查询")
	@ApiOperation(value="课程表-通过id查询", notes="课程表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Clazz> queryById(@RequestParam(name="id",required=true) String id) {
		Clazz clazz = clazzService.getById(id);
		if(clazz==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(clazz);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param clazz
    */
    @RequiresPermissions("clazz:clazz:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Clazz clazz) {
        return super.exportXls(request, clazz, Clazz.class, "课程表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("clazz:clazz:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Clazz.class);
    }

}
