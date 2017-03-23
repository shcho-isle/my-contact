package com.telecom.repository.json;

import org.springframework.beans.factory.annotation.Value;

import java.io.*;

public abstract class AbstractJsonRepository {

    @Value("${pathToFileFolder}")
    private String pathToFileFolder;

    protected void writeJson(String json, String fileName) {
        String fullFileName = pathToFileFolder + fileName + ".json";

        try (FileWriter writer = new FileWriter(new File(fullFileName))) {
            writer.write(json);
        } catch (IOException e) {
            System.err.println("ERROR: cannot write to: " + fullFileName);
            e.printStackTrace();
        }
    }

    protected String readJson(String fileName) {
        StringBuilder json = new StringBuilder();
        int c;
        String fullFileName = pathToFileFolder + fileName + ".json";

        try (FileReader reader = new FileReader(new File(fullFileName))) {
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
        } catch (IOException e) {
            System.err.println("ERROR: cannot read from: " + fullFileName);
            e.printStackTrace();
        }

        return json.toString();
    }

    protected void checkIfExists(File f, String className) {
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.err.println("ERROR: cannot create new file: " + className);
                e.printStackTrace();
            }
        }
    }

    protected File getFilePath(String className) {
        return new File(pathToFileFolder + className + ".json");
    }
}
