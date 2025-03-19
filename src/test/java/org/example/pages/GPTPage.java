package org.example.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GPTPage extends BasePage {
    
    @FindBy(css = "textarea[placeholder='Send a message']")
    private WebElement messageInput;
    
    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;
    
    @FindBy(css = ".markdown-content")
    private WebElement responseContent;
    
    public void enterQuestion(String question) {
        messageInput.clear();
        messageInput.sendKeys(question);
    }
    
    public void submitQuestion() {
        submitButton.click();
    }
    
    public String getResponse() {
        return responseContent.getText();
    }
}
