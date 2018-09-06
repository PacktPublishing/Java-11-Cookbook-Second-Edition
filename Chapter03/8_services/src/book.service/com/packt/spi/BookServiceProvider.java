package com.packt.spi;

import com.packt.service.BookService;

public interface BookServiceProvider{
	public BookService getBookService();
}