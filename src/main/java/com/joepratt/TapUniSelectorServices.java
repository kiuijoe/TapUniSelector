package com.joepratt;

import com.joepratt.model.Specialty;
import com.joepratt.model.SubjectData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.SortedMap;
import java.util.TreeMap;

public class TapUniSelectorServices {
    /**
     * Returns the amount of students who have passed the entrance examination of Tap University.
     * @param subjectData json file containing the subjects, specialisations, and score thresholds.
     * @param in the stdin containing the subject scores and elected specialisation for each student.
     * @return long of the amount of students who passed.
     */
    public long getAmtOfPass(SubjectData subjectData, Reader in) {
        try (BufferedReader reader = new BufferedReader(in)) {
            int numOfExaminees = Integer.parseInt(reader.readLine());
            return reader.lines()
                    .limit(numOfExaminees)
                    .map(line -> processLine(line, subjectData))
                    .filter(student -> hasPassed(student, subjectData))
                    .count();
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Input contains invalid number format.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Convert each line into a Student object
    private Student processLine(String line, SubjectData subjectData) {
        String[] arr = line.split(" ");
        String specialisation = arr[0];
        if (arr.length != subjectData.subjects().length + 1) {
            throw new InputMismatchException("Input line invalid: '" + line + "'");
        }

        SortedMap<String, Integer> subjects = new TreeMap<>();
        for (int i = 0; i < subjectData.subjects().length; i++) {
            int subjectScore = Integer.parseInt(arr[i+1]);
            if (subjectScore < 0) {
                throw new InputMismatchException("Student score invalid: '" + subjectScore + "'");
            }
            String subjectTitle = subjectData.subjects()[i];
            subjects.put(subjectTitle, subjectScore);
        }

        return new Student(specialisation, subjects);
    }

    // Determine whether student has passed based on the subject data
    private boolean hasPassed(Student student, SubjectData subjectData) {
        for (Specialty specialty : subjectData.specialties()) {
            if (specialty.key().equals(student.specialisation)) {
                int total = student.subjects.values()
                        .stream()
                        .mapToInt(Integer::intValue)
                        .sum();

                int specialtyScore = Arrays.stream(specialty.subjects())
                        .map(student.subjects::get)
                        .mapToInt(Integer::intValue)
                        .sum();

                return  total >= subjectData.globalMinScore() && specialtyScore >= specialty.minPass();
            }
        }
        throw new InputMismatchException("Student specialisation invalid: '" + student.specialisation + "'");
    }

    private record Student(String specialisation, SortedMap<String, Integer> subjects) {}
}
