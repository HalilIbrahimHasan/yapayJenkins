package org.example.steps;

import org.example.utils.DriverManager;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SampleSteps {
    
    @Given("I am on the homepage")
    public void iAmOnTheHomepage() {
        DriverManager.getDriver().get("https://chatgpt.com/");
    }

    @When("I perform some action")
    public void iPerformSomeAction() {

    }

    @Then("I should see the expected result")
    public void iShouldSeeTheExpectedResult() {

    }
} 