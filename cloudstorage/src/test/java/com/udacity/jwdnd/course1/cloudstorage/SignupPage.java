package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "success-msg")
    private WebElement loginLinkSuccessText;

    @FindBy(id = "error-msg")
    private WebElement loginErrorText;

    @FindBy(id = "login-link")
    private WebElement loginLink;

    private final JavascriptExecutor js;

    public SignupPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        js = (JavascriptExecutor) webDriver;
    }

    public void signup(String firstName, String lastName, String username, String password) {
//        this.firstNameField.sendKeys(firstName);
//        this.lastNameField.sendKeys(lastName);
//        this.usernameField.sendKeys(username);
//        this.passwordField.sendKeys(password);
//        this.submitButton.click();
        js.executeScript("arguments[0].value='"+ firstName +"';", firstNameField);
        js.executeScript("arguments[0].value='"+ lastName +"';", lastNameField);
        js.executeScript("arguments[0].value='"+ username +"';", usernameField);
        js.executeScript("arguments[0].value='"+ password +"';", passwordField);
        js.executeScript("arguments[0].click();", submitButton);
    }

    public String getLoginLinkSuccessText(){
        return loginLinkSuccessText.getText();
    }

    public String getLoginErrorText(){
        return loginErrorText.getText();
    }

    public void clickOnLoginLink(){
//        loginLink.click();
        js.executeScript("arguments[0].click();", loginLink);

    }
}
