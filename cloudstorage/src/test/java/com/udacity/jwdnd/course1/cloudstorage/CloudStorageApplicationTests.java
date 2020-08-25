package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
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
    public void testUserSignUp() throws InterruptedException {
        /**
         * Sign up a new user, logs that user in, verifies that they can
         * access the home page, then logs out and verifies that the home
         * page is no longer accessible.
         */
        String username = "testUserSignUp";
        String password = "password";

        WebDriverWait waiter = new WebDriverWait(driver, 5);

        // Signup
        driver.get(baseURL + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("firstname", "lastname", username, password);
        // Wait...
//        Thread.sleep(1000); // B/c webDriver doesn't wait 50% of the time
//        waiter.until(webDriver -> webDriver.findElement(By.id("login-link")));
        Assertions.assertEquals("You successfully signed up! Please continue to the login page.", signupPage.getLoginLinkSuccessText());

        // Verify user can now login and access Home page
        signupPage.clickOnLoginLink();

        // Login the user
//        Thread.sleep(1000); // B/c webDriver doesn't wait 50% of the time
//        waiter.until(webDriver -> webDriver.findElement(By.id("login-page")));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginUser(username, password);
        // Wait...
//        Thread.sleep(1000); // B/c webDriver doesn't find the element 50% of the time
//        waiter.until(webDriver -> webDriver.findElement(By.id("contentDiv")));
        Assertions.assertEquals("Home", driver.getTitle());

        // Logout user
        HomePage homePage = new HomePage(driver);
        homePage.logoutUser();

        // Try to access Home page
//        Thread.sleep(1000); // B/c webDriver doesn't wait 50% of the time
//        waiter.until(webDriver -> webDriver.findElement(By.id("login-page")));
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
    public void testUserSignUpFailsOnDuplicateUserName() throws InterruptedException{
        /**
         * Verify a user can only sign up if their username is unique.
         */
        String username = "username2";
        String password = "password";

        WebDriverWait waiter = new WebDriverWait(driver, 5);

        driver.get(baseURL + "/signup");
        SignupPage signupPage = new SignupPage(driver);

        signupPage.signup("firstname", "lastname", username, password);
//        Thread.sleep(1000);
//        waiter.until(webDriver -> webDriver.findElement(By.id("success-msg")));
        signupPage.signup("firstname", "lastname", username, password);
//        waiter.until(webDriver -> webDriver.findElement(By.id("error-msg")));

        Assertions.assertEquals("The username already exists.", signupPage.getLoginErrorText());
    }

    @Test
    public void testNoteCreateViewDelete() throws InterruptedException{
        /**
         * Setup: Signup, Login, Go to Home
         */
        String username = "testNoteCreateViewDelete";
        String password = "password";

        // Signup
        driver.get(baseURL + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("firstname", "lastname", username, password);

        // Wait...
//        WebDriverWait waitLogin = new WebDriverWait(driver, 5);
//        waitLogin.until(webDriver -> webDriver.findElement(By.id("login-link")));
        // Verify user can now login and access Home page
        signupPage.clickOnLoginLink();
        driver.get(baseURL + "/login");

        // Login the user
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginUser(username, password);
        // Wait...
//        WebDriverWait waitHome = new WebDriverWait(driver, 5);
//        waitHome.until(webDriver -> webDriver.findElement(By.id("contentDiv")));

        /**
         * Write a test that creates a note, and verifies it is displayed.
         */


        /**
         * Write a test that edits an existing note and verifies
         * that the changes are displayed.
         */


        /**
         * Write a test that deletes a note and verifies that
         * the note is no longer displayed.
         */
    }

}
