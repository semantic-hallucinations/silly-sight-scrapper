package com.hohich;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.time.Duration;
import java.util.*;

public class Scrapper {
    private WebDriver driver;
    private List<Sight> sights;
    private String path;


    public Scrapper() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
                " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124");

        driver = new ChromeDriver(options);
        sights = new ArrayList<>();
        path = "https://yandex.by/maps";
    }

    public List<Sight> getSights() {
        return Collections.unmodifiableList(sights);
    }

    public void scrap(String region, String sightClass) {
        driver.get(path);
        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(3));
        try{
            searchRequest(region, sightClass);
            scrollFeed();
        } catch (TimeoutException e) {
            System.out.println(e);
        }finally{
            driver.quit();
        }
    }

    private void searchRequest(String place, String type) throws TimeoutException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try{
            WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[@class='input__context']/input")));
            inputField.sendKeys(place + " " + type);

            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='small-search-form-view__button']/button")));
            searchButton.click();
        } catch (TimeoutException e) {
            throw new TimeoutException("Input field not found. Time out exceed");
        }
    }

    private void scrollFeed() throws TimeoutException{
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement sideBarList;
        try {
            sideBarList = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.className("search-list-view__list")));
        } catch (TimeoutException e) {
            throw new TimeoutException("Sigths list not found. Time out exceed");
        }

        Set<String> processedElements = new HashSet<>();
        boolean loadMore = true;

        while (loadMore) {
            //обработка видимых элементов
            List<WebElement> visibleElements = sideBarList.findElements(By.tagName("li"));
            if(visibleElements.size() == 0) {
                System.out.println("No sights of was found");
                return;
            }
            for (WebElement element : visibleElements) {
                String elementText = element.getText();
                if (processedElements.contains(elementText)) {
                    continue;
                }
                //записали в структуру для дальнейшей обработки полей
                Sight sight = scrapSight(element);
                System.out.println(sight.toString());
                sights.add(sight);
                processedElements.add(elementText); //запомнили уникальные элементы
            }

            try {
                //скроллим
                WebElement scrolledEl = driver.findElement(By.className("scroll__container"));
                js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", scrolledEl);
                //ждём
                wait.until(driver -> sideBarList.findElements(By.tagName("li")).size()
                        > visibleElements.size());
            } catch (TimeoutException e) {
                System.out.println("I've done! No more sights left!");
                loadMore = false;
            } finally {
                if (loadMore) { // Проверяем только если цикл не остановлен
                    List<WebElement> newVisibleElements = sideBarList.findElements(By.tagName("li"));
                    if (newVisibleElements.size() == processedElements.size()) {
                        loadMore = false; // Прерываем, если не загрузилось ничего нового
                    }
                }
            }
        }
    }

    private Sight scrapSight(WebElement sightWeb) {
        String sightName = "null", address = "null", url = "null";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            // Ожидание появления контейнера snippet
            WebElement snippet = wait.until(
                    ExpectedConditions.visibilityOf(sightWeb.findElement(
                            By.className("search-business-snippet-view__content")))
            );

            try {
                address = snippet.findElement(
                        By.className("search-business-snippet-view__address")).getText();
            } catch (org.openqa.selenium.NoSuchElementException e) {
                System.out.println("Address not found for this sight.");

            }

            try {
                sightName = snippet.findElement(
                        By.className("search-business-snippet-view__title")).getText();
            } catch (org.openqa.selenium.NoSuchElementException e) {
                System.out.println("Sight name not found for this sight.");
            }

            try {
                url = sightWeb.findElement(
                        By.className("link-overlay")).getAttribute("href");
            } catch (org.openqa.selenium.NoSuchElementException e) {
                System.out.println("URL not found for this sight.");
            }

        } catch (TimeoutException e) {
            System.out.println("Snippet container didn't load in time.");
        } finally {
            return new Sight(sightName, address, url);
        }
    }

    static class Sight {
        private String name;
        private String address;
        private String url;

        public Sight(String name, String address, String url) {
            this.name = name;
            this.address = address;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return "Sight{" +
                    "name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }
    }
}
