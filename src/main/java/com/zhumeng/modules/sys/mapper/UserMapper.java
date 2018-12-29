package com.zhumeng.modules.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhumeng.modules.sys.dto.UserInfo;
import com.zhumeng.modules.sys.entity.User;


/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-16
 */
public interface UserMapper extends BaseMapper<User> {

    UserInfo findUserInfo(String userName);

}
