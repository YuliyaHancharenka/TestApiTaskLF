Feature: Book API Testing

    Scenario: Read all books
        Given I have access to the book API
        When I request all books
        Then I should get a list of books

    Scenario: Read a book by ID
        Given I have access to the book API
        When I request a book with ID 2
        Then I should get the book with ID 2

    Scenario: Create a new book
        Given I have access to the book API
        When I create a new book with name "Refactoring: Improving the Design of Existing Code", author "Martin Fowler", publication "Addison-Wesley Professional", category "Programming", pages 448, price 35.50
        Then I should see the book in the list

    Scenario: Update a book
        Given I have access to the book API
        When I update the book with ID 2 to name "Test Driven Development: By Example", author "Kent Beck", publication "Addison-Wesley Professional", category "Programming", pages 240, price 29.26
        Then the book should be updated

    Scenario: Delete a book
        Given I have access to the book API
        When I delete the book with ID 1
        Then the book should no longer exist
