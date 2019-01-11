package com.zhumeng.modules.sys.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 监控
 * @author Gaojun.Zhou
 * @date 2017年2月5日 下午3:38:19
 */
@Controller
@RequestMapping("/backpage/monitor")
public class MonitorController{
	
	/**
	 * 系统监控列表
	 */
	@RequiresPermissions("monitorList")
    @RequestMapping("/list")
    public  String list(Model model){
		return "sys/monitor_list";
    } 
}
