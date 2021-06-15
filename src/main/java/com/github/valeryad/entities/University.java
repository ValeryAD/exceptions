package com.github.valeryad.entities;

import com.github.valeryad.exceptions.NoFacultiesInUniversityException;
import com.github.valeryad.exceptions.NoGroupsInFacultyException;
import com.github.valeryad.exceptions.NoStudentsInGroupException;
import com.github.valeryad.exceptions.NoSubjectsExcetption;

import java.util.*;

public class University {
    private static final String NO_FACULTIES_MESSAGE = "There's no any faculty in the university";

    private static University instance;
    private Set<Faculty> faculties;

    private University() {
        faculties = new HashSet<>();
    }

    public static University getInstance() {
        if (instance == null) {
            instance = new University();
        }

        return instance;
    }

    public void addFaculty(Faculty faculty) {
        faculties.add(faculty);
    }

    public List<Faculty> getFaculties() throws NoFacultiesInUniversityException {
        if (faculties.size() == 0) {
            throw new NoFacultiesInUniversityException(NO_FACULTIES_MESSAGE);
        }
        return new ArrayList<>(faculties);
    }

    public double countAverageGradeOfStudent(Student student) {
        int sum = 0;
        int gradesAmount = 0;

        try {
            for (Subjects subject : student.getSubjects()) {
                sum += student.getGradesOfSubject(subject).stream().mapToInt(grade -> grade).sum();
                gradesAmount += student.getGradesOfSubject(subject).size();
            }
        } catch (NoSubjectsExcetption e) {
            System.err.println(e.getMessage());
        }
        return (double) sum / gradesAmount;
    }

    public double countAverageGradeInSubjectOfGroup(Group group, Subjects subject) {
        List<Student> students = null;

        try {
            students = group.getStudents();
        } catch (NoStudentsInGroupException e) {
            System.err.println(e.getMessage());
        }

        OptionalDouble optional = students.stream().map(student -> student.getGradesOfSubject(subject))
                .flatMap(grades -> grades.stream())
                .mapToInt(grade -> grade)
                .average();

        if (optional.isPresent()) {
            return optional.getAsDouble();
        }

        return -1;
    }

    public double countAverageGradeInSubjectOfUniversity(Subjects subject) {
        List<Double> averageGradesInSubjectOfEachGroup = new LinkedList<>();

        try {
            for (Faculty faculty : getFaculties()) {
                for (Group group : faculty.getGroups()) {
                    averageGradesInSubjectOfEachGroup.add(countAverageGradeInSubjectOfGroup(group, subject));
                }
            }
        } catch (NoFacultiesInUniversityException | NoGroupsInFacultyException e) {
            System.err.println(e.getMessage());
        }

        OptionalDouble optional = averageGradesInSubjectOfEachGroup.stream().
                mapToDouble(averageGrade -> averageGrade).
                filter(value -> value > 0).
                average();

        if(optional.isPresent()){
            return optional.getAsDouble();
        }

        return -1;
    }
}
