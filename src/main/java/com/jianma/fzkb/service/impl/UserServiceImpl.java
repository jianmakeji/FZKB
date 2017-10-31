package com.jianma.fzkb.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jianma.fzkb.dao.UserDao;
import com.jianma.fzkb.model.Role;
import com.jianma.fzkb.model.User;
import com.jianma.fzkb.model.UserPageModel;
import com.jianma.fzkb.model.UserRole;
import com.jianma.fzkb.service.UserService;
import com.jianma.fzkb.util.PasswordHelper;
import com.jianma.fzkb.util.ResponseCodeUtil;


@Service
@Component
@Qualifier(value = "userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier(value = "userDaoImpl")
	private UserDao userDaoImpl;
	
	@Override
	public int createUser(User user) {
		try {
			Optional<User> optUser = userDaoImpl.findByEmail(user.getEmail());

			if (optUser.isPresent()) {
				return ResponseCodeUtil.UESR_CREATE_EXIST;
			} else {
				PasswordHelper.encryptAppPassword(user);
				Set<UserRole> userRoles = new HashSet<>();
				UserRole userRole = new UserRole();
				userRole.setUser(user);
				Role role = new Role();
				role.setId(1);
				userRole.setRole(role);
				userRoles.add(userRole);
				user.setUserRoles(userRoles);
				userDaoImpl.createUser(user);

				return ResponseCodeUtil.UESR_OPERATION_SUCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseCodeUtil.UESR_OPERATION_FAILURE;
		}

	}

	@Override
	public int updateUser(User user) {
		try {
			userDaoImpl.updateUser(user);
			return ResponseCodeUtil.UESR_OPERATION_SUCESS;
		} catch (Exception e) {
			return ResponseCodeUtil.UESR_OPERATION_FAILURE;
		}

	}

	@Override
	public int deleteUser(Long userId) {
		try {
			userDaoImpl.deleteUser(userId);
			return ResponseCodeUtil.UESR_OPERATION_SUCESS;
		} catch (Exception e) {
			return ResponseCodeUtil.UESR_OPERATION_FAILURE;
		}
	}

	@Override
	public int correlationRoles(Long userId, Long... roleIds) {
		try {
			userDaoImpl.correlationRoles(userId, roleIds);
			return ResponseCodeUtil.UESR_OPERATION_SUCESS;
		} catch (Exception e) {
			return ResponseCodeUtil.UESR_OPERATION_FAILURE;
		}

	}

	@Override
	public int uncorrelationRoles(Long userId, Long... roleIds) {
		try {
			userDaoImpl.uncorrelationRoles(userId, roleIds);
			return ResponseCodeUtil.UESR_OPERATION_SUCESS;
		} catch (Exception e) {
			return ResponseCodeUtil.UESR_OPERATION_FAILURE;
		}

	}

	@Override
	public Optional<User> findOne(Long userId) {
		return userDaoImpl.findOne(userId);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userDaoImpl.findByEmail(email);
	}

	@Override
	public Set<String> findRoles(String username) {

		return userDaoImpl.findRoles(username);
	}

	@Override
	public Set<String> findPermissions(String username) {

		return userDaoImpl.findPermissions(username);
	}


	@Override
	public int updatePwd(String email, String password,String oldSlot) {
		try {

			User user = new User();
			user.setPassword(password);
			user.setEmail(email);
			PasswordHelper.encryptAppPassword(user);

			userDaoImpl.updatePwd(email, user.getPassword(), oldSlot, user.getSlot());
			return ResponseCodeUtil.UESR_OPERATION_SUCESS;

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseCodeUtil.UESR_OPERATION_FAILURE;
		}
	}
	
	@Override
	public int resetLoginUserPwd(String password) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String email = subject.getSession().getAttribute("email").toString();
			
			User user = new User();
			user.setPassword(password);
			user.setEmail(email);
			PasswordHelper.encryptAppPassword(user);

			userDaoImpl.resetLoginUserPwd(email, user.getPassword(), user.getSlot());
			return ResponseCodeUtil.UESR_OPERATION_SUCESS;

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseCodeUtil.UESR_OPERATION_FAILURE;
		}
	}

	@Override
	public Optional<User> checkAuthc(String email) {
		// TODO Auto-generated method stub
		return userDaoImpl.checkAuthc(email);
	}

	@Override
	public UserPageModel getUserByPage(int offset, int limit) {
		List<User> list = userDaoImpl.findUserListByPage(offset, limit);
		int count = userDaoImpl.getCountUser();
		UserPageModel userPageModel = new UserPageModel();
		userPageModel.setCount(count);
		userPageModel.setList(list);
		return userPageModel;
	}

	@Override
	public int updateValidSign(String email, int validValue) {
		try {
			userDaoImpl.updateValidSign(email, validValue);
			return ResponseCodeUtil.UESR_OPERATION_SUCESS;
		} catch (Exception e) {
			return ResponseCodeUtil.UESR_OPERATION_FAILURE;
		}
	}

}
