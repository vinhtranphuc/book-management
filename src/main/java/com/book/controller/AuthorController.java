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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.payload.Response;
import com.book.service.LibaryService;

@RestController
@RequestMapping(value = "/author")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthorController {

	protected Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private LibaryService libaryService;

	@RequestMapping(value = "/api/author-list", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public <T> ResponseEntity<Response> getAuthors(@RequestParam Map<String,Object> params) {
		try {
			List<T> authors = libaryService.getAuthors();
			return ResponseEntity.ok().body(new Response(authors));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@RequestMapping(value = "/api/author-page-list", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public <T> ResponseEntity<Response> getPageAuthors(@RequestParam Map<String,Object> params) {
		try {
			Map<String,Object> result = libaryService.getPageAuthors(params);
			return ResponseEntity.ok().body(new Response(result));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PostMapping("/api/create-author")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> createAuthor(@Valid @RequestBody Map<String,Object> params) {
		try {
			libaryService.createAuthor(params);
			return ResponseEntity.ok().body(new Response(null,"You're successfully create author."));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PutMapping("/api/edit-author")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> editAuthor(@Valid @RequestBody Map<String,Object> params) {
		try {
			libaryService.editAuthor(params);
			return ResponseEntity.ok().body(new Response(null,"You're successfully edit author."));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@DeleteMapping("/api/delete-author")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> deleteAuthor(@RequestParam Map<String,Object> params) {
		try {
			libaryService.deleteAuthor(params);
			return ResponseEntity.ok().body(new Response(null,"You're successfully delete author."));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
