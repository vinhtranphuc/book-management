package com.book.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.book.common.FieldMap;
import com.book.payload.Response;
import com.book.security.UserPrincipal;
import com.book.service.LibaryService;

@Controller
@RequestMapping(value = "/book")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookController {

	protected Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private LibaryService libaryService;
	
	@RequestMapping(value = {"","/"}, method = RequestMethod.GET)
	public String book(Model model,@RequestParam Map<String,Object> param, Authentication authentication) {
		List<FieldMap> bookList = libaryService.getBooks();
		model.addAttribute("bookList", bookList);
		return "/cms/book";
	}

	@RequestMapping(value = "/api/book-page-list", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Response> getBooks(@RequestParam Map<String,Object> params) {
		try {
			Map<String,Object> result = libaryService.getBooks(params);
			return ResponseEntity.ok().body(new Response(result));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@PostMapping("/api/create-book")
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Response> createBook(@Valid @RequestBody Map<String,Object> params, Authentication authentication) {
		try {
			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
			params.put("user_id", userPrincipal.getId());
			libaryService.createBook(params);
			return ResponseEntity.ok().body(new Response(null,"You're successfully create book."));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@PutMapping("/api/edit-book")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Response> editBook(@Valid @RequestBody Map<String,Object> params) {
		try {
			libaryService.editBook(params);
			return ResponseEntity.ok().body(new Response(null,"You're successfully edit book."));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@DeleteMapping("/api/delete-book")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Response> deleteBook(@RequestParam Map<String,Object> params) {
		try {
			libaryService.deleteBook(params);
			return ResponseEntity.ok().body(new Response(null,"You're successfully delete book."));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
