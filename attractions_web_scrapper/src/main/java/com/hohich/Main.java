package com.hohich;

import java.util.*;
import java.io.*;
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

        String region = reader.readLine(),
                sightClass = reader.readLine();

        Scrapper srp = new Scrapper();
        srp.scrap(region, sightClass);

        JsonFormatter jf = new JsonFormatter();
        jf.processData(srp.getSights(), region, sightClass);

    }
}