package com.lardi.service.jsonService;

import java.io.IOException;

public interface JsonFileService {
    void write(String json, String fileName);
    String readJson(String fileName) throws IOException;
}
