package fi.terasvirta.chapter_2.Bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.terasvirta.chapter_2.Bookstore.domain.Book;
import fi.terasvirta.chapter_2.Bookstore.domain.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner bookstoreApp(BookRepository repository) {
		return (args) -> {
			log.info("Save some Books");
			repository.save(new Book("Luvattu maa", "Barack Obama", 2020, "123456789", 32.90));
			repository.save(new Book("Kuinkas sitten kävikään", "Tove Janson", 1965, "ABC543987", 22.90));
			repository.save(new Book("Kuka lohduttaisi Nyytiä", "Tove Janson", 1971, "ABC87651234", 24.90));
			repository.save(new Book("Taru Sormusten Herrasta", "J.R.R. Tolkien", 1978, "PPR432143", 64.90));
			
			log.info("Fetch all books");
			for (Book book : repository.findAll()) {
				log.info(book.toString());
			}
		};
	}

}
