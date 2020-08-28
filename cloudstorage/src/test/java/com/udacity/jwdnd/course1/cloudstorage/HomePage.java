package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {
    private final JavascriptExecutor js;
    private final WebDriverWait wait;

    /**
     * Navigation
     */
    @FindBy(id = "logout-button")
    private WebElement logoutButton;
    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;
    @FindBy(id = "nav-files-tab")
    private WebElement filesTab;
    @FindBy(id = "nav-credentials-tab")
    private WebElement credsTab;

    /**
     * Notes
     */
    @FindBy(id = "add-note")
    private WebElement addNote;
    @FindBy(id = "edit-note")
    private WebElement editNote;
    @FindBy(id = "delete-note")
    private WebElement deleteNote;
    @FindBy(id = "note-table")
    private WebElement noteTable;
    @FindBy(id = "note-title")
    private WebElement noteTitle;
    @FindBy(id = "note-description")
    private WebElement noteDescription;
    @FindBy(id = "save-note")
    private WebElement saveNote;

    /**
     * Credentials
     */
    @FindBy(id = "add-credential")
    private WebElement addCred;
    @FindBy(id = "save-credential")
    private WebElement saveCred;
    @FindBy(id = "edit-credential")
    private WebElement editCred;
    @FindBy(id = "credential-url")
    private WebElement credUrl;
    @FindBy(id = "credential-username")
    private WebElement credUsername;
    @FindBy(id = "credential-password")
    private WebElement credPassword;



    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        wait = new WebDriverWait(webDriver, 1);
        js = (JavascriptExecutor) webDriver;
    }

    public void logoutUser() {
        js.executeScript("arguments[0].click();", logoutButton);
    }

    /**
     * Tab Navigation
     */
    public void goToNotesTab() {
        js.executeScript("arguments[0].click();", notesTab);
        wait.until(webDriver -> webDriver.findElement(By.id("note-table")));
    }

    public void goToCredsTab() {
        js.executeScript("arguments[0].click();", credsTab);
    }

    /**
     * Notes
     */
    public void addNote(String title, String description) {
        js.executeScript("arguments[0].click();", addNote);
        js.executeScript("arguments[0].value='" + title + "';", noteTitle);
        js.executeScript("arguments[0].value='" + description + "';", noteDescription);
        js.executeScript("arguments[0].click();", saveNote);
    }

    public void editNote(String title, String description) {
        WebElement noteTable = wait.until(webDriver -> webDriver.findElement(By.id("note-table")));
        WebElement row = noteTable.findElement(By.tagName("td"));
        WebElement editButton = row.findElement(By.id("edit-note"));
        js.executeScript("arguments[0].click();", editButton);
        js.executeScript("arguments[0].value='" + title + "';", noteTitle);
        js.executeScript("arguments[0].value='" + description + "';", noteDescription);
        js.executeScript("arguments[0].click();", saveNote);
    }

    public void deleteNote() {
        WebElement noteTable = wait.until(webDriver -> webDriver.findElement(By.id("note-table")));
        WebElement row = noteTable.findElement(By.tagName("td"));
        WebElement deleteButton = row.findElement(By.id("delete-note"));
        js.executeScript("arguments[0].click();", deleteButton);
    }

    public Note getFirstNote() {
        WebElement noteTable = wait.until(webDriver -> webDriver.findElement(By.id("note-table")));
        List<WebElement> tdList = noteTable.findElements(By.tagName("td"));
        if (tdList.size() == 0) return new Note();
        return new Note(tdList.get(1).getText(), tdList.get(2).getText(), 1);
    }

    /**
     * Credentials
     */
    public void addCred(String url, String username, String password) {
        js.executeScript("arguments[0].click();", addCred);
        js.executeScript("arguments[0].value='" + url + "';", credUrl);
        js.executeScript("arguments[0].value='" + username + "';", credUsername);
        js.executeScript("arguments[0].value='" + password + "';", credPassword);
        js.executeScript("arguments[0].click();", saveCred);
    }

    public Credential getFirstCred() {
        WebElement noteTable = wait.until(webDriver -> webDriver.findElement(By.id("credential-table")));
        List<WebElement> tdList = noteTable.findElements(By.tagName("td"));
        if (tdList.size() == 0) return new Credential();
        return new Credential(tdList.get(2).getText(), tdList.get(3).getText(), "key", tdList.get(1).getText(), 1);
    }

    public void editCred(String url, String username, String password) {
        WebElement noteTable = wait.until(webDriver -> webDriver.findElement(By.id("credential-table")));
        WebElement row = noteTable.findElement(By.tagName("td"));
        WebElement editButton = row.findElement(By.id("edit-cred"));
        try{
            js.executeScript("arguments[0].click();", editButton);
            js.executeScript("arguments[0].value='" + url + "';", credUrl);
            js.executeScript("arguments[0].value='" + username + "';", credUsername);
            js.executeScript("arguments[0].value='" + password + "';", credPassword);
            js.executeScript("arguments[0].click();", saveCred);
        }catch (Exception err){
            System.out.println(err.toString());
        }
    }

    public void deleteCred() {
        WebElement noteTable = wait.until(webDriver -> webDriver.findElement(By.id("credential-table")));
        WebElement row = noteTable.findElement(By.tagName("td"));
        WebElement deleteButton = row.findElement(By.id("delete-credential"));
        js.executeScript("arguments[0].click();", deleteButton);
    }


}
