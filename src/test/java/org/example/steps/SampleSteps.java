package org.example.steps;

import org.example.utils.DriverManager;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SampleSteps {
    
    @Given("I am on the homepage")
    public void iAmOnTheHomepage() {
        DriverManager.getDriver().get("https://www.google.com");
    }

    @When("I perform some action")
    public void iPerformSomeAction() {
        String title = DriverManager.getDriver().getTitle();
        System.out.println("Page title: " + title);
        Assert.assertTrue("Page title should contain 'Google'", 
            title.contains("Google"));
    }

    @Then("I should see the expected result")
    public void iShouldSeeTheExpectedResult() {
        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);
        Assert.assertTrue("URL should contain 'google'", 
            currentUrl.contains("google"));
    }
} 