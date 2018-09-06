package com.packt.mongodb;

import com.packt.spi.BookServiceProvider;
import com.packt.mongodb.service.MongoDbBookService;
import com.packt.service.BookService;

public class MongoDbBookServiceProvider implements BookServiceProvider{

	@Override
	public BookService getBookService(){
		return new MongoDbBookService();
	}
}