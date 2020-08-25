package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private final JavascriptExecutor js;
    @FindBy(id = "logout-button")
    private WebElement logoutButton;
    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;
    @FindBy(id = "nav-files-tab")
    private WebElement filesTab;
    @FindBy(id = "nav-credentials-tab")
    private WebElement credsTab;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        js = (JavascriptExecutor) webDriver;
    }

    public void logoutUser() {
        js.executeScript("arguments[0].click();", logoutButton);
    }

    public void goToNotesTab() {
        js.executeScript("arguments[0].click();", notesTab);
    }

    public void goToFilesTab() {
        js.executeScript("arguments[0].click();", filesTab);
    }

    public void goToCredsTab() {
        js.executeScript("arguments[0].click();", credsTab);
    }
}
