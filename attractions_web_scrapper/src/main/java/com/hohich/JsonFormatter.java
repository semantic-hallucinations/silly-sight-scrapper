package com.hohich;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;

public class JsonFormatter {

    private List<String> sightsJson;
    private Gson gson;

    public JsonFormatter() {
        gson = new Gson();
        sightsJson = new ArrayList<>();
    }

    public void processData(List<Scrapper.Sight> sights, String region, String sightClass){
        serialize(sights);
        writeToFile(region, sightClass);
    }

    private void serialize(List<Scrapper.Sight> sights){
        for(Scrapper.Sight sight : sights){
            sightsJson.add(gson.toJson(sight));
        }
    }

    private void writeToFile(String region, String sightClass){
        try(FileWriter writer = new FileWriter(region+""+sightClass+".json")){
            writer.write(gson.toJson(sightsJson));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}

