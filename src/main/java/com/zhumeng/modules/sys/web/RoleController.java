package com.zhumeng.modules.sys.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhumeng.annotation.SysLog;
import com.zhumeng.modules.sys.dto.ResultInfo;
import com.zhumeng.modules.sys.entity.Role;
import com.zhumeng.modules.sys.entity.User;
import com.zhumeng.modules.sys.service.IRoleService;
import com.zhumeng.modules.sys.service.IUserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-10
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IUserService iUserService;

    @RequestMapping("/*")
    public void toHtml() {

    }

    @RequestMapping("/selectListData")
    @ResponseBody
    public ResultInfo<List<Role>> selectListData(Role role){
        List<Role> list = iRoleService.selectList(new EntityWrapper<Role>(role));
        return new ResultInfo<List<Role>>(list);
    }

    @RequestMapping("/listData")
    @RequiresPermissions("role:view")
    public @ResponseBody
    ResultInfo<List<Role>> listData(Role role, Integer page, Integer limit) {
        EntityWrapper<Role> wrapper = new EntityWrapper<Role>(role);
        if (role != null && role.getRoleCode() != null) {
            wrapper.like("role_code", role.getRoleCode());
            role.setRoleCode(null);
        }
        if (role != null && role.getRoleName() != null) {
            wrapper.like("role_name", role.getRoleName());
            role.setRoleName(null);
        }
        Page<Role> pageObj = iRoleService.selectPage(new Page<Role>(page, limit), wrapper);
        return new ResultInfo<List<Role>>(pageObj.getRecords(), pageObj.getTotal());
    }

    @SysLog("保存角色操作")
    @RequestMapping("/save")
    @RequiresPermissions(value = {"role:add", "role:edit"}, logical = Logical.OR)
    public @ResponseBody
    ResultInfo<Boolean> save(Role role) {
        return new ResultInfo<Boolean>(iRoleService.saveRole(role));
    }

    @SysLog("删除角色操作")
    @RequestMapping("/delBatch")
    @RequiresPermissions("role:del")
    public @ResponseBody
    ResultInfo<Boolean> delBatch(Integer[] idArr) {
        EntityWrapper<User> wrapper = new EntityWrapper<User>();
        wrapper.in("role_id", idArr);
        List<User> userList = iUserService.selectList(wrapper);
        if(userList!=null && userList.size()>0){
            return new ResultInfo<Boolean>("用户拥有角色不能删除！");
        }
        return new ResultInfo<Boolean>(iRoleService.deleteBatchIds(Arrays.asList(idArr)));
    }

}