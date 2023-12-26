package com.bookstore.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bookstore.demo.entity.Book;
import com.bookstore.demo.service.BookService;

import io.swagger.annotations.ApiOperation;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
	@Autowired
	private BookService bookService;

	@GetMapping
	@ApiOperation(value = "Get a list of all books")
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	@GetMapping("/{id}")
	 @ApiOperation(value = "Get a book by ID")
	public Book getBookById(@PathVariable Long id) {
		return bookService.getBookById(id);
	}

	@PostMapping
	@ApiOperation(value = "Add a new book")
	public Book addBook(@RequestBody Book book) {
		return bookService.addBook(book);
	}

	@PutMapping("/{id}")
	  @ApiOperation(value = "Update an existing book")
	public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
		return bookService.updateBook(id, updatedBook);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a book by ID")
	public void deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
	}
}
