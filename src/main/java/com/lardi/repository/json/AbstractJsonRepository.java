package com.lardi.repository.json;

import com.lardi.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.*;
import java.util.Properties;

public abstract class AbstractJsonRepository {
    @Autowired
    private Environment env;

    void writeJson(String json, String fileName) {
        String fullFileName = env.getProperty("pathToFileFolder") + fileName + ".json";

        try(FileWriter writer = new FileWriter(new File(fullFileName))) {
            writer.write(json);
        } catch (IOException e) {
            System.err.println("ERROR: cannot write to: " + fullFileName);
            e.printStackTrace();
        }
    }

    String readJson(String fileName) {
        String json = "";
        int c;
        String fullFileName = env.getProperty("pathToFileFolder") + fileName + ".json";

        try(FileReader reader = new FileReader(new File(fullFileName))) {
            while ((c = reader.read()) != -1) {
                json += (char) c;
            }
        } catch (IOException e) {
            System.err.println("ERROR: cannot read from: " + fullFileName);
            e.printStackTrace();
        }

        return json;
    }

    void checkIfExists(File f, String className) {
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.err.println("ERROR: cannot create new file: " + className);
                e.printStackTrace();
            }
        }
    }

    File getFilePath(String className) {
//        Properties properties = ServiceUtils.getProperties();
        return new File(env.getProperty("pathToFileFolder") + className + ".json");
    }
}
