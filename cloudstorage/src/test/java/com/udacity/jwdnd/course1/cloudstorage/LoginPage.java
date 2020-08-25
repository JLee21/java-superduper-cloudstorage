package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Write a Selenium test that verifies that the home page is not accessible without logging in.
 *
 * Write a Selenium test that signs up a new user, logs that user in, verifies that they can access the home page,
 * then logs out and verifies that the home page is no longer accessible.
 */

public class LoginPage {
    private final JavascriptExecutor js;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        js = (JavascriptExecutor) webDriver;
    }

    public void loginUser(String username, String password) {
        js.executeScript("arguments[0].value='"+ username +"';", usernameField);
        js.executeScript("arguments[0].value='"+ password +"';", passwordField);
        js.executeScript("arguments[0].click();", submitButton);
    }



}
