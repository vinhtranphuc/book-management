package com.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.book.jpa.repository.UserRepository;
import com.book.mybatis.mapper.AuthorMapper;
import com.book.mybatis.mapper.BookMapper;
import com.book.mybatis.mapper.UserMapper;
import com.book.service.LibaryService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookManagementApplicationTest {
	
	@Autowired
    private DataSource dataSource;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookMapper bookMapper;
    @Resource
	private AuthorMapper authorMapper;
    @Resource
   	private UserMapper userMapper;
	@Autowired
	private LibaryService libaryService;
	
	@Test
    public void allComponentAreNotNull() {
		Assertions.assertThat(dataSource).isNotNull();
        Assertions.assertThat(entityManager).isNotNull();
        Assertions.assertThat(userRepository).isNotNull();
        Assertions.assertThat(bookMapper).isNotNull();
        Assertions.assertThat(authorMapper).isNotNull();
        Assertions.assertThat(userMapper).isNotNull();
        Assertions.assertThat(libaryService).isNotNull();
    }
	
	@Test
	public void testCreateAuthor() {
		
		Map<String,Object> author = new HashMap<String,Object>();
		author.put("avatar", "base64 avatar");
		author.put("description", "description author");
		author.put("fullName", "Test Author 1");
		
		authorMapper.insertAuthor(author);
		
		List<Map<String,Object>> authors = authorMapper.selectAuthors();
		@SuppressWarnings("unchecked")
		Map<String,Object> newAuthor = (Map<String, Object>) authorMapper.selectAuthors().get(authors.size()-1);
        Assertions.assertThat(newAuthor.get("full_name")).isEqualTo("Test Author 1");
	}
	
	@Test
	public void testCreateBook() {
		
		Map<String,Object> book = new HashMap<String,Object>();
		book.put("description", "Book description 1");
		book.put("enabled", 1);
		book.put("image", "Book base 64 image1");
		book.put("title", "Book Title test 1");
		book.put("user_id", 1);
		
		List<Integer> authorIds = new ArrayList<Integer>();
		authorIds.add(1);
		authorIds.add(2);
		book.put("authors", authorIds);
		
		libaryService.createBook(book);
		
		List<Map<String,Object>> books = bookMapper.selectBooks(new HashMap<String,Object>());
		Map<String,Object> newBook = (Map<String, Object>) books.get(books.size()-1);
        Assertions.assertThat(newBook.get("title")).isEqualTo("Book Title test 1");
	}
}
