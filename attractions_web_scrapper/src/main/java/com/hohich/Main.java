package com.hohich;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        String region = "Минск", sightClass = "Бары";
        Scrapper srp = new Scrapper();
        srp.scrap(region, sightClass);

    }
}