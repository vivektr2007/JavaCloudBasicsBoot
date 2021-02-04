package com.pixel.blog.service;

import java.util.List;

import com.pixel.model.BookModel;

public interface BookService {

	public List<BookModel> getBookListbyCatergory(int categoryPk);

	public List<BookModel> getBookList();

}
