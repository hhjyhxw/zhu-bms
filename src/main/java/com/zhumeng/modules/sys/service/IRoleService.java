package com.zhumeng.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhumeng.modules.sys.entity.Role;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-16
 */
public interface IRoleService extends IService<Role> {

    Boolean saveRole(Role role);

}