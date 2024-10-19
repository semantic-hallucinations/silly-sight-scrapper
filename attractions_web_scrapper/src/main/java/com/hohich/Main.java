package com.hohich;

public class Main {
    public static void main(String[] args) {
        String region = "Минск", sightClass = "Бары";
        Scrapper srp = new Scrapper();
        srp.scrap(region, sightClass);

    }
}