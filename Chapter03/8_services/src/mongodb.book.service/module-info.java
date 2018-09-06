module mongodb.book.service{
	requires book.service;
	provides com.packt.spi.BookServiceProvider
		with com.packt.mongodb.MongoDbBookServiceProvider;
}