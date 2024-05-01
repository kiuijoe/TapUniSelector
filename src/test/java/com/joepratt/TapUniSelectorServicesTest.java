package com.joepratt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joepratt.model.SubjectData;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;

class TapUniSelectorServicesTest {
    private final SubjectData subjectData;
    private static final String TEST_COURSES_FILE = "subjectData.json";

    public TapUniSelectorServicesTest() throws IOException {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(TEST_COURSES_FILE);
        subjectData = new ObjectMapper().readValue(is, SubjectData.class);
    }

    @org.junit.jupiter.api.Test
    void selectEx1() {
        String inputString =
                """
                5
                s 70 78 82 57 74
                l 68 81 81 60 78
                s 63 76 55 80 75
                s 90 100 96 10 10
                l 88 78 81 97 93
                
                """;
        Reader mockReader = new StringReader(inputString);

        long actualResult = new TapUniSelectorServices().getAmtOfPass(subjectData, mockReader);
        long expectedResult = 2;

        assertEquals(expectedResult, actualResult);
    }

    @org.junit.jupiter.api.Test
    void selectEx2() {
        String inputString =
                """
                20
                l 100 67 39 85 87
                s 38 75 75 45 90
                l 43 95 7 35 49
                l 82 77 74 35 44
                s 96 80 92 58 84
                l 23 60 44 27 3
                l 42 24 52 23 63
                s 44 78 98 51 10
                l 93 38 73 88 12
                l 34 29 43 48 61
                l 83 33 97 3 59
                l 24 84 22 35 33
                s 81 42 80 34 87
                l 8 87 82 80 100
                l 48 75 75 3 50
                l 93 76 25 71 31
                s 60 92 64 66 11
                l 61 47 6 21 83
                l 68 1 47 81 78
                l 8 72 54 20 25
                
                """;
        Reader mockReader = new StringReader(inputString);

        long actualResult = new TapUniSelectorServices().getAmtOfPass(subjectData, mockReader);
        long expectedResult = 3;

        assertEquals(expectedResult, actualResult);
    }

    @org.junit.jupiter.api.Test
    void selectWithInvalidSpecialty() {
        String inputString =
                """
                5
                s 63 76 55 80 75
                x 90 100 96 10 10
                l 88 78 81 97 93
                
                """;
        Reader mockReader = new StringReader(inputString);

        InputMismatchException exception = assertThrows(InputMismatchException.class, () -> new TapUniSelectorServices().getAmtOfPass(subjectData, mockReader));
        assertEquals("Student specialisation invalid: 'x'", exception.getMessage());
    }

    @org.junit.jupiter.api.Test
    void selectWithBlankInput() {
        String inputString = "";
        Reader mockReader = new StringReader(inputString);

        InputMismatchException exception = assertThrows(InputMismatchException.class, () -> new TapUniSelectorServices().getAmtOfPass(subjectData, mockReader));
        assertEquals("Input contains invalid number format.", exception.getMessage());
    }

    @org.junit.jupiter.api.Test
    void selectWithMissingScore() {
        String inputString =
                """
                5
                s 63 76 55 80 75
                l 88 78 81 97
                
                """;
        Reader mockReader = new StringReader(inputString);

        InputMismatchException exception = assertThrows(InputMismatchException.class, () -> new TapUniSelectorServices().getAmtOfPass(subjectData, mockReader));
        assertEquals("Input line invalid: 'l 88 78 81 97'", exception.getMessage());
    }

    @org.junit.jupiter.api.Test
    void selectWithNegativeScore() {
        String inputString =
                """
                5
                s 63 -76 55 80 75
                l 88 78 81 97 93
                
                """;
        Reader mockReader = new StringReader(inputString);

        InputMismatchException exception = assertThrows(InputMismatchException.class, () -> new TapUniSelectorServices().getAmtOfPass(subjectData, mockReader));
        assertEquals("Student score invalid: '-76'", exception.getMessage());
    }
}