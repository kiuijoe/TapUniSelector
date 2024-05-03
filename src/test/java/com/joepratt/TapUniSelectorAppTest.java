package com.joepratt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TapUniSelectorAppTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setIn(System.in);
        System.setOut(System.out);
    }

    public void runApp(String path) throws IOException {
        final InputStream inputStream = getClass().getResourceAsStream(path);
        System.setIn(inputStream);

        TapUniSelectorApp.main(new String[0]);
    }

    @Test
    void testValidStdin() throws IOException {
        runApp("/ex1Input.txt");

        String actual = outputStreamCaptor.toString();
        String expected =
                """
                2
                
                """;

        assertEquals(expected, actual);
    }

}