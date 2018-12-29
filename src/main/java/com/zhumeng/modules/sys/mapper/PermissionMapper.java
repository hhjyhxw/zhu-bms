package com.zhumeng.modules.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhumeng.modules.sys.dto.PermissionInfo;
import com.zhumeng.modules.sys.entity.Permission;


import java.util.List;

/**
 * <p>
 * 系统权限表 Mapper 接口
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-16
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<PermissionInfo> allPermissionInfo();

}
