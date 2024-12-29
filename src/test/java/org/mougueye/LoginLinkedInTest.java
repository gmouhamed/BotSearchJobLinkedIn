package org.mougueye;


import com.aventstack.extentreports.ExtentTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.time.Duration;
import java.util.List;

import static org.mougueye.Init.extent;

@Tag("loginLinkedInTest")
public class LoginLinkedInTest {
    //public static ChromeDriver driver1 =null ;
    public static WebDriver driver1 =null ;
    private static ExtentTest extentTest;


    public static EdgeDriver getDriver1() {
        return (EdgeDriver) driver1;
    }



    public static void login() throws InterruptedException, MalformedURLException {
        extentTest = extent.createTest("Login LinkedIn ", "");
        String messageError = "", titleError = "";
       // driver1 = new ChromeDriver();
        driver1 = new EdgeDriver();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("browserVersion", "100");
        chromeOptions.setCapability("platformName", "Windows");
// Showing a test name instead of the session id in the Grid UI
        chromeOptions.setCapability("se:name", "My simple test");
// Other type of metadata can be seen in the Grid UI by clicking on the
// session info or via GraphQL
        chromeOptions.setCapability("se:sampleMetadata", "Sample metadata value");
        driver1 = new RemoteWebDriver(new URL(" http://192.168.0.100:4444"), chromeOptions);
        driver1.manage().window().maximize();
        driver1.get("https://www.linkedin.com/");
        WebDriverWait wait =new WebDriverWait(driver1, Duration.ofSeconds(20));
        //clique sur le boutton connexion
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href=\"https://www.linkedin.com/login/fr?fromSignIn=true&trk=guest_homepage-basic_nav-header-signin\"]"))
        ).click();
        Thread.sleep(2000);
        //username
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id=\"username\"]"))
        ).sendKeys("gmouhamed4@gmail.com");
        Thread.sleep(2000);
        //password
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id=\"password\"]"))
        ).sendKeys("mertegueye");
        Thread.sleep(2000);
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type=\"submit\"]"))
        ).click();
        //fermer les messages
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"msg-overlay-bubble-header__details flex-row align-items-center ml1\"]"))
        ).click();
        Thread.sleep(3000);

    }

}


