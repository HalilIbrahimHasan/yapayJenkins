package org.example.steps;

import org.example.pages.GPTPage;
import org.example.utils.DriverManager;
import org.example.utils.ConfigReader;
import org.junit.Assert;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

public class ChatGPTSteps {
    
    private GPTPage gptPage;
    private String currentResponse;
    private JsonObject jsonResponse;
    private static final Gson gson = new Gson();
    
    public ChatGPTSteps() {
        gptPage = new GPTPage();
    }
    
    @Given("I am on the ChatGPT page")
    public void i_am_on_the_chat_gpt_page() {
        DriverManager.getDriver().get("https://chat.openai.com");
    }
    
    @And("I am logged in to ChatGPT")
    public void i_am_logged_in_to_chat_gpt() {
        String username = ConfigReader.getProperty("chatgpt.username");
        String password = ConfigReader.getProperty("chatgpt.password");
        
        if (username == null || password == null) {
            throw new RuntimeException("ChatGPT credentials not found in configuration");
        }
        
        gptPage.login(username, password);
    }
    
    @When("I enter question {string}")
    public void i_enter_question(String question) {
        try {
            Thread.sleep(2000); // Wait for page to be fully loaded
            gptPage.enterQuestion(question);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @And("I submit the question")
    public void i_submit_the_question() {
        gptPage.submitQuestion();
    }
    
    @Then("I should see a response")
    public void i_should_see_a_response() {
        currentResponse = gptPage.getResponse();
        Assert.assertFalse("Response should not be empty", currentResponse.isEmpty());
    }
    
    @And("I store the response as JSON")
    public void i_store_the_response_as_json() {
        JsonObject responseObj = new JsonObject();
        responseObj.addProperty("response", currentResponse);
        jsonResponse = responseObj;
    }
    
    @And("I validate the response contains {string}")
    public void i_validate_the_response_contains(String expectedKeyword) {
        Assert.assertTrue(
            String.format("Response should contain '%s'", expectedKeyword),
            currentResponse.toLowerCase().contains(expectedKeyword.toLowerCase())
        );
    }
    
    @And("I display the response in the HTML report")
    public void i_display_the_response_in_the_html_report() {
        System.out.println("Response JSON: " + jsonResponse.toString());
    }
} 