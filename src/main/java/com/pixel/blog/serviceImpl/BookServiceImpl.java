package com.pixel.blog.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixel.blog.service.BookService;
import com.pixel.model.BookModel;
import com.pixel.mybatis.dao.BookDao;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	BookDao bookDao;
	
	public List<BookModel> getBookListbyCatergory(int categoryPk){
		return bookDao.getBookListbyCatergory(categoryPk);
	}
	
	public List<BookModel> getBookList(){
		return bookDao.getBookList();
	}

}
