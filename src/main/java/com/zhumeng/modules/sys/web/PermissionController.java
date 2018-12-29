package com.zhumeng.modules.sys.web;

import com.zhumeng.annotation.SysLog;
import com.zhumeng.enums.ResourceType;
import com.zhumeng.modules.sys.dto.EnumInfo;
import com.zhumeng.modules.sys.dto.PermissionInfo;
import com.zhumeng.modules.sys.dto.ResultInfo;
import com.zhumeng.modules.sys.entity.Permission;
import com.zhumeng.modules.sys.service.IPermissionService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 系统权限表 前端控制器
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-10
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    @Autowired
    private IPermissionService iPermissionService;

    @RequestMapping("/*")
    public void toHtml() {

    }

    @RequestMapping("/listData")
    @RequiresPermissions("permission:view")
    public @ResponseBody
    ResultInfo<List<Permission>> listData() {
        return new ResultInfo<List<Permission>>(iPermissionService.getAllPermissions());
    }

    @SysLog("保存权限操作")
    @RequestMapping("/save")
    @RequiresPermissions(value = {"permission:add", "permission:edit"}, logical = Logical.OR)
    public @ResponseBody
    ResultInfo<Boolean> save(Permission permission) {
        return new ResultInfo<Boolean>(iPermissionService.savePermission(permission));
    }

    @SysLog("删除权限操作")
    @RequestMapping("/delBatch")
    @RequiresPermissions("permission:del")
    public @ResponseBody
    ResultInfo<Boolean> delBatch(Integer[] idArr) {
        return new ResultInfo<Boolean>(iPermissionService.delBatchPermission(Arrays.asList(idArr)));
    }

    @RequestMapping("/typeSelectData")
    public @ResponseBody
    ResultInfo<List<EnumInfo>> typeSelectData() {
        return new ResultInfo<List<EnumInfo>>(ResourceType.getAllEnumInfo());
    }

    @RequestMapping("/parentSelectData")
    public @ResponseBody
    ResultInfo<List<Permission>> parentSelectData(String resourceType) {
        List<Permission> list = new ArrayList<Permission>();
        if (resourceType.equals(ResourceType.TOP_DIRECTORY.getCode())){
            return new ResultInfo<List<Permission>>(list);
        } else if (resourceType.equals(ResourceType.DIRECTORY.getCode())) {
            List<Permission> allPermissions = iPermissionService.getAllPermissions();
            for (Permission p : allPermissions) {
                if (p.getResourceType().equals(ResourceType.TOP_DIRECTORY.getCode())) {
                    list.add(p);
                }
            }
        } else if (resourceType.equals(ResourceType.MENU.getCode())) {
            List<Permission> allPermissions = iPermissionService.getAllPermissions();
            for (Permission p : allPermissions) {
                if (p.getResourceType().equals(ResourceType.DIRECTORY.getCode()) ||
                        p.getResourceType().equals(ResourceType.TOP_DIRECTORY.getCode())) {
                    list.add(p);
                }
            }
        } else if (resourceType.equals(ResourceType.BUTTON.getCode())) {
            List<Permission> allPermissions = iPermissionService.getAllPermissions();
            for (Permission p : allPermissions) {
                if (p.getResourceType().equals(ResourceType.MENU.getCode())) {
                    list.add(p);
                }
            }
        }
        return new ResultInfo<List<Permission>>(list);
    }

    @RequestMapping("/xtreeData")
    public @ResponseBody
    ResultInfo<List<PermissionInfo>> xtreeData() {
        return new ResultInfo<List<PermissionInfo>>(iPermissionService.allPermissionInfo());
    }

}