package com.hohich;

import java.util.*;
import java.io.*;


public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(Arrays.toString(args));

        String regions[] = args.length == 0? new String[]{"minsk"}
                : args;

        String sights[] = {"galleries", "museums", "theaters", "massage", "spa", "casinos", "parks", "libraries", "gyms",
                "hotels", "bars", "restaurants", "churches", "cafes", "clubs", "monuments",
                "shopping centers", "cinemas", "strip clubs", "sex shops"};


        for(String region : regions){
            for(String sightClass : sights){
                Scrapper srp = new Scrapper();
                srp.scrap(region, sightClass);

                JsonFormatter jf = new JsonFormatter();
                jf.processData(srp.getSights(), region, sightClass);
            }
        }

    }
}
