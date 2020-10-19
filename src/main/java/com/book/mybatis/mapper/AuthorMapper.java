package com.book.mybatis.mapper;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Resource
public interface AuthorMapper {
	
	<T> List<T> selectAuthorsByBookId(Map<String, Object> params);

	<T> List<T> selectAuthors();

	int selectAuthorsTotCnt(Map<String, Object> params);

	<T> List<T> selectPageAuthors(Map<String, Object> params);

	void insertAuthor(Map<String, Object> params);

	void updateAuthor(Map<String, Object> params);
	
	void deleteBooksAuthors(Map<String, Object> params);
	
	void deleteAuthor(Map<String, Object> params);
}
