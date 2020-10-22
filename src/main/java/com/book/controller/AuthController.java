package com.book.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.book.common.BaseController;
import com.book.common.CookieUtils;
import com.book.payload.LoginRequest;
import com.book.payload.Response;
import com.book.security.JwtTokenProvider;
import com.book.service.UserService;

@Controller
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController extends BaseController {

	protected Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	HttpSession httpSession;
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String login(Model model) {
		return "auth/login";
	}

	@PostMapping("/api/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,HttpServletResponse response,HttpServletRequest request) {
		
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			if(loginRequest.isRememberMe()==true) {
				// remember 30 days
				String cookieJwt = tokenProvider.generateTokenExp(authentication,30);
				CookieUtils.addCookieJwtRememberMe(response, "remember-me","Bearer "+cookieJwt, 2592000); // 30 days to seconds
			} else {
				CookieUtils.clear(response,"remember-me");
				String jwt = tokenProvider.generateTokenPrivate(authentication);
				httpSession.setAttribute("Authorization", "Bearer "+jwt);
			}
			return ResponseEntity.ok(new Response(null, "You are successfully logged in"));
		} catch (Exception e) {
			if (e instanceof BadCredentialsException) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new Response(null, "Your email or password incorrect!"));
			} else {
				logger.error("Exception : {}", ExceptionUtils.getStackTrace(e));
			}
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}