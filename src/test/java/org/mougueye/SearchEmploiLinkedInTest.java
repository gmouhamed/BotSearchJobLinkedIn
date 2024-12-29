package org.mougueye;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.List;
@Tag("searchEmploiTest")
public class SearchEmploiLinkedInTest {

    public void competenceInput(WebElement labelItem,WebElement inputItem){
        if(labelItem.getText().contains("Databases")){
            inputItem.sendKeys("5");
        }
        if(labelItem.getText().contains("Python")){
            inputItem.sendKeys("1");
        }
        if(labelItem.getText().contains("Technologies")){
            inputItem.sendKeys("4");
        }
        if(labelItem.getText().contains("qualité")){
            inputItem.sendKeys("3");
        }
        else if (labelItem.getText().contains("d’équipe ")){
            inputItem.sendKeys("4");
        }
        else if(labelItem.getText().contains("Postman")){
            inputItem.sendKeys("5");
        }
        else  if(labelItem.getText().contains("Java")){
            inputItem.sendKeys("5");
        }
        else  if(labelItem.getText().contains("Automatisation")){
            inputItem.sendKeys("3");
        }
        else if(labelItem.getText().contains("Selenium")){
            inputItem.sendKeys("3");
        }
        else if(labelItem.getText().contains("Karate")){
            inputItem.sendKeys("3");
        }
        else if(labelItem.getText().contains("Test")){
            inputItem.sendKeys("3");
        }else if(labelItem.getText().contains("React")){
            inputItem.sendKeys("2");
        }
        else if(labelItem.getText().contains("Spring")){
            inputItem.sendKeys("3");
        }
        else if(labelItem.getText().contains("Cucumber")){
            inputItem.sendKeys("3");
        }
        else if(labelItem.getText().contains("Docker")){
            inputItem.sendKeys("3");
        }
        else if(labelItem.getText().contains("Agile")){
            inputItem.sendKeys("4");
        }
        else if(labelItem.getText().contains("SQL")){
            inputItem.sendKeys("5");
        }
        else if(labelItem.getText().contains("Azure")){
            inputItem.sendKeys("1");
        }
        else if(labelItem.getText().contains("CI/CD")){
            inputItem.sendKeys("3");
        }
        else if(labelItem.getText().contains("C#")){
            inputItem.sendKeys("1");
        }
        else if(labelItem.getText().contains("microservices")){
            inputItem.sendKeys("4");
        }
        else if(labelItem.getText().contains("Linux")){
            inputItem.sendKeys("5");
        }
        else inputItem.sendKeys("0");
    }
    public void informationInput(WebElement labelItem , WebElement inputItem){

        if(labelItem.getText().contains("First")){
            inputItem.clear();
            inputItem.sendKeys("YOUR FIRSTNAME");
        }
        if(labelItem.getText().contains("Last")){
            inputItem.clear();
            inputItem.sendKeys("YOUR LASTNAME");
        }
        if(labelItem.getText().contains("phone")){
            inputItem.clear();
            inputItem.sendKeys("YOUR PHONE");
        }
        if(labelItem.getText().contains("Address*")){
            inputItem.clear();
            inputItem.sendKeys("ADDRESS");
        }
    }

