package com.zhumeng.modules.sys.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhumeng.common.FormatUtil;
import com.zhumeng.common.StringUtils;
import com.zhumeng.modules.sys.dto.ResultInfo;
import com.zhumeng.modules.sys.entity.LoginLog;
import com.zhumeng.modules.sys.service.ILoginLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 系统登录日志表 前端控制器
 * </p>
 *
 * @author Auto Generator
 * @since 2018-10-01
 */
@Controller
@RequestMapping("/loginLog")
public class LoginLogController extends BaseController {

    @Autowired
    private ILoginLogService iloginLogService;

    @RequestMapping("/*")
    public void toHtml(){

    }

    @RequestMapping("/listData")
    @RequiresPermissions("loginLog:view")
    public @ResponseBody
        ResultInfo<List<LoginLog>> listData(String userName, String loginTime, Integer page, Integer limit){
        LoginLog loginLog = new LoginLog();
        loginLog.setUserName(userName);
        EntityWrapper<LoginLog> wrapper = new EntityWrapper<LoginLog>(loginLog);
        if(!StringUtils.isEmpty(loginTime)){
            wrapper.ge("create_time",FormatUtil.parseDate(loginTime.split(" - ")[0]+" 00:00:00", null));
            wrapper.le("create_time",FormatUtil.parseDate(loginTime.split(" - ")[1]+" 23:59:59", null));
        }
        wrapper.orderBy("create_time",false);
        Page<LoginLog> pageObj = iloginLogService.selectPage(new Page<LoginLog>(page,limit), wrapper);
        return new ResultInfo<List<LoginLog>>(pageObj.getRecords(), pageObj.getTotal());
    }

}
