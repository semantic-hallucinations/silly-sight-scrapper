package com.hohich;

import java.util.*;
import java.io.*;


public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(Arrays.toString(args));

        String regions[] = args.length == 0? new String[]{"минск"}
                : args;

        String sights[] = {"массаж", "спа", "казино", "парки", "библиотеки", "спортзалы",
                "отели", "бары", "рестораны", "кафе", "галереи", "клубы", "музеи", "памятники", "театры",
                "торговые центры", "кинотеатры", "стриптиз", "сексшопы", "церкви"};
        
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
