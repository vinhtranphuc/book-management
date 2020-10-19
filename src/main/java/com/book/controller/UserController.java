package com.book.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.exception.ResourceNotFoundException;
import com.book.jpa.entity.User;
import com.book.jpa.repository.UserRepository;
import com.book.mybatis.model.UserVO;
import com.book.payload.UserProfile;
import com.book.security.UserPrincipal;
import com.book.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public UserVO getCurrentUser(Authentication authentication) {
    	UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        return userService.getUserById(currentUser.getId());
    }

    @GetMapping("/{email}")
    public UserProfile getUserProfile(@PathVariable(value = "email") String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        UserProfile userProfile = new UserProfile(user.getId(), user.getEmail(), user.getFirstName()+" "+user.getLastName());
        return userProfile;
    }
}
