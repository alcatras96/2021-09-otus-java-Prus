package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final File file;
    private final Gson gson;

    public FileSerializer(String fullOutputFilePath) {
        this.file = new File(fullOutputFilePath);
        gson = new Gson();
    }

    @Override
    public void serialize(Map<String, Double> data) {
        final String jsonData = gson.toJson(data);
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(jsonData);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
