package com.book.mybatis.mapper;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Resource
public interface BookMapper {
	
	int selectBooksTotCnt(Map<String, Object> param);
	
	<T> List<T> selectBooks(Map<String, Object> params);

	void insertBook(Map<String, Object> params);

	void insertBooksAuthors(Map<String, Object> params);

	void deleteBooksAuthorsByBookId(Map<String, Object> params);

	void updateBook(Map<String, Object> params);

	void deleteBook(Map<String, Object> params);
}
