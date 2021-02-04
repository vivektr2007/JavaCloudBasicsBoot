package com.pixel.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pixel.blog.service.BookService;
import com.pixel.model.BookModel;

@Controller
public class BookController {

	@Autowired
	BookService bookService;

	@Autowired
	Environment env;

	@RequestMapping(value = "book.html", method = { RequestMethod.GET })
	public String bookPage(Model model) {

		String awsEndPoint = "https://" + env.getProperty("awsEndPoint");

		List<BookModel> listBookModel = bookService.getBookList();
		HashMap<String, List<BookModel>> hm = new HashMap<String, List<BookModel>>();
		Iterator<BookModel> itr = listBookModel.iterator();
		while (itr.hasNext()) {
			BookModel bModel = itr.next();
			if (hm.containsKey(bModel.getCatergory())) {
				hm.get(bModel.getCatergory()).add(bModel);
			} else {
				ArrayList<BookModel> al = new ArrayList<BookModel>();
				al.add(bModel);
				hm.put(bModel.getCatergory(), al);
			}

		}

		model.addAttribute("listBookModel", listBookModel);
		model.addAttribute("MapBookList", hm);
		model.addAttribute("awsEndPoint", awsEndPoint);

		return "bookView";
	}

	@RequestMapping(value = "addbook.html", method = { RequestMethod.GET })
	public String addBook(HttpServletRequest request, @RequestParam("file") List<MultipartFile> fileList, Model model) {

		String awsEndPoint = "https://" + env.getProperty("awsEndPoint");

		List<BookModel> listBookModel = bookService.getBookList();

		model.addAttribute("listBookModel", listBookModel);
		model.addAttribute("awsEndPoint", awsEndPoint);

		return "bookView";
	}

}
