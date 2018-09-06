package com.packt.mongodb.service;

import com.packt.service.BookService;
import com.packt.model.Book;

public class MongoDbBookService extends BookService{
	public void create(Book book){
		System.out.println("Mongodb Create book ... " + book.title);
	}
	public Book read(String id){
		System.out.println("Mongodb Reading book ... " + id);
		return new Book(id, "Title", "Author");
	}
	public void update(Book book){
		System.out.println("Mongodb Updating book ... " + book.title);
	}
	public void delete(String id){
		System.out.println("Mongodb Deleting ... " + id);
	}
}