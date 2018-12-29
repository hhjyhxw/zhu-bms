package com.zhumeng.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhumeng.modules.sys.dto.UserInfo;
import com.zhumeng.modules.sys.entity.User;
import com.zhumeng.modules.sys.mapper.UserMapper;
import com.zhumeng.modules.sys.service.IUserService;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public UserInfo findUserInfo(String userName) {
        return this.baseMapper.findUserInfo(userName);
    }
}