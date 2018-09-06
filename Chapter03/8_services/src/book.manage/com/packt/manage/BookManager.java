package com.packt.manage;

import com.packt.service.BookService;
import com.packt.model.Book;

public class BookManager{
	public static void main(String[] args){
		BookService service = BookService.getInstance();
		System.out.println(service.getClass());
		Book book = new Book("1", "Title", "Author");
		service.create(book);
		service.read("1");
		service.update(book);
		service.delete("1");
	}
}