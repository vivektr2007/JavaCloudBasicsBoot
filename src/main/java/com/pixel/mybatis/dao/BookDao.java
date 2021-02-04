package com.pixel.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pixel.model.BookModel;

@Mapper
public interface BookDao {

	@Select("select bookid,bookpath,thumbpath,bookname,version,category,orderid from book_info where category=#{categoryId} order by orderid")
	public List<BookModel> getBookListbyCatergory(@Param("categoryId") int categoryId);

	@Select("select bookid,bookpath,thumbpath,bookname,version,category,orderid from book_info")
	public List<BookModel> getBookList();

}
