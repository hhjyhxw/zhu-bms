package com.zhumeng.modules.sys.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhumeng.annotation.SysLog;
import com.zhumeng.common.StringUtils;
import com.zhumeng.common.util.PasswordEncoder;
import com.zhumeng.config.redis.RedisService;
import com.zhumeng.modules.sys.dto.ResultInfo;
import com.zhumeng.modules.sys.dto.UserInfo;
import com.zhumeng.modules.sys.entity.User;
import com.zhumeng.modules.sys.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-10
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private IUserService iUserService;
    @Autowired
    private RedisService redisService;

    @RequestMapping("/*")
    public void toHtml(){

    }

    @RequestMapping("/unlock")
    @ResponseBody
    public ResultInfo<Boolean> unlock(@RequestParam("password") String password){
        UserInfo userInfo = this.getUserInfo();
        SimpleHash simpleHash = new SimpleHash("md5", password, userInfo.getCredentialsSalt(), 2);
        if (simpleHash.toString().equals(userInfo.getPassWord())){
            return new ResultInfo<Boolean>(true);
        }

        return new ResultInfo<Boolean>(false);
    }

    @RequestMapping("/listData")
    @RequiresPermissions("user:view")
    public @ResponseBody
    ResultInfo<List<User>> listData(User user, Integer page, Integer limit){
        redisService.set("sss","哈哈哈哈哈哈哈哈");
        EntityWrapper<User> wrapper = new EntityWrapper<User>(user);
        if(user!=null&&user.getUserName()!=null){
            wrapper.like("user_name", user.getUserName());
            user.setUserName(null);
        }
        if(user!=null&&user.getName()!=null){
            wrapper.like("name",user.getName());
            user.setName(null);
        }
        Page<User> pageObj = iUserService.selectPage(new Page<User>(page,limit), wrapper);
        return new ResultInfo<List<User>>(pageObj.getRecords(), pageObj.getTotal());
    }

    @SysLog("添加用户操作")
    @RequestMapping("/add")
    @RequiresPermissions("user:add")
    public @ResponseBody
    ResultInfo<Boolean> add(User user){
        Map<String, String> map = PasswordEncoder.enCodePassWord(user.getUserName(), user.getPassWord());
        user.setSalt(map.get(PasswordEncoder.SALT));
        user.setPassWord(map.get(PasswordEncoder.PASSWORD));
        boolean b = iUserService.insert(user);
        return new ResultInfo<Boolean>(b);
    }

    @SysLog("删除用户操作")
    @RequestMapping("/delBatch")
    @RequiresPermissions("user:del")
    public @ResponseBody
    ResultInfo<Boolean> delBatch(Integer[] idArr){
        boolean b = iUserService.deleteBatchIds(Arrays.asList(idArr));
        return new ResultInfo<Boolean>(b);
    }

    @SysLog("修改用户操作")
    @RequestMapping("/edit")
    @RequiresPermissions("user:edit")
    public @ResponseBody
    ResultInfo<Boolean> edit(User user){
        User us = iUserService.selectById(user.getId());
        us.setName(user.getName());
        us.setRoleId(user.getRoleId());
        us.setState(user.getState());
        boolean b = iUserService.updateById(us);
        return new ResultInfo<Boolean>(b);
    }

    @SysLog("本人修改用户操作")
    @RequestMapping("/userEdit")
    public @ResponseBody
    ResultInfo<Boolean> userEdit(User user){
        UserInfo userInfo = this.getUserInfo();
        User us = iUserService.selectById(userInfo.getId());
        if(!StringUtils.isEmpty(user.getName())){
            us.setName(user.getName());
        }
        if(!StringUtils.isEmpty(user.getPassWord())){
            Map<String, String> map = PasswordEncoder.enCodePassWord(us.getUserName(),user.getPassWord());
            us.setSalt(map.get(PasswordEncoder.SALT));
            us.setPassWord(map.get(PasswordEncoder.PASSWORD));
        }
        boolean b = iUserService.updateById(us);
        String value = (String) redisService.get("sss");
        System.out.println("value========="+value);
        return new ResultInfo<Boolean>(b);
    }

    @RequestMapping("/centerDate")
    public @ResponseBody
    ResultInfo<UserInfo> centerDate(){
        UserInfo userInfo = this.getUserInfo();
        BeanUtils.copyProperties(iUserService.selectById(userInfo.getId()),userInfo);
        return new ResultInfo<UserInfo>(userInfo);
    }

    @RequestMapping("/count")
    public @ResponseBody
    ResultInfo<Integer> count(){
        return new ResultInfo<Integer>(iUserService.selectCount(new EntityWrapper<User>()));
    }

}