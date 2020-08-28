package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    public String baseURL;
    @LocalServerPort
    private int port;
    private WebDriver driver;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        baseURL = baseURL = "http://localhost:" + port;
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testUserSignUp() {
        /**
         * Sign up a new user, logs that user in, verifies that they can
         * access the home page, then logs out and verifies that the home
         * page is no longer accessible.
         */
        String username = "testUserSignUp";
        String password = "password";

        // Signup
        driver.get(baseURL + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("firstname", "lastname", username, password);
        Assertions.assertEquals("You successfully signed up! Please continue to the login page.", signupPage.getLoginLinkSuccessText());

        // Verify user can now login and access Home page
        signupPage.clickOnLoginLink();

        // Login the user
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginUser(username, password);
        Assertions.assertEquals("Home", driver.getTitle());

        // Logout user
        HomePage homePage = new HomePage(driver);
        homePage.logoutUser();

        // Try to access Home page
        driver.get(baseURL + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void testUnauthorizedUser() {
        /**
         * Write a test that verifies that an unauthorized user can
         * only access the login and signup pages.
         */

        // Can access login page
        driver.get(baseURL + "/login");
        Assertions.assertEquals("Login", driver.getTitle());

        // Can access signup page
        driver.get(baseURL + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        // Cannot access home page
        driver.get(baseURL + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void testUserSignUpFailsOnDuplicateUserName() throws InterruptedException {
        /**
         * Verify a user can only sign up if their username is unique.
         */
        String username = "username2";
        String password = "password";

        driver.get(baseURL + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("firstname", "lastname", username, password);
        signupPage.signup("firstname", "lastname", username, password);

        Assertions.assertEquals("The username already exists.", signupPage.getLoginErrorText());
    }

    @Test
    public void testNoteCreateViewDelete() throws InterruptedException {
        /**
         * Setup: Signup, Login, Go to Home
         */
        String username = "testNoteCreateViewDelete";
        String password = "password";
        HomePage homePage = new HomePage(driver);

        // Signup
        driver.get(baseURL + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("firstname", "lastname", username, password);

        // Verify user can now login and access Home page
        signupPage.clickOnLoginLink();

        // Login the user
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginUser(username, password);

        /**
         * Write a test that creates a note, and verifies it is displayed.
         */
        homePage.goToNotesTab();
        homePage.addNote("title", "description");
        homePage.goToNotesTab();
        Thread.sleep(1000);
        Note note = homePage.getFirstNote();
        Assertions.assertEquals("title", note.getNoteTitle());
        Assertions.assertEquals("description", note.getNoteDescription());


        /**
         * Write a test that edits an existing note and verifies
         * that the changes are displayed.
         */
        homePage.goToNotesTab();
        homePage.editNote("newTitle", "newDescription");
        homePage.goToNotesTab();
        Thread.sleep(1000);
        Note editedNote = homePage.getFirstNote();
        Assertions.assertEquals("newTitle", editedNote.getNoteTitle());
        Assertions.assertEquals("newDescription", editedNote.getNoteDescription());


        /**
         * Write a test that deletes a note and verifies that
         * the note is no longer displayed.
         */
        homePage.goToNotesTab();
        homePage.deleteNote();
        homePage.goToNotesTab();
        Thread.sleep(1000);
        Note deletedNote = homePage.getFirstNote();
        Assertions.assertNull(deletedNote.getNoteTitle());
        Assertions.assertNull(deletedNote.getNoteDescription());
    }


    @Test
    public void testViewEditDeleteCredentials() throws InterruptedException {
        /**
         * Setup: Signup, Login, Go to Home
         */
        String url = "myUrl.com";
        String username = "username";
        String password = "password";
        HomePage homePage = new HomePage(driver);
        EncryptionService encryptionService = new EncryptionService();

        // Signup
        driver.get(baseURL + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("firstname", "lastname", username, password);

        // Verify user can now login and access Home page
        signupPage.clickOnLoginLink();

        // Login the user
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginUser(username, password);

        /**
         * Write a test that creates a set of credentials,
         * verifies that they are displayed,
         * and verifies that the displayed password is encrypted.
         */
        homePage.goToCredsTab();
        homePage.addCred(url, username, password);
        homePage.goToCredsTab();
        Thread.sleep(1000);
        Credential credential = homePage.getFirstCred();
        Assertions.assertEquals(url, credential.getUrl());
        Assertions.assertEquals(username, credential.getUsername());
        Assertions.assertNotEquals(password, credential.getPassword());

        /**
         * Write a test that views an existing set of credentials,
         * verifies that the viewable password is unencrypted,
         * edits the credentials, and verifies that the changes are displayed.
         */
        driver.get(baseURL + "/home");
        homePage.goToCredsTab();
        Thread.sleep(1000);
        homePage.editCred("newURL", "newUsername", "newPassword");
        homePage.goToCredsTab();
        Thread.sleep(1000);
        Credential credentialEdited = homePage.getFirstCred();
        Assertions.assertEquals("newURL", credentialEdited.getUrl());
        Assertions.assertEquals("newUsername", credentialEdited.getUsername());
        Assertions.assertNotEquals("newPassword", credentialEdited.getPassword());

        /**
         * Write a test that deletes an existing set of credentials
         * and verifies that the credentials are no longer displayed.
         */
        homePage.goToCredsTab();
        homePage.deleteCred();
        homePage.goToCredsTab();
        Thread.sleep(1000);
        Credential deletedCred = homePage.getFirstCred();
        Assertions.assertNull(deletedCred.getUrl());
        Assertions.assertNull(deletedCred.getUsername());
        Assertions.assertNull(deletedCred.getPassword());
    }

}
