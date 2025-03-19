@gpt
Feature: ChatGPT Question and Answer Functionality
  As a user
  I want to ask questions to ChatGPT
  So that I can get intelligent responses

  Background:
    Given I am on the ChatGPT page
    And I am logged in to ChatGPT

  Scenario Outline: Ask questions and validate responses
    When I enter question "<question>"
    And I submit the question
    Then I should see a response
    And I store the response as JSON
    And I validate the response contains "<expected_keyword>"
    And I display the response in the HTML report

    Examples:
      | question                    | expected_keyword |
      | What is Selenium WebDriver? | automation      |
      | Explain Cucumber BDD       | behavior        |
      | What is Page Object Model? | design pattern  | 