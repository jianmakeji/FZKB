package com.jianma.fzkb.realm;

import java.util.Optional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.jianma.fzkb.model.User;
import com.jianma.fzkb.service.UserService;



public class UserRealm extends AuthorizingRealm{

	@Autowired
	@Qualifier(value = "userServiceImpl")
	private UserService userServiceImpl;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String)principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userServiceImpl.findRoles(username));
        authorizationInfo.setStringPermissions(userServiceImpl.findPermissions(username));
        
        return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String)token.getPrincipal();
		
        Optional<User> user = userServiceImpl.checkAuthc(username);

        if(user.isPresent()) {
        	
        	 if(user.get().getValid() == 1) {
                 throw new LockedAccountException(); //帐号锁定
             }
        	 
        	SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user.get().getEmail(), //用户
                    user.get().getPassword(), //密码
                    ByteSource.Util.bytes(user.get().getCredentialsSalt()),
                    this.getName()  //realm name
            );

        	Subject subject = SecurityUtils.getSubject();
        	subject.getSession().setAttribute("userId", user.get().getId());
        	subject.getSession().setAttribute("email", username);
        	subject.getSession().setAttribute("realname", user.get().getRealname());
            return authenticationInfo;
            
        }
        else{
        	throw new UnknownAccountException();//没找到帐�????
        }
       
	}

}
