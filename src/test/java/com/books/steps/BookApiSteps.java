package com.books.steps;

import com.books.models.BookRequest;
import com.books.models.BookResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import com.books.util.ConfigUtil;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class BookApiSteps {

    private Response response;

    @Given("I have access to the book API")
    public void i_have_access_to_the_book_api() {
        RestAssured.baseURI = ConfigUtil.getProperty("base.url");
    }

    @When("I request all books")
    public void i_request_all_books() {
        response = given().auth().basic(ConfigUtil.getProperty("api.username"), ConfigUtil.getProperty("api.password"))
                .get("/books");
    }

    @Then("I should get a list of books")
    public void i_should_get_a_list_of_books() {
        if (response.statusCode() != 200) {
            fail("Expected status code 200, but got: " + response.statusCode() + "\nResponse body: " + response.getBody().asString());
        }
        assertNotNull("Book list should not be null", response.jsonPath().getList(""));
    }

    @When("I request a book with ID {int}")
    public void i_request_a_book_with_id(Integer id) {
        response = given().auth().basic(ConfigUtil.getProperty("api.username"), ConfigUtil.getProperty("api.password"))
                .get("/books/" + id);
    }

    @Then("I should get the book with ID {int}")
    public void i_should_get_the_book_with_id(Integer id) {
        if (response.statusCode() != 200) {
            fail("Expected status code 200, but got: " + response.statusCode() + "\nResponse body: " + response.getBody().asString());
        }
        BookResponse book = response.as(BookResponse.class);
        assertEquals("Book ID does not match", java.util.Optional.ofNullable(id), book.getId());
    }

    @When("I create a new book with name {string}, author {string}, publication {string}, category {string}, pages {int}, price {double}")
    public void i_create_a_new_book(String name, String author, String publication, String category, int pages, double price) {
        BookRequest newBook = new BookRequest(name, author, publication, category, pages, price);

        response = given()
                .auth().basic(ConfigUtil.getProperty("api.username"),  ConfigUtil.getProperty("api.password"))
                .header("Content-Type", "application/json")
                .body(newBook)
                .when()
                .post("/books");
    }

    @Then("I should see the book in the list")
    public void i_should_see_the_book_in_the_list() {
        assertEquals(201, response.statusCode());
        Integer id = response.jsonPath().getInt("id");
        Response getAllResponse = given().auth().basic(ConfigUtil.getProperty("api.username"), ConfigUtil.getProperty("api.password"))
                .get("/books");
        assertTrue(getAllResponse.jsonPath().getList("id").contains(id));
    }

    @When("I update the book with ID {int} to name {string}, author {string}, publication {string}, category {string}, pages {int}, price {double}")
    public void i_update_the_book_with_id(Integer id, String name, String author, String publication, String category, int pages, double price) {
        BookRequest updatedBook = new BookRequest(name, author, publication, category, pages, price);

        response = given()
                .auth().basic(ConfigUtil.getProperty("api.username"), ConfigUtil.getProperty("api.password"))
                .header("Content-Type", "application/json")
                .body(updatedBook)
                .when()
                .put("/books/" + id);
    }

    @Then("the book should be updated")
    public void the_book_should_be_updated() {
        assertEquals(200, response.statusCode());
    }

    @When("I delete the book with ID {int}")
    public void i_delete_the_book_with_id(Integer id) {
        response = given().auth().basic(ConfigUtil.getProperty("api.username"), ConfigUtil.getProperty("api.password"))
                .delete("/books/" + id);
    }

    @Then("the book should no longer exist")
    public void the_book_should_no_longer_exist() {
        assertEquals(204, response.statusCode());
        Response getAllResponse = given().auth().basic(ConfigUtil.getProperty("api.username"), ConfigUtil.getProperty("api.password"))
                .get("/books");
        assertFalse(getAllResponse.jsonPath().getList("id").contains(1));
    }
}
