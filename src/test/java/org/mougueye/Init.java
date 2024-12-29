package org.mougueye;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Getter
@Setter
public class Init {
    public static final LoginLinkedInTest loginTest = new LoginLinkedInTest(); // Créer une instance de LoginTest
    public static final ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") +"\\test-output\\testReport.html");;
    public static final ExtentReports extent =  new ExtentReports();;



    public static void setUp() throws InterruptedException, MalformedURLException {
        extent.attachReporter(sparkReporter);

        sparkReporter.config().setOfflineMode(Boolean.valueOf(true));
        sparkReporter.config().setDocumentTitle("Automation Saman Report");
        sparkReporter.config().setReportName("Test Report Selenium");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        sparkReporter.config().setEncoding("UTF-8");

        if (LoginLinkedInTest.getDriver1()!=null && !LoginLinkedInTest.getDriver1().getWindowHandles().isEmpty()) {
            System.out.println("Le driver est ouvert.");

            // Faire un refresh de la page
            loginTest.getDriver1().navigate().refresh();
        } else {
            loginTest.login();
            assertEquals("SaMan",Init.loginTest.getDriver1().getTitle());
        }

    }
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        // Chemin pour enregistrer la capture d'écran

        String filePath = System.getProperty("user.dir") + "\\test-output\\screenshots\\" + screenshotName + ".png";
        File destFile = new File(filePath);
        System.out.println("Screenshot enregistré : " + filePath);

        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs(); // Créer le dossier parent
            System.out.println("Screenshot enregistré 0000000: " + filePath);

        }
        if (destFile.exists()) {
            destFile.delete();
        }
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            // Copier le fichier screenshot dans l'emplacement voulu
            Files.copy(srcFile.toPath(), destFile.toPath());
            System.out.println("Screenshot enregistré : " + filePath);

        } catch (IOException e) {
            System.out.println("Screenshot  : " + filePath);

            e.printStackTrace();
        }

        return filePath; // Retourne le chemin de la capture d'écran
    }
}
