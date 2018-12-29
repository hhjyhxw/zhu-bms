package com.zhumeng.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhumeng.modules.sys.dto.MenuInfo;
import com.zhumeng.modules.sys.dto.PermissionInfo;
import com.zhumeng.modules.sys.entity.Permission;

import java.util.List;

/**
 * <p>
 * 系统权限表 服务类
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-16
 */
public interface IPermissionService extends IService<Permission> {

     List<Permission> getAllPermissions();

     Boolean savePermission(Permission permission);

     Boolean delBatchPermission(List<Integer> ids);

     List<PermissionInfo> allPermissionInfo();

     List<MenuInfo> getMenuPermissions(String code);

     List<Permission> getTopDirectoryPermissions();

}