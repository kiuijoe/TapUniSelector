package com.joepratt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joepratt.model.SubjectData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.*;

public class TapUniSelectorApp {
    private static final String COURSES_FILE = "subjectData.json";

    public static void main(String[] args) throws IOException, IllegalArgumentException {
        TapUniSelectorServices selector = new TapUniSelectorServices();
        SubjectData subjectData = extractSubjectData();
        long amtPass = selector.getAmtOfPass(subjectData, new InputStreamReader(System.in));
        System.out.println(amtPass + "\n");
    }

    private static SubjectData extractSubjectData() throws IOException {
        String filePath = System.getProperty("user.dir") + File.separator + COURSES_FILE;
        File file = new File(filePath);

        // if file doesn't exist, copy a fresh one from resources folder
        copyFileFromResources(filePath);

        return new ObjectMapper().readValue(file, SubjectData.class);
    }

    private static void copyFileFromResources(String destinationFile) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("subjectData.json");
        Path destinationPath = Paths.get(destinationFile);

        try {
            Files.copy(is, destinationPath);
        } catch (FileAlreadyExistsException ignored) {
            // Don't overwrite, move on. Preserve the user's preferences.
        }
    }
}