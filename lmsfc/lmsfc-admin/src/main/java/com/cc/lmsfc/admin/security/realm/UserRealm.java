package com.cc.lmsfc.admin.security.realm;

import com.cc.lmsfc.common.model.security.User;
import com.cc.lmsfc.common.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tomchen on 15-3-24.
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.err.print("=======doGetAuthorizationInfo========");
        String username = (String)principals.getPrimaryPrincipal();

        //todo doGetAuthorizationInfo
        if(username.equals("admin")){
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            Set<String> sets = new HashSet<>();
            sets.add("all:*");
            sets.add("article:*");
            sets.add("articleEle:*");
            sets.add("filter:*");
            sets.add("filterRule:*");
            sets.add("filterDetail:*");
            sets.add("task:*");
            sets.add("batchTask:*");
            sets.add("deploy:*");
            authorizationInfo.setStringPermissions(sets);
            return authorizationInfo;
        }
        return null;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.err.print("=======doGetAuthenticationInfo========");

        String userName = (String) token.getPrincipal();

        User loginUser = userService.getUserByUserName(userName,true);
        if(loginUser == null){
            throw new UnknownAccountException();//没找到帐号
        }

        if(loginUser.getState() == 1){
            throw new LockedAccountException();//locked
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                loginUser.getUserName(), //用户名
                loginUser.getPassword(), //密码
                ByteSource.Util.bytes(loginUser.getSalt()),//salt
                getName()  //realm name
        );
        this.setSession("loginUser",loginUser);
        return authenticationInfo;
    }

    private void setSession(Object key, Object value){
        Subject currentUser = SecurityUtils.getSubject();
        if(null != currentUser){
            Session session = currentUser.getSession();
            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if(null != session){
                session.setAttribute(key, value);
            }
        }
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
