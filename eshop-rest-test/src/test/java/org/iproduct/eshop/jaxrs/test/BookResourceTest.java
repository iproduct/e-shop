package org.iproduct.eshop.jaxrs.test;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.ContextResolver;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.iproduct.eshop.jpa.entity.Book;
import org.iproduct.eshop.jpa.entity.Publisher;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;

public class BookResourceTest {

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/EShop/api/books";

    private static final int EXISTING_BOOK_INDEX = 1;
    private static final String TITLE_UPDATE = "Updated Title",
            AUTHOR_UPDATE = "Bruce Eckel";

    public static final Publisher[] SAMPLE_PUBLISHERS = {
        new Publisher("O'Reilly Media", "http://shop.oreilly.com/product/0636920033158.do"),
        new Publisher("Addison Wesley", "http://www.informit.com/about/index.aspx"),
        new Publisher("Test publisher", "http://testpublisheer.com")
    };

    public static final Book[] SAMPLE_BOOKS = {
        new Book("RESTful Web Services", "0596529260",
        new String[]{"Leonard Richardson", "Sam Ruby"},
        "Very good intro to RESTful services.",
        SAMPLE_PUBLISHERS[0],
        "https://images-na.ssl-images-amazon.com/images/I/51rwSlFnsHL._SX379_BO1,204,203,200_.jpg",
        "https://www.amazon.com/RESTful-Web-Services-Leonard-Richardson/dp/0596529260",
        55, 0),
        new Book("Building Microservices", "1491950358",
        new String[]{"Sam Newman"},
        "Microservice technologies are moving quickly. Author Sam Newman provides you with a firm grounding in the concepts while diving into current solutions for modeling, integrating, testing, deploying, and monitoring your own autonomous services. You will follow a fictional company throughout the book to learn how building a microservice architecture affects a single domain.",
        SAMPLE_PUBLISHERS[0],
        "https://images-na.ssl-images-amazon.com/images/I/5156gHBSxaL._SX379_BO1,204,203,200_.jpg",
        "http://shop.oreilly.com/product/0636920033158.do",
        47.49, 0),
        new Book("REST in Practice: Hypermedia and Systems Architecture", "978-0596805821",
        new String[]{"Leonard Richardson", "Sam Ruby"},
        "In this insightful book, three Soa experts provide a down-to-earth explanation of Rest and demonstrate how you can develop simple and elegant distributed hypermedia systems by applying the Web's guiding principles to common enterprise computing problems. You will learn techniques for implementing specific Web technologies and patterns to solve the needs of a typical company as it grows from modest beginnings to become a global enterprise.",
        SAMPLE_PUBLISHERS[0],
        "https://images-na.ssl-images-amazon.com/images/I/51z9S88jI6L._SX383_BO1,204,203,200_.jpg",
        "http://shop.oreilly.com/product/9780596805838.do",
        35.61, 0.1)
    };

    private static final Long NONEXISTING_BOOK_ID = 100000L;
    private static Book NONEXISTING_BOOK = new Book("REST API Design Rulebook", "978-1449310509",
            new String[]{"Leonard Richardson", "Sam Ruby"},
            "Very good intro to RESTful services.",
            SAMPLE_PUBLISHERS[0],
            "https://images-na.ssl-images-amazon.com/images/I/51rwSlFnsHL._SX379_BO1,204,203,200_.jpg",
            "https://www.amazon.com/RESTful-Web-Services-Leonard-Richardson/dp/0596529260",
            15.93, 0);

    private final List<Book> actualBooks = new ArrayList<>();
    private static Client client;
    private static WebTarget target;

    final Map<String, String> namespacePrefixMapper = new HashMap<String, String>();

    @BeforeClass
    public static void setUpClass() {
        final Map<String, String> namespacePrefixMapper = new HashMap<String, String>();
        namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");

        MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig()
                .setNamespacePrefixMapper(namespacePrefixMapper)
                .setNamespaceSeparator(':');

        final ContextResolver<MoxyJsonConfig> jsonConfigResolver = moxyJsonConfig.resolver();

        // Initialize base REST client and target
        URI uri = UriBuilder.fromUri(BASE_URI).build();
        Client client = ClientBuilder.newBuilder()
        // The line below that registers MOXy feature can be
        // omitted if FEATURE_AUTO_DISCOVERY_DISABLE is
        // not disabled.
        .register(MoxyJsonFeature.class)
        .register(jsonConfigResolver)
        .build();
//        ClientConfig config = new ClientConfig();
//        config.register(MoxyJsonFeature.class);
//        client = ClientBuilder.newClient(config);
        target = client.target(uri);
    }

    /**
     * Post sample books for the test
     */
    @Before
    public void setUp() {
        Arrays.asList(SAMPLE_BOOKS).stream()
                .forEach(book -> {
                    Entity entity = Entity.entity(book, javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
                    System.out.println(entity);
                    Book updatedBook = target.request(APPLICATION_JSON_TYPE)
                            .post(entity, Book.class);
                    if (updatedBook != null) {
                        actualBooks.add(updatedBook);
                    }
                });
    }

    /**
     * Remove all posted polls during the test
     */
    @After
    public void tearDown() {
        actualBooks.stream().forEach(book -> {
            try {
                target.path(book.getId().toString()).request().delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Test to see that we GET sample book successfully
     */
    @Test
    public void should_GetBook_whenBookExisits() {
        Book actualSampleBook = actualBooks.get(1);
        Book result = target.path(actualSampleBook.getId().toString())
                .request().get(Book.class);
        assertNotNull(result);
        assertEquals(actualSampleBook.getId(), result.getId());
        assertEquals(actualSampleBook.getTitle(), result.getTitle());
        assertEquals(actualSampleBook.getDescription(), result.getDescription());
        assertEquals(actualSampleBook.getIsbn(), result.getIsbn());
        assertEquals(actualSampleBook.getPrice(), result.getPrice(), 0.0001);
        assertEquals(actualSampleBook.getPublisher(), result.getPublisher());
        assertEquals(actualSampleBook.getPhotoUrl(), result.getPhotoUrl());
        assertEquals(actualSampleBook.getBookUrl(), result.getBookUrl());

//    assertReflectionEquals(actualSampleBook, result); //with Unitils
//        assertThat(actualSampleBook, reflectEquals(result)); //with Hamcrest 1.3 Utility Matchers
    }
}
