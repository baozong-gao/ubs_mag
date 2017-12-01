package com.company.core.service;

import com.company.core.entity.WzInfoDo;
import com.company.core.form.AccountUserForm;
import com.company.core.form.Pagination;

import java.util.List;

/**
 * @Author: weiwankun
 * @Date: 2017/11/9
 */

public interface AccountUserService {
    
    
    Pagination getAccountUserListPage(AccountUserForm accountUserForm);
}
