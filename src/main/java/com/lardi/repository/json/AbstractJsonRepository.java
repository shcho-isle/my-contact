package com.lardi.repository.json;

import com.lardi.util.ServiceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public abstract class AbstractJsonRepository {
    void writeJson(String json, String fileName) {
        try {
            FileWriter writer = new FileWriter(new File(ServiceUtils.getProperties().getProperty("pathToFileFolder") + fileName + ".json"));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readJson(String fileName) throws IOException {
        FileReader reader = new FileReader(new File(ServiceUtils.getProperties().getProperty("pathToFileFolder") + fileName + ".json"));
        String json = "";
        int c;
        while ((c = reader.read()) != -1) {
            json += (char) c;
        }
        return json;
    }

    File getFilePath(String className) {
        Properties properties = ServiceUtils.getProperties();
        return new File(properties.getProperty("pathToFileFolder") + className + ".json");
    }
}
