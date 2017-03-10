package com.lardi.service.jsonService;

import com.lardi.util.ServiceUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Service("jsonFileService")
public class JsonFileServiceImpl implements JsonFileService {
    @Override
    public void write(String json, String fileName) {
        try {
            FileWriter writer = new FileWriter(new File(ServiceUtils.getProperties().getProperty("pathToFileFolder") + fileName + ".json"));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readJson(String fileName) throws IOException {
        FileReader reader = new FileReader(new File(ServiceUtils.getProperties().getProperty("pathToFileFolder") + fileName + ".json"));
        String json = "";
        int c;
        while ((c = reader.read()) != -1) {
            json += (char) c;
        }
        return json;
    }
}
