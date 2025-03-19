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
    
    @FindBy(id = "username")
    private WebElement usernameInput;
    
    @FindBy(id = "password")
    private WebElement passwordInput;
    
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
        BrowserUtils.waitForElementClickable(loginButton);
        loginButton.click();
        
        BrowserUtils.waitForElementVisible(usernameInput);
        usernameInput.sendKeys(username);
        
        BrowserUtils.waitForElementVisible(passwordInput);
        passwordInput.sendKeys(password);
        
        loginButton.click();
    }
}