    @Test
    public void searchEmploi() throws InterruptedException, MalformedURLException {
        LoginLinkedInTest.login();
        EdgeDriver driver1 = LoginLinkedInTest.getDriver1();
        WebDriverWait wait =new WebDriverWait(driver1, Duration.ofSeconds(20));
        //clique sur le boutton Emploi
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href=\"https://www.linkedin.com/jobs/?\"]"))
        ).click();
        Thread.sleep(3000);
        //liste complete des emplois
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href=\"https://www.linkedin.com/jobs/collections/recommended?discover=recommended&discoveryOrigin=JOBS_HOME_JYMBII\"]"))
        ).click();
        Thread.sleep(3000);
        // champs pour rechercher un poste
        WebElement post = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class=\"jobs-search-box__text-input jobs-search-box__keyboard-text-input jobs-search-global-typeahead__input\"]"))
        );
        post.sendKeys("POSTE A RECHERCHER");

        wait.until(ExpectedConditions.attributeToBe(post, "aria-expanded", "true"));
        post.sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        // champs pour mettre la ville
        WebElement pays = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-label=\"Ville, département ou code postal\"]"))
        );
        pays.clear();
        pays.sendKeys("Maroc");
        pays.sendKeys(Keys.ENTER);

        Thread.sleep(3000);
        //Filtre Candidature simplifiée
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Filtre Candidature simplifiée.\"]"))
        ).click();
        // Filtre Date de publication
        Thread.sleep(3000);
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Filtre Date de publication. En cliquant sur ce bouton, toutes les options de filtres Date de publication apparaîtront.\"]"))
        ).click();
        Thread.sleep(3000);
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@class=\"search-reusables__value-label\"]"))
        );
        List<WebElement> dateFilters = driver1.findElements(By.xpath("//label[@class=\"search-reusables__value-label\"]"));
        int i=0;
        System.out.println("Size : "+dateFilters.size());
        // Chosir le temps pour le filtre, 24h
        for (WebElement dateFilter : dateFilters){
            i++;
            driver1.executeScript("arguments[0].scrollIntoView(true);", dateFilter); // Défiler jusqu'à l'élément
            dateFilter.click();
            Thread.sleep(1000);
            if (i==3){
                break;
            }
        }
        WebElement cancelButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Annuler le filtre Date de publication']"))
        );
        // le boutton à droite de Annuler
        WebElement buttonRight = cancelButton.findElement(By.xpath("following-sibling::button"));

        // Cliquer sur le bouton à droite
        buttonRight.click();
        Thread.sleep(2000);
        //Afficher tous les filtres
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Afficher tous les filtres. En cliquant sur ce bouton, toutes les options de filtres disponibles apparaîtront.\"]"))
        ).click();
        Thread.sleep(1000);
        //Afficher par les plus recents
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@for=\"advanced-filter-sortBy-R\"]"))
        ).click();
        Thread.sleep(2000);
        //Appliquer les filtres actuels pour afficher les résultats
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@aria-label, 'Appliquer les filtres actuels pour afficher')]"))
        ).click();
        Thread.sleep(3000);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='ember-view   jobs-search-results__list-item occludable-update p0 relative scaffold-layout__list-item']"))
        );
        //Liste des Jobs
        List<WebElement> jobItems = driver1.findElements(By.xpath("//li[@class='ember-view   jobs-search-results__list-item occludable-update p0 relative scaffold-layout__list-item']"));
        // Depot de Candidature
        System.out.println("Size : "+jobItems.size());
        for (WebElement item : jobItems) {
            driver1.executeScript("arguments[0].scrollIntoView(true);", item); // Défiler jusqu'à l'élément
            item.click();
            Thread.sleep(500); //
            try {
                //Si j'ai pas encore deposer à ce poste
                WebElement saveBut = driver1.findElement(By.xpath("//button[contains(@aria-label, 'Candidature simplifiée pour le poste')]"));
                if(saveBut != null){
                    saveBut.click();
                    Thread.sleep(500);
                    try {// Scenario 1 -> Suivant -> Suivant -> Formulaire
                        // Scenario 2 -> Suivant ->
                        try {
                            driver1.findElement(By.xpath("//button[@aria-label=\"Passez à l’étape suivante\"]")
                            ).click();
                        }catch (NoSuchElementException e){

                        }
                        Thread.sleep(500);
                        try {
                            driver1.findElement(By.xpath("//button[@aria-label=\"Passez à l’étape suivante\"]"))
                                    .click();
                        }catch (NoSuchElementException e){

                        }
                        //Recuperation de la liste des inputs
                        try {
                            List<WebElement> inputItems = driver1.findElements(By.xpath("//input[@class=\" artdeco-text-input--input\"]"));
                            WebElement labelItem;
                            for( WebElement inputItem : inputItems){
                                driver1.executeScript("arguments[0].scrollIntoView(true);", inputItem); // Défiler jusqu'à l'élément
                                inputItem.clear();
                                labelItem = inputItem.findElement(By.xpath("preceding-sibling::label"));
                                competenceInput(labelItem,inputItem);
                                informationInput(labelItem,inputItem);
                                Thread.sleep(500);
                            }
                        }catch (NoSuchElementException e){

                        }
                        wait.until(
                                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Vérifiez votre candidature\"]"))
                        ).click();
                        Thread.sleep(500);
                        driver1.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                        Thread.sleep(500);

                    }catch (TimeoutException e){
                        driver1.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                    }
                    // Pour envoyer une candidature
                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Envoyer la candidature\"]"))
                    ).click();
                    Thread.sleep(1000);
                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Ignorer\"]"))
                    ).click();
                }
            }catch (NoSuchElementException e) {
                // Si le poste est déja deposé on continue avec un autre poste
                System.out.println("Poste déja déposé.");
            }

        }

    }
}
