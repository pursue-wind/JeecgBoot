package org.jeecg.modules.demo.reg;


import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.modules.redis.client.JeecgRedisClient;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.encryption.AesEncryptUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.app_info.entity.AppInfo;
import org.jeecg.modules.demo.app_info.service.IAppInfoService;
import org.jeecg.modules.demo.devices.entity.Devices;
import org.jeecg.modules.demo.devices.service.IDevicesService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.*;
import org.jeewx.api.wxuser.user.JwUserAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @Author scott
 * @since 2018-12-20
 */
@Slf4j
@RestController
@RequestMapping("/sys/user")
public class RegController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysDepartService sysDepartService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysUserDepartService sysUserDepartService;

    @Autowired
    private ISysDepartRoleUserService departRoleUserService;

    @Autowired
    private ISysDepartRoleService departRoleService;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    @Autowired
    private IDevicesService devicesService;

    @Autowired
    private ISysUserAgentService sysUserAgentService;

    @Autowired
    private ISysPositionService sysPositionService;

    @Autowired
    private ISysUserTenantService userTenantService;

    @Autowired
    private JeecgRedisClient jeecgRedisClient;
    @Autowired
    private IAppInfoService appInfoService;

    /**
     * 注册接口
     *
     * @param jsonObject
     * @param user
     * @return
     */
    @ApiOperation(value = "注册设备", notes = "json 传 id：deviceId，openId，c：aes加密的deviceId，返回 aksk 用于登录")
    @PostMapping("/reg")
    public Result<JSONObject> deviceRegister(@RequestBody JSONObject jsonObject, SysUser user) {
        Result<JSONObject> result = new Result<JSONObject>();
        String deviceId = jsonObject.getString("id");
        String openId = jsonObject.getString("openId");
        String code = jsonObject.getString("c");
        try {
            String s = AesEncryptUtil.desEncrypt(code);
            if (!s.equals(deviceId)) {
                result.setMessage("err");
                result.setSuccess(false);
                return result;
            }
        } catch (Exception e) {
            result.setMessage("err");
            result.setSuccess(false);
            return result;
        }
        String password = RandomUtil.randomString(8);
        SysUser sysUser1 = sysUserService.getUserByName(deviceId);
        if (sysUser1 != null) {
            result.setMessage("用户名已注册");
            result.setSuccess(true);
            Map<String, Object> data = ImmutableMap.of(
                    "ak", deviceId,
                    "sk", sysUser1.getEmail()
            );
            return Result.OK(new JSONObject(data));
        }

        try {
            user.setCreateTime(new Date());// 设置创建时间
            String salt = oConvertUtils.randomGen(8);
            String passwordEncode = PasswordUtil.encrypt(deviceId, password, salt);
            user.setSalt(salt);
            user.setUsername(deviceId);
            user.setRealname(deviceId);
            user.setPassword(passwordEncode);
            user.setEmail(password);
            user.setPhone(deviceId);
            user.setStatus(CommonConstant.USER_UNFREEZE);
            user.setDelFlag(CommonConstant.DEL_FLAG_0);
            user.setActivitiSync(CommonConstant.ACT_SYNC_1);
            sysUserService.addUserWithRole(user, "1169504891467464705");//默认临时角色 test
            Devices entity = new Devices();
            entity.setDeviceId(deviceId);
            entity.setOpenId(openId);
            entity.setCreateTime(new Date());
            entity.setEn(1);
            entity.setDeviceType("1");
            devicesService.save(entity);

            Map<String, Object> data = ImmutableMap.of(
                    "ak", deviceId,
                    "sk", password
            );
            return Result.OK(new JSONObject(data));
        } catch (Exception e) {
            result.error500("注册失败");
        }
        return result;
    }

    @ApiOperation(value = "微信登陆", notes = "json 传 c：微信公众号名字或者名字根据配置来，code：wechat login code")
    @PostMapping("/wcLogin")
    public Result<JSONObject> wechatLogin(@RequestBody JSONObject jsonObject) {
        Result<JSONObject> result = new Result<JSONObject>();
        String cus = jsonObject.getString("c");
        String code = jsonObject.getString("code");
        try {
            AppInfo one = appInfoService.getOne(new LambdaQueryWrapper<AppInfo>().eq(AppInfo::getCus, cus));
            if (one == null) {
                result.setMessage("not found customer");
                result.setSuccess(false);
                return result;
            }
            JSONObject wechat = JSON.parseObject(one.getWechat());
            JSONObject iot = JSON.parseObject(one.getIot());
            String appId = wechat.getString("appId");
            String appSecret = wechat.getString("appSecret");

            if (StringUtils.isBlank(appId) || StringUtils.isBlank(appSecret)) {
                result.setMessage("wechat config err");
                result.setSuccess(false);
                return result;
            }

            WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
            configStorage.setAppId(appId);
            configStorage.setSecret(appSecret);
            WxMpService wxMpService = new WxMpServiceImpl();
            wxMpService.setWxMpConfigStorage(configStorage);
            WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);

            Map<String, Object> data = ImmutableMap.of(
                    "ak", iot,
                    "wxRet", accessToken
            );
            return Result.OK(new JSONObject(data));
        } catch (Exception e) {
            result.error500("注册失败");
        }
        return result;
    }
}