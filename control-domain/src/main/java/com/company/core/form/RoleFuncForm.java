package com.company.core.form;

import com.company.core.domain.FuncBO;

import java.util.List;

/**
 * Created by fireWorks on 2016/2/3.
 */
public class RoleFuncForm extends BaseForm{

    private String roleId;

    private String roleName;

    private String roleDisableTag;

    private String roleRemark;

    private String roleChecked;
    
    private  String oldRoleChecked;//存储 现有权限ids
    
    private List<FuncBO> allFuncBOList;
    
    public List<FuncBO> getAllFuncBOList() {
        return allFuncBOList;
    }
    
    public void setAllFuncBOList(List<FuncBO> allFuncBOList) {
        this.allFuncBOList = allFuncBOList;
    }
    
    public String getOldRoleChecked() {
        return oldRoleChecked;
    }
    
    public void setOldRoleChecked(String oldRoleChecked) {
        this.oldRoleChecked = oldRoleChecked == null ? null : oldRoleChecked.trim();
    }
    
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleDisableTag() {
        return roleDisableTag;
    }

    public void setRoleDisableTag(String roleDisableTag) {
        this.roleDisableTag = roleDisableTag == null ? null : roleDisableTag.trim();
    }

    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark == null ? null : roleRemark.trim();
    }

    public String getRoleChecked() {
        return roleChecked;
    }

    public void setRoleChecked(String roleChecked) {
        this.roleChecked = roleChecked == null ? null : roleChecked.trim();
    }
}
