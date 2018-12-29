package com.zhumeng.modules.sys.dto;

;

import com.zhumeng.modules.sys.entity.Permission;
import com.zhumeng.modules.sys.entity.Role;

import java.io.Serializable;
import java.util.List;

public class RoleInfo extends Role implements Serializable {

    private List<Permission> permissions;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}