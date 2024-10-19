package com.hohich;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Scrapper {
    private WebDriver driver;
    private List<Sight> sights;
    private String path;

    public Scrapper() {
        driver = new ChromeDriver();
        sights = new ArrayList<>();
        path = "https://yandex.by/maps";
    }

    public void scrap(String region, String sightClass){
        driver.get(path);
        System.out.println("Loading main page..." + path);
        System.out.println("Page title: " + driver.getTitle());

        searchRequest(region, sightClass);

        WebElement sideBarList = driver.findElement(By.className("search-list-view__list"));
        for (WebElement element : sideBarList.findElements(By.tagName("li"))) {
            scrapSight(element);
        }
    }

    private void searchRequest(String place, String type) throws NoSuchElementException {
        WebElement inputField = driver.findElement(
                By.xpath("//span[@class='input__context']/input")
        );
        System.out.println(inputField.toString());
        inputField.sendKeys(place + " " + type);

        WebElement searchButton = driver.findElement(
                By.xpath("//div[@class='small-search-form-view__button']/button")
        );
        searchButton.click();
    }

    private void scrapSight(WebElement sigth){
        //ща всё будет
    }

    static class Sight{
        private String name;
        private String url;
        private String addres;

        public Sight(String name, String url, String addres){
            this.name = name;
            this.url = url;
            this.addres = addres;
        }
    }
}
