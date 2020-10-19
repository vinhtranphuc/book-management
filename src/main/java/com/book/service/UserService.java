package com.book.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.book.common.BaseService;
import com.book.jpa.entity.Role;
import com.book.jpa.entity.RoleName;
import com.book.jpa.entity.User;
import com.book.jpa.repository.RoleRepository;
import com.book.jpa.repository.UserRepository;
import com.book.mybatis.mapper.UserMapper;
import com.book.mybatis.model.UserVO;

@Service
public class UserService extends BaseService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserMapper userMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	public Optional<Role> getRoleByName(RoleName roleName) {
		return roleRepository.findByName(roleName);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public UserVO getUserById(Long userId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("user_id",userId);
		UserVO user = userMapper.getUserById(param);
		return user;
	}
}
