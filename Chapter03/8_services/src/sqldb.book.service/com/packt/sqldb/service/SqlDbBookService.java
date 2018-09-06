package com.packt.sqldb.service;

import com.packt.service.BookService;
import com.packt.model.Book;

public class SqlDbBookService extends BookService{
	public void create(Book book){
		System.out.println("Sqldb Create book ... " + book.title);
	}
	public Book read(String id){
		System.out.println("Sqldb Reading book ... " + id);
		return new Book(id, "Title", "Author");
	}
	public void update(Book book){
		System.out.println("Sqldb Updating book ... " + book.title);
	}
	public void delete(String id){
		System.out.println("Sqldb Deleting ... " + id);
	}
}