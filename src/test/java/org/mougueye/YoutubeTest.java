package org.mougueye;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Tag("youtubeTest")
public class YoutubeTest {
    public static ChromeDriver driver1 =null ;
   // public static EdgeDriver driver1 =null ;
  //  private static ExtentTest extentTest;


    public static ChromeDriver getDriver1() {
        return driver1;
    }
    public static long convertToSeconds(String time) {
        // Diviser la chaîne en parties
        String[] parts = time.split(":");
        long totalSeconds = 0;

       if(time!=""){
           if (parts.length == 3) { // Format HH:mm:ss
               int hours = Integer.parseInt(parts[0]);
               int minutes = Integer.parseInt(parts[1]);
               int seconds = Integer.parseInt(parts[2]);
               totalSeconds = (hours * 3600L) + (minutes * 60L) + seconds;
           } else if (parts.length == 2) { // Format mm:ss
               int minutes = Integer.parseInt(parts[0]);
               int seconds = Integer.parseInt(parts[1]);
               totalSeconds = (minutes * 60L) + seconds;
           } else {
               throw new IllegalArgumentException("Format de durée non valide : " + time);
           }
       }

        return totalSeconds;
    }

    public static boolean isRelevant(double views,  int years) {
        // Normalisation des critères
        double normalizedViews = Math.min(views / 700000, 1); // Ex: 1M vues = score max
        double recency = 1 - (years) / 5.0; // Ex: Pondérer les 5 dernières années

        // Calcul du score de pertinence
        double relevanceScore = 0.5 * normalizedViews +   0.2 * Math.max(0, recency);

        // Définir les conditions de pertinence
        return relevanceScore >= 0.5 &&  recency >= 0.5;
    }

    public static int convertToYears(String elapsedTime) {
        int years = 0;

            if (elapsedTime.toLowerCase().contains("an")) {
                years = Integer.parseInt(elapsedTime.replace("an","").trim()); // Ajouter les années
            }
            else if (elapsedTime.toLowerCase().contains("mois")) {
                years = Integer.parseInt(elapsedTime.replace("mois","").trim()) / 12; // Ajouter les mois convertis en années
            }
            else if (elapsedTime.toLowerCase().contains("semaine")) {
                years = Integer.parseInt(elapsedTime.replace("semaines","").trim())/ 52; // Ajouter les semaines convertis en années
            }


        return years;
    }
    public static double convertToViews(String viewsText) {
        double views = 0.0;
        // Recuperation nombre de vues

        viewsText = viewsText.replace(",", ".");  // Remplacer la virgule par un point
        if (viewsText.toLowerCase().contains("k")) {
            views =  (Double.parseDouble(viewsText.replace("k", "").replace("K", "")) * 1000);
        } else if (viewsText.toLowerCase().contains("m")) {
            views =  (Double.parseDouble(viewsText.replace("m", "").replace("M", "")) * 1000000);
        } else {
            views = Double.parseDouble(viewsText);
        }
        return  views;
    }
    @Test
    public void youtubeSearchVideo() throws InterruptedException,IllegalArgumentException {
        driver1 = new ChromeDriver();
        //driver1 = new EdgeDriver();
        driver1.manage().window().maximize();
        driver1.get("https://www.youtube.com/");
        WebDriverWait wait = new WebDriverWait(driver1,Duration.ofSeconds(200));
// Champs de recherche
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name=\"search_query\"]"))
        ).sendKeys("Backend Java");
        Thread.sleep(500);
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@title=\"Rechercher\"]"))
        ).click();
        Thread.sleep(1000);
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//yt-formatted-string[@title=\"Vidéos\"]"))
        ).click();
        Thread.sleep(500);
        //recuperation de la liste des videos
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//ytd-video-renderer"))
        );
        int j=0;
        List<WebElement> listVideos = driver1.findElements(By.xpath("//ytd-video-renderer"));
        List<WebElement> nextListVideos,saveOld=listVideos;
        WebElement duree,elText;
        String viewsText = "",elapsedTime="";
        Pattern viewsPattern;  Matcher viewsMatcher;
        do{
            nextListVideos = listVideos;
            for (WebElement video : nextListVideos){
                driver1.executeScript("arguments[0].scrollIntoView(true);", video); // Défiler jusqu'à l'élément
                Thread.sleep(500);
                try {
                    duree = video.findElement(By.xpath(".//a[@id=\"video-title\"]"));
                    elText =  video.findElement(By.xpath(".//div[@class=\"text-wrapper style-scope ytd-video-renderer\"]"));
                    System.out.println("Duree : "+duree.getText()+ "Text : "+elText.getText());
                    viewsPattern = Pattern.compile("(\\d+(,\\d+)?\\s?[kKmM]?)\\s?vues");
                    viewsMatcher = viewsPattern.matcher(elText.getText());
                    viewsText = viewsMatcher.find() ? viewsMatcher.group(1) : "0";

                    // Recuperation anciennete de la video sur youtube
                    Pattern timePattern = Pattern.compile("il y a (\\d+)\\s?(an|semaines|ans|mois)");
                    Matcher timeMatcher = timePattern.matcher(elText.getText());
                    elapsedTime = "0";
                    if (timeMatcher.find()) {
                        elapsedTime = timeMatcher.group(1) + " " + timeMatcher.group(2);
                    }
                    if(isRelevant(convertToViews(viewsText),convertToYears(elapsedTime)) && !duree.getText().contains("SHORTS")) {
                        System.out.println("Vues : " + convertToViews(viewsText) + " Annee : " + convertToYears(elapsedTime)+"Seconde : "+convertToSeconds(duree.getText()));
                        System.out.println(" ");
                        elText.click();
                        Thread.sleep(2000);
                        String urlDown = driver1.getCurrentUrl();
                        driver1.executeScript("window.open()");
                        // Passer au dernier onglet
                        driver1.switchTo().window(driver1.getWindowHandles().toArray()[1].toString());
                        driver1.get("https://notube.lol/fr/youtube-app-45"); // Charger une page dans le nouvel onglet
                        wait.until(
                                ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id=\"keyword\"]"))
                        ).sendKeys(urlDown);
                        Thread.sleep(500);
                        wait.until(
                                ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id=\"myDropdown\"]"))
                        ).click();
                        Thread.sleep(500);
                        wait.until(
                                ExpectedConditions.visibilityOfElementLocated(By.xpath("//option[@value=\"mp4hd\"]"))
                        ).click();
                        Thread.sleep(500);
                        wait.until(
                                ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id=\"submit-button\"]"))
                        ).click();
                        Thread.sleep(2000);

                        driver1.switchTo().window(driver1.getWindowHandles().toArray()[2].toString());
                        driver1.close();
                        Thread.sleep(2000);
                        wait.until(
                                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id=\"downloadButton\"]"))
                        ).click();
                        driver1.switchTo().window(driver1.getWindowHandles().toArray()[2].toString());
                        driver1.close();
                        Thread.sleep(1000);
                        driver1.close();
                        Thread.sleep(convertToSeconds(duree.getText())*1000);
                    }

                }catch (NotFoundException | NoSuchElementException e){

                }
            }
            listVideos = driver1.findElements(By.xpath("//ytd-video-renderer"));
            saveOld.addAll(nextListVideos);
            listVideos.removeAll(saveOld);
            j++;
        }while (!isRelevant(convertToViews(viewsText),convertToYears(elapsedTime)));
        System.out.println("Taille finale : "+saveOld.size());
    }
}


