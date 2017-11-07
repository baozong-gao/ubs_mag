package com.company.core.form;

/**
 * Created by fireWorks on 2016/2/1.
 */
public class UserManageForm extends BaseForm {
    private String usrId;

    private String usrName;

    private String passRe;

    private String pass;

    private String usrRemark;

    private String usrDisableTag;

    private String usrEmail;

    public String getUsrid() {
        return usrId;
    }

    public void setUsrid(String usrId) {
        this.usrId = usrId;
    }

    public String getUsername() {
        return usrName;
    }

    public void setUsername(String usrName) {
        this.usrName = usrName;
    }

    public String getPassRe() {
        return passRe;
    }

    public void setPassRe(String passRe) {
        this.passRe = passRe;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUsrRemark() {
        return usrRemark;
    }

    public void setUsrRemark(String usrRemark) {
        this.usrRemark = usrRemark == null ? null : usrRemark.trim();
    }

    public String getUsrDisableTag() {
        return usrDisableTag;
    }

    public void setUsrDisableTag(String usrDisableTag) {
        this.usrDisableTag = usrDisableTag == null ? null : usrDisableTag.trim();
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail == null ? null : usrEmail.trim();
    }
}
