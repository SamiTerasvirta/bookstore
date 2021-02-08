package fi.terasvirta.chapter_2.Bookstore.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.terasvirta.chapter_2.Bookstore.domain.Book;
import fi.terasvirta.chapter_2.Bookstore.domain.BookRepository;


@Controller
@RequestMapping("/books/")
public class BookController {

	private final BookRepository bookRepository;
	
	@Autowired
	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@GetMapping("list")
	public String showListForm(Model model) {
		model.addAttribute("books", bookRepository.findAll());
		return "index";
	}
	
	@GetMapping("newbook")
	public String newBookForm(Book book) {
		return "add-book";
	}
	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid book Id: " + id));
		model.addAttribute("book", book);
		return "update-book";
	}
	
	@GetMapping("delete/{id}")
	public String deleteBook(@PathVariable("id") Long id, Model model) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid book Id: " + id));
		bookRepository.delete(book);
		model.addAttribute("books", bookRepository.findAll());
		return "index";
	}
	
	@PostMapping("add")
	public String addBook (@Valid Book book, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-book";
		}
		
		bookRepository.save(book);
		return "redirect:list";
	}
	
	@PostMapping("update/{id}")
	public String updateBook(@PathVariable("id") Long id, @Valid Book book, BindingResult result, Model model) {
		if (result.hasErrors()) {
			book.setId(id);
			return "update-book";
		}
		
		bookRepository.save(book);
		model.addAttribute("books", bookRepository.findAll());
		return "index";
	}
	
	
}