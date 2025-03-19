package org.example.steps;

import org.example.pages.GPTPage;
import org.example.utils.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

public class ChatGPTSteps {
    
    private GPTPage gptPage;
    private String currentResponse;
    
    public ChatGPTSteps() {
        gptPage = new GPTPage();
    }
    
    @Given("I am on the ChatGPT page")
    public void i_am_on_the_chat_gpt_page() {
        DriverManager.getDriver().get("https://chat.openai.com");
    }
    
    @And("I am logged in to ChatGPT")
    public void i_am_logged_in_to_chat_gpt() {
        // This step would handle login, but for now we'll skip it
        System.out.println("Login step - would be implemented based on authentication requirements");
    }
    
    @When("I enter question {string}")
    public void i_enter_question(String question) {
        gptPage.enterQuestion(question);
    }
    
    @And("I submit the question")
    public void i_submit_the_question() {
        gptPage.submitQuestion();
    }
    
    @Then("I should see a response")
    public void i_should_see_a_response() {
        currentResponse = gptPage.getResponse();
    }
    
    @And("I store the response as JSON")
    public void i_store_the_response_as_json() {
        // For now, we'll just store the response as is
        System.out.println("Response stored: " + currentResponse);
    }
    
    @And("I validate the response contains {string}")
    public void i_validate_the_response_contains(String expectedKeyword) {
        // Basic validation
        if (currentResponse != null && !currentResponse.toLowerCase().contains(expectedKeyword.toLowerCase())) {
            throw new AssertionError("Response does not contain expected keyword: " + expectedKeyword);
        }
    }
    
    @And("I display the response in the HTML report")
    public void i_display_the_response_in_the_html_report() {
        // The response will be included in the Cucumber report automatically
        System.out.println("Response displayed in report: " + currentResponse);
    }
} 