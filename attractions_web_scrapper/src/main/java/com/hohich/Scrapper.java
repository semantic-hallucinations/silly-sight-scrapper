package com.hohich;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class Scrapper {
    private WebDriver driver;
    private List<Sight> sights;
    private String path;

    public Scrapper() {
        driver = new ChromeDriver();
        sights = new ArrayList<>();
        path = "https://yandex.by/maps";
    }

    public List<Sight> getSights() {
        return Collections.unmodifiableList(sights);
    }

    public void scrap(String region, String sightClass) throws NoSuchElementException {
        driver.get(path);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        searchRequest(region, sightClass);

        //дописать код для прокрутки страницы
        //потому что 4 пивнухи нам недостаточно

        WebElement sideBarList = driver.findElement(By.className("search-list-view__list"));
        for (WebElement element : sideBarList.findElements(By.tagName("li"))) {
            Sight sight = scrapSight(element);
            System.out.println(sight.toString());
            sights.add(sight);
        }

        driver.quit();
    }

    private void searchRequest(String place, String type) throws NoSuchElementException {
        WebElement inputField = driver.findElement(
                By.xpath("//span[@class='input__context']/input"));
        inputField.sendKeys(place + " " + type);

        WebElement searchButton = driver.findElement(
                By.xpath("//div[@class='small-search-form-view__button']/button"));
        searchButton.click();
    }

    private Sight scrapSight(WebElement sightWeb) {
        WebElement snippet = sightWeb.findElement(
                By.className("search-business-snippet-view__content"));
        String sightName = snippet.findElement(
                By.className("search-business-snippet-view__title")).getText();
        String address = snippet.findElement(
                By.className("search-business-snippet-view__address")).getText();
        String url = sightWeb.findElement(
                By.className("link-overlay")).getAttribute("href");
        return new Sight(sightName, address, url);
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
