package org.example.pages;

import org.example.utils.BrowserUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GPTPage extends BasePage {
    
    @FindBy(css = "#prompt-textarea")
    private WebElement messageInput;
    
    @FindBy(css = "button[data-testid='send-button']")
    private WebElement submitButton;
    
    @FindBy(css = ".markdown-content")
    private WebElement responseContent;
    
    @FindBy(css = "button[data-testid='login-button']")
    private WebElement loginButton;
    
    @FindBy(css = "input[name='email']")
    private WebElement emailInput;
    
    @FindBy(css = "input[name='password']")
    private WebElement passwordInput;
    
    @FindBy(css = "button[type='submit']")
    private WebElement continueButton;
    
    public void enterQuestion(String question) {
        BrowserUtils.waitForElementVisible(messageInput);
        messageInput.clear();
        messageInput.sendKeys(question);
    }
    
    public void submitQuestion() {
        BrowserUtils.waitForElementClickable(submitButton);
        submitButton.click();
    }
    
    public String getResponse() {
        BrowserUtils.waitForElementVisible(responseContent);
        return responseContent.getText();
    }
    
    public void login(String username, String password) {
        try {
            Thread.sleep(2000); // Wait for page to load
            BrowserUtils.waitForElementClickable(loginButton);
            loginButton.click();
            
            BrowserUtils.waitForElementVisible(emailInput);
            emailInput.sendKeys(username);
            continueButton.click();
            
            BrowserUtils.waitForElementVisible(passwordInput);
            passwordInput.sendKeys(password);
            continueButton.click();
            
            Thread.sleep(3000); // Wait for login to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
