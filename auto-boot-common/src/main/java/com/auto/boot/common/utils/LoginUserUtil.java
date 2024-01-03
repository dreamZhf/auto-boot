package com.auto.boot.common.utils;

import cn.aos.isigning.autograph.autographcommon.enums.RealAuthStateEnum;
import cn.aos.isigning.autograph.autographcommon.enums.UserIdentityTypeEnum;
import cn.aos.isigning.autograph.autographcommon.model.dto.TokenInfoDTO;
import cn.aos.isigning.autograph.autographcommon.model.vo.LoginInfoVO;
import cn.aos.isigning.autograph.autographcommon.model.vo.UserInfoCacheVo;
import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 登录用户工具类
 *
 * @author zhaohaifan
 */
public class LoginUserUtil {

    private static final TransmittableThreadLocal<LoginInfoVO> USER_THREAD_LOCAL =
            new TransmittableThreadLocal<>();

    /**
     * 添加当前登录用户方法  在拦截器方法执行前调用设置获取用户
     * @param loginInfoVO 登录信息
     */
    public static void add(LoginInfoVO loginInfoVO){
        USER_THREAD_LOCAL.set(loginInfoVO);
    }

    /**
     * 获取 LoginInfoVO
     *
     * @return 返回 LoginInfoVO
     */
    public static LoginInfoVO getLoginInfo() {
        return USER_THREAD_LOCAL.get();
    }

    /**
     * 获取 LoginInfoVO
     *
     * @return 返回 LoginInfoVO
     */
    public static TokenInfoDTO getTokenInfo() {
        LoginInfoVO loginInfo = getLoginInfo();
        return loginInfo == null ? null : loginInfo.getTokenInfoDTO();
    }

    /**
     * 获取当前登录用户方法
     */
    public static UserInfoCacheVo getCurrentUser(){
        LoginInfoVO loginInfo = getLoginInfo();
        return loginInfo == null ? null : loginInfo.getUserInfoCacheVo();
    }

    /**
     * 获取登录用户的USERID
     *
     * @return 返回 userId
     */
    public static Long getCurrentUserId() {
        UserInfoCacheVo user = getCurrentUser();
        return user == null ? null : Long.parseLong(user.getUserId());
    }

    /**
     * 获取当前身份的企业ID
     *
     * @return 返回企业id
     */
    public static Long getEnterpriseId() {
        UserInfoCacheVo user = getCurrentUser();
        if(user == null || user.getEnterpriseId() == null){
            return null;
        }
        return Long.valueOf(user.getEnterpriseId());
    }


    /**
     * 删除当前登录用户方法  在拦截器方法执行后 移除当前用户对象
     */
    public static void remove(){
        USER_THREAD_LOCAL.remove();
    }

    /**
     * 没有bearer前缀的token
     *
     * @return 获取 token
     */
    public static String getToken() {
        UserInfoCacheVo currentUser = getCurrentUser();
        return currentUser == null ? null : currentUser.getToken();
    }

    /**
     * 获取当前登录用户的用户名称, 未实名时返回昵称、已实名时返回真实姓名
     *
     * @return 返回用户名称
     */
    public static String getUserName(){
        UserInfoCacheVo currentUser = getCurrentUser();
        if(currentUser == null){
            return null;
        }
        if(RealAuthStateEnum.YES.getState().equals(currentUser.getAuthFlag())) {
            return currentUser.getRealCertName();
        }else {
            return currentUser.getNickName();
        }
    }

    /**
     * 获取当前登录用户的id或者企业id
     *
     * @return 返回id
     */
    public static Long getLoginIdentityId(){
        UserInfoCacheVo currentUser = getCurrentUser();
        if(currentUser == null){
            return null;
        }
        String identityId;
        if(UserIdentityTypeEnum.personnel.getType().equals(currentUser.getIdentityType())){
            identityId = currentUser.getUserId();
        }else{
            identityId = currentUser.getEnterpriseId();
        }
        return Long.parseLong(identityId);
    }
}
