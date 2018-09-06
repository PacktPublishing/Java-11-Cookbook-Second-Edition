module sqldb.book.service{
	requires book.service;
	provides com.packt.spi.BookServiceProvider
		with com.packt.sqldb.SqlDbBookServiceProvider;
}