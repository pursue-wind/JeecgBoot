package org.jeecg.modules.demo.ota.controller;

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
import org.jeecg.modules.demo.ota.entity.Ota;
import org.jeecg.modules.demo.ota.service.IOtaService;

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
 * @Description: ota
 * @Author: jeecg-boot
 * @Date:   2025-01-07
 * @Version: V1.0
 */
@Api(tags="ota")
@RestController
@RequestMapping("/ota/ota")
@Slf4j
public class OtaController extends JeecgController<Ota, IOtaService> {
	@Autowired
	private IOtaService otaService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ota
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "ota-分页列表查询")
	@ApiOperation(value="ota-分页列表查询", notes="ota-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Ota>> queryPageList(Ota ota,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<Ota> queryWrapper = QueryGenerator.initQueryWrapper(ota, req.getParameterMap());
		Page<Ota> page = new Page<Ota>(pageNo, pageSize);
		IPage<Ota> pageList = otaService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ota
	 * @return
	 */
	@AutoLog(value = "ota-添加")
	@ApiOperation(value="ota-添加", notes="ota-添加")
	@RequiresPermissions("ota:ota:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Ota ota) {
		otaService.save(ota);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ota
	 * @return
	 */
	@AutoLog(value = "ota-编辑")
	@ApiOperation(value="ota-编辑", notes="ota-编辑")
	@RequiresPermissions("ota:ota:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Ota ota) {
		otaService.updateById(ota);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ota-通过id删除")
	@ApiOperation(value="ota-通过id删除", notes="ota-通过id删除")
	@RequiresPermissions("ota:ota:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		otaService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ota-批量删除")
	@ApiOperation(value="ota-批量删除", notes="ota-批量删除")
	@RequiresPermissions("ota:ota:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.otaService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "ota-通过id查询")
	@ApiOperation(value="ota-通过id查询", notes="ota-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Ota> queryById(@RequestParam(name="id",required=true) String id) {
		Ota ota = otaService.getById(id);
		if(ota==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ota);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ota
    */
    @RequiresPermissions("ota:ota:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Ota ota) {
        return super.exportXls(request, ota, Ota.class, "ota");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("ota:ota:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Ota.class);
    }

}
