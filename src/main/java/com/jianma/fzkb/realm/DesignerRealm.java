package com.jianma.fzkb.realm;

import java.util.Optional;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.jianma.fzkb.model.Designer;
import com.jianma.fzkb.service.DesignerService;

public class DesignerRealm extends AuthorizingRealm {

	@Autowired
	@Qualifier(value = "designerServiceImpl")
	private DesignerService designerServiceImpl;
	
	private Designer successDesigner;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) { //授权
		
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException { //认证
		
		String username = (String)token.getPrincipal();
		
		Optional<Designer> designer = designerServiceImpl.findDesignerByUsername(username);
        
        if(!designer.isPresent()) {
            throw new UnknownAccountException();//没找到帐�?
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		designer.get().getUsername(), //用户�?
                designer.get().getPassword(), //密码
                ByteSource.Util.bytes(designer.get().getCredentialsSalt()),
                getName()  //realm name
        );
        
        this.setSuccessDesigner(designer.orElse(null));
        
        return authenticationInfo;
	}

	public Designer getSuccessDesigner() {
		return successDesigner;
	}

	public void setSuccessDesigner(Designer successDesigner) {
		this.successDesigner = successDesigner;
	}

	

	
}
