package com.company.core.service.impl;

import com.company.core.biz.TblBtsUserMapBiz;
import com.company.core.constant.Constant;
import com.company.core.constant.SystemConstant;
import com.company.core.constant.UserConstant;
import com.company.core.domain.UserBO;
import com.company.core.entity.*;
import com.company.core.form.Pagination;
import com.company.core.mapper.TblBTSSysRoleDOMapper;
import com.company.core.mapper.TblBTSSysUsrDOMapper;
import com.company.core.mapper.TblBTSSysUsrRoleDOMapper;
import com.company.core.service.UserService;
import com.company.core.util.DateUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by APPLE on 15/12/27.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    TblBTSSysUsrDOMapper        tblBTSSysUsrDOMapper;

    @Autowired
    TblBTSSysUsrRoleDOMapper    tblBTSSysUsrRoleDOMapper;

    @Autowired
    TblBTSSysRoleDOMapper       tblBTSSysRoleDOMapper;
    @Autowired
    TblBtsUserMapBiz tblBtsUserMapBiz;

    

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserBO get(String username) {
        TblBTSSysUsrDOExample tblBTSSysUsrDOExample = new TblBTSSysUsrDOExample();
        tblBTSSysUsrDOExample.createCriteria().andUsrNameEqualTo(username);
        List<TblBTSSysUsrDO> tblBTSSysUsrDOList = new ArrayList<TblBTSSysUsrDO>();
        try {
            tblBTSSysUsrDOList = tblBTSSysUsrDOMapper.selectByExample(tblBTSSysUsrDOExample);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return convertUsrDOToUserBO(tblBTSSysUsrDOList.get(0));
    }

    public UserBO getById(String usrId) {
        TblBTSSysUsrDOExample tblBTSSysUsrDOExample = new TblBTSSysUsrDOExample();
        tblBTSSysUsrDOExample.createCriteria().andUsrIdEqualTo(usrId);
        List<TblBTSSysUsrDO> tblBTSSysUsrDOList = tblBTSSysUsrDOMapper
            .selectByExample(tblBTSSysUsrDOExample);
        return convertUsrDOToUserBO(tblBTSSysUsrDOList.get(0));
    }

    public Map updatePwd(UserBO userBO) {

        Map resultMap = new HashMap();

        if (userBO.getUsrPwd() != null && !userBO.getUsrPwd().isEmpty()) {
            TblBTSSysUsrDOExample tblBTSSysUsrDOExample = new TblBTSSysUsrDOExample();
            tblBTSSysUsrDOExample.createCriteria().andUsrIdEqualTo(userBO.getUsrId())
                .andUsrNameEqualTo(userBO.getUsrName()).andUsrPwdEqualTo(userBO.getUsrPwd());
            List<TblBTSSysUsrDO> tblBTSSysUsrDOList = tblBTSSysUsrDOMapper
                .selectByExample(tblBTSSysUsrDOExample);
            if (tblBTSSysUsrDOList == null || tblBTSSysUsrDOList.size() == 0) {
                resultMap.put("statusCode", 300);
                resultMap.put("message", "旧密码错误!");
                return resultMap;
            }
        }

        TblBTSSysUsrDO tblBTSSysUsrDO = new TblBTSSysUsrDO();
        tblBTSSysUsrDO.setUsrId(userBO.getUsrId());
        tblBTSSysUsrDO.setUsrPwd(userBO.getPass());
        tblBTSSysUsrDOMapper.updateByPrimaryKeySelective(tblBTSSysUsrDO);
        resultMap.put("statusCode", 200);
        resultMap.put("message", "操作成功!");
        return resultMap;
    }

    public Map addNewUsr(UserBO userBO) {
        Map resultMap = new HashMap();

        TblBTSSysUsrDO refSysUsrDO = new TblBTSSysUsrDO();
        refSysUsrDO.setUsrPwd(userBO.getUsrPwd());
        refSysUsrDO.setUsrName(userBO.getUsrName());
        refSysUsrDO.setUsrEmail(userBO.getUsrEmail());
        refSysUsrDO.setUsrId(userBO.getUsrId());
        refSysUsrDO.setUsrDisableTag(userBO.getUsrDisableTag());
        if (userBO.getUsrType() != null && !userBO.getUsrType().trim().equals("")) {
            refSysUsrDO.setUsrType(userBO.getUsrType());
            TblBTSSysRoleDOExample tblBTSSysRoleDOExample = new TblBTSSysRoleDOExample();
            tblBTSSysRoleDOExample.createCriteria().andRoleNameEqualTo(userBO.getUsrType());
            List<TblBTSSysRoleDO> roleDOList = tblBTSSysRoleDOMapper
                .selectByExample(tblBTSSysRoleDOExample);
            if (roleDOList != null && roleDOList.size() != 0) {
                try {
                    addAcctAuthority(roleDOList.get(0).getRoleId(), refSysUsrDO.getUsrId());
                } catch (DataIntegrityViolationException ex) {
                    logger.info("角色已分配:" + ex.getMessage());
                    resultMap.put("statusCode", 300);
                    resultMap.put("message", "角色已分配!");
                } catch (Exception ex) {
                    resultMap.put("statusCode", 300);
                    resultMap.put("message", "账号类型失败!");
                    return resultMap;
                }
            }
        }
        refSysUsrDO.setUsrRemark(userBO.getUsrRemark());
        refSysUsrDO.setUsrCreateDate(DateUtil.currentTimestamp());
        refSysUsrDO.setUsrUpdateDate(DateUtil.currentTimestamp());
        refSysUsrDO.setUsrCreateBy(userBO.getUsrCreateBy());
        refSysUsrDO.setUsrUpdateBy(userBO.getUsrUpdateBy());

        int rt = tblBTSSysUsrDOMapper.insert(refSysUsrDO);
        if (rt != 0) {
            resultMap.put("statusCode", 200);
            resultMap.put("message", "操作成功!");
        } else {
            resultMap.put("statusCode", 300);
            resultMap.put("message", "操作失败!");
            return resultMap;
        }
    
        //绑定登录ID跟用户  wwk
        if (StringUtils.isNotBlank(userBO.getUserCode())) {
            logger.info("进行商户关联绑定");
            // 添加角色关联
            TblBtsUsrMapDoKey tblBtsUsrMapDoKey = new TblBtsUsrMapDoKey();
            tblBtsUsrMapDoKey.setLoginSeq(userBO.getUsrId());
            tblBtsUsrMapDoKey.setLoginId(userBO.getUsrName());
            TblBtsUsrMapDo tblBtsUsrMapDo = tblBtsUserMapBiz.selectByPrimaryKey(tblBtsUsrMapDoKey);
            if(tblBtsUsrMapDo != null){
                return resultMap;
            }
            tblBtsUsrMapDo = new TblBtsUsrMapDo();
            tblBtsUsrMapDo.setLoginSeq(userBO.getUsrId());
            tblBtsUsrMapDo.setLoginId(userBO.getUsrName());
            tblBtsUsrMapDo.setUsrType(userBO.getUsrType());
            tblBtsUsrMapDo.setUserCode(userBO.getUserCode());
            tblBtsUsrMapDo.setUserName(userBO.getUserCodeName());
    
            tblBtsUsrMapDo.setCreateUser(userBO.getUsrCreateBy());
            tblBtsUsrMapDo.setCreateSource(SystemConstant.DEFAULT_SOURCE_CODE);
            tblBtsUsrMapDo.setCreateTime(DateUtil.getCurrentDateTime());
            tblBtsUsrMapDo.setModifyUser(userBO.getUsrCreateBy());
            tblBtsUsrMapDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
            tblBtsUsrMapDo.setModifyTime(DateUtil.getCurrentDateTime());
            tblBtsUsrMapDo.setLockVersion(String.valueOf(0));
            int a = tblBtsUserMapBiz.insert(tblBtsUsrMapDo);
            if (a <= 0) {
                resultMap.remove("statusCode");
                resultMap.remove("message");
                resultMap.put("statusCode", 300);
                resultMap.put("message", "添加用户失败-绑定用户");
            }
    
            //分配对应的权限
            if (UserConstant.USER_INST.equals(userBO.getUsrType())) {
                addAcctAuthority(Constant.INST_ROLE_AUTH_ID, userBO.getUsrId());
            } else if (UserConstant.USER_AGENT.equals(userBO.getUsrType())) {
                addAcctAuthority(Constant.AGENT_ROLE_AUTH_ID, userBO.getUsrId());
            }
        }
        
        return resultMap;
    }

    public Pagination<UserBO> getAllUsr(UserBO userBO) {
        List<UserBO> userList = new ArrayList<UserBO>();
        TblBTSSysUsrDOExample tblBTSSysUsrDOExample = new TblBTSSysUsrDOExample();
        tblBTSSysUsrDOExample.createCriteria();
        int count = tblBTSSysUsrDOMapper.countByExample(tblBTSSysUsrDOExample);
        Pagination pagination = new Pagination(count, userBO.getPageCurrent(),
            userBO.getPageSize());
        PageHelper.startPage(userBO.getPageCurrent(), userBO.getPageSize());
        List<TblBTSSysUsrDO> usrDOList = tblBTSSysUsrDOMapper
            .selectByExample(tblBTSSysUsrDOExample);
    
        UserBO user = new UserBO();
        TblBtsUsrMapDo userMapDo = new TblBtsUsrMapDo();
        TblBtsUsrMapDoKey userMapKey = new TblBtsUsrMapDoKey();
        for (TblBTSSysUsrDO usrDO : usrDOList) {
            user = new UserBO();
            userMapDo = new TblBtsUsrMapDo();
            userMapKey = new TblBtsUsrMapDoKey();
            user.setUsrId(usrDO.getUsrId());
            user.setUsrName(usrDO.getUsrName());
            user.setUsrPwd(usrDO.getUsrPwd());
            user.setUsrRemark(usrDO.getUsrRemark());
            if (usrDO.getUsrDisableTag().trim().equals("1")) {
                user.setUsrDisableTag("启用");

            } else {
                user.setUsrDisableTag("禁用");
            }
            user.setUsrEmail(usrDO.getUsrEmail());
    
            userMapKey.setLoginSeq(usrDO.getUsrId());
            userMapKey.setLoginId(usrDO.getUsrName());
            userMapDo = tblBtsUserMapBiz.selectByPrimaryKey(userMapKey);
            if(userMapDo != null){
                user.setUserCodeType(userMapDo.getUsrType());
                user.setUserCode(userMapDo.getUserCode());
                user.setUserCodeName(userMapDo.getUserName());
            }
            
            userList.add(user);
        }
        pagination.addResult(userList);
        return pagination;
    }

    public Pagination<UserBO> getTheUsr(UserBO userBO) {
    
        //按照账户查询
        TblBTSSysUsrDOExample tblBTSSysUsrDOExample = new TblBTSSysUsrDOExample();
        TblBTSSysUsrDOExample.Criteria cri = tblBTSSysUsrDOExample.createCriteria();
        if (!(userBO.getUsrName() == null || userBO.getUsrName().trim().equals(""))) {
//            cri.andUsrNameEqualTo(userBO.getUsrName());
            cri.andUsrNameLike("%" + userBO.getUsrName() + "%");
        }
        int count = tblBTSSysUsrDOMapper.countByExample(tblBTSSysUsrDOExample);
        Pagination pagination = new Pagination(count, userBO.getPageCurrent(),
            userBO.getPageSize());
        PageHelper.startPage(userBO.getPageCurrent(), userBO.getPageSize());
        List<TblBTSSysUsrDO> usrDOList = tblBTSSysUsrDOMapper
            .selectByExample(tblBTSSysUsrDOExample);
        
        List<UserBO> userBOList = new ArrayList<UserBO>();
        TblBtsUsrMapDo userMapDo = new TblBtsUsrMapDo();
        TblBtsUsrMapDoKey userMapKey = new TblBtsUsrMapDoKey();
        for (TblBTSSysUsrDO usrDO : usrDOList) {
            UserBO user = new UserBO();
            user.setUsrId(usrDO.getUsrId());
            user.setUsrName(usrDO.getUsrName());
            user.setUsrPwd(usrDO.getUsrPwd());
            user.setUsrRemark(usrDO.getUsrRemark());
            if (usrDO.getUsrDisableTag().trim().equals("1")) {
                user.setUsrDisableTag("启用");

            } else {
                user.setUsrDisableTag("禁用");
            }
    
            userMapKey.setLoginSeq(usrDO.getUsrId());
            userMapKey.setLoginId(usrDO.getUsrName());
            userMapDo = tblBtsUserMapBiz.selectByPrimaryKey(userMapKey);
            if(userMapDo != null){
                user.setUserCodeType(userMapDo.getUsrType());
                user.setUserCode(userMapDo.getUserCode());
                user.setUserCodeName(userMapDo.getUserName());
            }
            
            user.setUsrEmail(usrDO.getUsrEmail());
            userBOList.add(user);
        }
        pagination.addResult(userBOList);

        return pagination;

    }
    
    
    /**
     *
     * @param userBO
     * @return
     */
    public Pagination<UserBO> getUsrsByUserInfo(UserBO userBO) {
        
        //按照用户查
        TblBtsUsrMapDoExample tblBtsUsrMapDoExample = new TblBtsUsrMapDoExample();
        TblBtsUsrMapDoExample.Criteria criteria = tblBtsUsrMapDoExample.createCriteria();
        if(StringUtils.isNotBlank(userBO.getUserCode()) && StringUtils.isNotBlank(userBO.getUserCode().trim())){
            criteria.andUserCodeEqualTo(userBO.getUserCode().trim());
        }
        if(StringUtils.isNotBlank(userBO.getUserCodeName()) && StringUtils.isNotBlank(userBO.getUserCodeName().trim())){
            criteria.andUserNameLike("%" + userBO.getUserCodeName().trim() + "%");
        }
        long c = tblBtsUserMapBiz.countByExample(tblBtsUsrMapDoExample);
        Pagination pagination = new Pagination((int) c, userBO.getPageCurrent(),
                userBO.getPageSize());
        PageHelper.startPage(userBO.getPageCurrent(), userBO.getPageSize());
        
        List<TblBtsUsrMapDo> tblBtsUsrMapDoList = tblBtsUserMapBiz.selectByExample(tblBtsUsrMapDoExample);
        
        List<UserBO> userBOList = new ArrayList<UserBO>();
        UserBO user = new UserBO();
        TblBTSSysUsrDO tblBTSSysUsrDO = new TblBTSSysUsrDO();
        for (TblBtsUsrMapDo usermapdo : tblBtsUsrMapDoList) {
            user = new UserBO();
            tblBTSSysUsrDO = new TblBTSSysUsrDO();
            tblBTSSysUsrDO = tblBTSSysUsrDOMapper.selectByPrimaryKey(usermapdo.getLoginSeq());
            if(tblBTSSysUsrDO == null){
                continue;
            }
            user.setUsrId(tblBTSSysUsrDO.getUsrId());
            user.setUsrName(tblBTSSysUsrDO.getUsrName());
            user.setUsrPwd(tblBTSSysUsrDO.getUsrPwd());
            user.setUsrRemark(tblBTSSysUsrDO.getUsrRemark());
            if (tblBTSSysUsrDO.getUsrDisableTag().trim().equals("1")) {
                user.setUsrDisableTag("启用");
                
            } else {
                user.setUsrDisableTag("禁用");
            }
            user.setUsrEmail(tblBTSSysUsrDO.getUsrEmail());
    
            user.setUserCodeType(usermapdo.getUsrType());
            user.setUserCode(usermapdo.getUserCode());
            user.setUserCodeName(usermapdo.getUserName());
            
            userBOList.add(user);
        }
        pagination.addResult(userBOList);
        
        return pagination;
        
    }
    

    private UserBO convertUsrDOToUserBO(TblBTSSysUsrDO tblBTSSysUsrDO) {
        UserBO userBO = new UserBO();
        userBO.setUsrId(tblBTSSysUsrDO.getUsrId());
        userBO.setUsrName(tblBTSSysUsrDO.getUsrName());
        userBO.setUsrPwd(tblBTSSysUsrDO.getUsrPwd());
        userBO.setUsrDisableTag(tblBTSSysUsrDO.getUsrDisableTag());
    
        //wwk -
        userBO.setUsrType(tblBTSSysUsrDO.getUsrType());
        TblBtsUsrMapDoKey tblBtsUsrMapDoKey = new TblBtsUsrMapDoKey();
        tblBtsUsrMapDoKey.setLoginSeq(tblBTSSysUsrDO.getUsrId());
        tblBtsUsrMapDoKey.setLoginId(tblBTSSysUsrDO.getUsrName());
        TblBtsUsrMapDo tblBtsUsrMapDo = tblBtsUserMapBiz.selectByPrimaryKey(tblBtsUsrMapDoKey);
        if(tblBtsUsrMapDo != null){
            userBO.setUserCode(tblBtsUsrMapDo.getUserCode());
            userBO.setUserCodeType(tblBtsUsrMapDo.getUsrType());
            userBO.setUserCodeName(tblBtsUsrMapDo.getUserName());
        }
        return userBO;
    }

    public Map setAcctEnable(String usrId) throws Exception {
        Map resultMap = new HashMap();
        TblBTSSysUsrDOExample usrDOExample = new TblBTSSysUsrDOExample();
        usrDOExample.createCriteria().andUsrIdEqualTo(usrId);
        TblBTSSysUsrDO usrDO = new TblBTSSysUsrDO();
        usrDO.setUsrDisableTag("1");
        tblBTSSysUsrDOMapper.updateByExampleSelective(usrDO, usrDOExample);
        resultMap.put("statusCode", 200);
        resultMap.put("message", "操作成功!");

        return resultMap;

    }

    public Map setAcctDisable(String usrId) throws Exception {
        Map resultMap = new HashMap();
        TblBTSSysUsrDOExample usrDOExample = new TblBTSSysUsrDOExample();
        usrDOExample.createCriteria().andUsrIdEqualTo(usrId);
        TblBTSSysUsrDO usrDO = new TblBTSSysUsrDO();
        usrDO.setUsrDisableTag("0");
        tblBTSSysUsrDOMapper.updateByExampleSelective(usrDO, usrDOExample);
        resultMap.put("statusCode", 200);
        resultMap.put("message", "操作成功!");

        return resultMap;

    }

    public Map cancelAcctAuthority(String id, String uid) throws Exception {

        Map resultMap = new HashMap();
        TblBTSSysUsrRoleDOKey usrRoleDOKey = new TblBTSSysUsrRoleDOKey();
        usrRoleDOKey.setUsrId(uid);
        usrRoleDOKey.setRoleId(id);
        tblBTSSysUsrRoleDOMapper.deleteByPrimaryKey(usrRoleDOKey);
        resultMap.put("statusCode", 200);
        resultMap.put("message", "操作成功!");

        return resultMap;
    }

    public Map addAcctAuthority(String roleId, String userId) {
        Map resultMap = new HashMap();

        TblBTSSysRoleDOExample roleDOExample = new TblBTSSysRoleDOExample();
        TblBTSSysRoleDO roleDO = tblBTSSysRoleDOMapper.selectByPrimaryKey(roleId);

        TblBTSSysUsrRoleDO usrRoleDO = new TblBTSSysUsrRoleDO();
        usrRoleDO.setUsrId(userId);
        usrRoleDO.setRoleId(roleId);
        usrRoleDO.setUsrRoleRemark(roleDO.getRoleName());
        tblBTSSysUsrRoleDOMapper.insert(usrRoleDO);
        resultMap.put("statusCode", 200);
        resultMap.put("message", "操作成功!");

        return resultMap;
    }

    public TblBTSSysUsrDO getIdByName(String usr_name) {

        Map resultMap = new HashedMap();
        TblBTSSysUsrDOExample tblBTSSysUsrDOExample = new TblBTSSysUsrDOExample();
        tblBTSSysUsrDOExample.createCriteria().andUsrNameEqualTo(usr_name);
        List<TblBTSSysUsrDO> usrDOList = tblBTSSysUsrDOMapper
            .selectByExample(tblBTSSysUsrDOExample);
        resultMap.put("statusCode", 200);
        resultMap.put("message", "操作成功!");
        return usrDOList.get(0);
    }
}
