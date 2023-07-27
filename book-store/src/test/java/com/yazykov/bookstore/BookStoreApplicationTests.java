package com.yazykov.bookstore;

import com.yazykov.bookstore.dto.BookDto;
import com.yazykov.bookstore.dto.GroupedBook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class BookStoreApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void createNewBooksAndGet() {
		BookDto book1 = new BookDto(null, "Spring in action(4th)", "Craig Walls", "Base book for java dev");
		BookDto book2 = new BookDto(null, "Microservices patterns", "Chris Richardson", "Nice book for backend architect");
		BookDto book3 = new BookDto(null, "Cloud native spring in action", "Thomas Vitale", "Perfect book for 15-factor app building");

		BookDto expBook1 = webTestClient
				.post()
				.uri("/store/v1/books")
				.bodyValue(book1)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(BookDto.class).value(Assertions::assertNotNull)
				.returnResult().getResponseBody();
		BookDto expBook2 = webTestClient
				.post()
				.uri("/store/v1/books")
				.bodyValue(book2)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(BookDto.class).value(Assertions::assertNotNull)
				.returnResult().getResponseBody();
		BookDto expBook3 = webTestClient
				.post()
				.uri("/store/v1/books")
				.bodyValue(book3)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(BookDto.class).value(Assertions::assertNotNull)
				.returnResult().getResponseBody();
		Assertions.assertEquals(book1.author(), expBook1.author());
		Assertions.assertEquals(book2.title(), expBook2.title());
		Assertions.assertEquals(book3.description(), expBook3.description());

		webTestClient.get()
				.uri("/store/v1/books")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(List.class).value(books -> {
					Assertions.assertNotNull(books);
					Assertions.assertEquals(8, books.size());
				});

		webTestClient.get()
				.uri("/store/v1/books/authors-group")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(GroupedBook.class).value(books -> {
					Assertions.assertNotNull(books);
					Assertions.assertTrue(books.booksAuthorGrouped().containsKey("Craig Walls"));
					Assertions.assertTrue(books.booksAuthorGrouped().containsKey("Chris Richardson"));
					Assertions.assertTrue(books.booksAuthorGrouped().containsKey("Thomas Vitale"));
				});

		webTestClient.get()
				.uri("/store/v1/books/filter?char=t")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(List.class).value(books -> {
					Assertions.assertNotNull(books);
					Assertions.assertEquals(4, books.size());
				});
	}

}
