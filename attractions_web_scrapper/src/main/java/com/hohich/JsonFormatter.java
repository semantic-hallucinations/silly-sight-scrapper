package com.hohich;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonFormatter {
    private Gson gson;

    public JsonFormatter() {
        gson = new GsonBuilder().setPrettyPrinting().create(); // Включаем Pretty Printing
    }

    public void processData(List<Scrapper.Sight> sights, String region, String sightClass) {
        writeToFile(sights, region, sightClass);
    }

    private void writeToFile(List<Scrapper.Sight> sights, String region, String sightClass) {
        try (FileWriter writer = new FileWriter("/app/data/" +
                region.replaceAll(" ", "") +
                sightClass.replaceAll(" ", "") + ".json")) {
            gson.toJson(sights, writer); // Сериализация списка объектов в файл
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


