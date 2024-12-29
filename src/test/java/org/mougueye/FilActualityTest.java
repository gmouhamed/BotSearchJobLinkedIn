package org.mougueye;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.List;

@Tag("filActualityTest")
public class FilActualityTest {

    public Boolean filtreActuality(WebElement element){
        return element.getText().contains("Test") ||  element.getText().contains("test") || element.getText().contains("QA") || element.getText().contains("Testeur") || element.getText().contains("Automatisation");
    }
    @Test
    public void getListPostPerninent() throws InterruptedException, MalformedURLException {
        LoginLinkedInTest.login();
        EdgeDriver driver1 =LoginLinkedInTest.getDriver1();
        WebDriverWait wait =new WebDriverWait(driver1, Duration.ofSeconds(30));
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-view-name=\"feed-full-update\"]"))
        );
        int j=0;
        List<WebElement> listActualities = driver1.findElements(By.xpath("//div[@data-view-name=\"feed-full-update\"]"));
        List<WebElement> nextListActuality,saveOld=listActualities;
        WebElement duree,elText;
        String viewsText = "",elapsedTime="";
        do{
            nextListActuality = listActualities;
            for (WebElement actuality : listActualities){
                driver1.executeScript("arguments[0].scrollIntoView(true);", actuality); // Défiler jusqu'à l'élément
                Thread.sleep(200);
                try {
                    WebElement voirPlus = actuality.findElement(By.xpath(".//button[contains(@aria-label, 'voir plus, affiche du contenu déjà')]"));
                    voirPlus.click();
                }catch (NotFoundException  | ElementClickInterceptedException ex){
                    System.out.println("Not found");
                }
                try {Thread.sleep(500);
                    WebElement mail = actuality.findElement(By.xpath(".//a[contains(@href, 'mailto:')]"));
                    if (filtreActuality(actuality)){
                        System.out.println("Email : "+mail.getText());
                     /*   WebElement textDescription = driver1.findElement(By.xpath(".//div[@class=\"update-components-text relative update-components-update-v2__commentary \"]"));
                        System.out.println("Description : "+textDescription.getText());*/
                    }
                }catch (NotFoundException e){

                }
            }
            listActualities = driver1.findElements(By.xpath("//div[@data-view-name=\"feed-full-update\"]"));
            saveOld.addAll(nextListActuality);
            listActualities.removeAll(saveOld);
            j++;
        }while (j!=13);
    }
}
