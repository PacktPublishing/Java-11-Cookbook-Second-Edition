package com.packt.sqldb;

import com.packt.spi.BookServiceProvider;
import com.packt.sqldb.service.SqlDbBookService;
import com.packt.service.BookService;

public class SqlDbBookServiceProvider implements BookServiceProvider{

	@Override
	public BookService getBookService(){
		return new SqlDbBookService();
	}
}