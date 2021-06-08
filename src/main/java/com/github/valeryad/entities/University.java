package com.github.valeryad.entities;

import com.github.valeryad.exceptions.NoFacultiesInUniversityException;
import com.github.valeryad.exceptions.NoStudentsInGroupException;
import com.github.valeryad.exceptions.NoSubjectsExcetption;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class University {
    private static University instance;
    private Set<Faculty> faculties;

    private University() {
        faculties = new HashSet();
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
            throw new NoFacultiesInUniversityException("There's no any faculty in the university");
        }
        return new ArrayList<Faculty>(faculties);
    }

    public double countAverageGradeOfStudent(Student student) {
        int sum = 0;
        int gradesAmount = 0;

        try {
            for (Subjects subject : student.getSubjects()) {
                sum += student.getGradesOfSubject(subject).stream().mapToInt(grade -> grade).sum();
                gradesAmount += student.getGradesOfSubject(subject).size();
            }
        } catch (NoSubjectsExcetption noSubjectsExcetption) {
            noSubjectsExcetption.printStackTrace();
        }
        return (double) sum / gradesAmount;
    }

    public double countAverageGradeInSubjectOfGroup(Group group, Subjects subject){
        List<Student> students = null;

        try{
            students = group.getStudents();
        }catch(NoStudentsInGroupException e){
            System.err.println(e.getMessage());
        }

        return students.stream().map(student -> student.getGradesOfSubject(subject))
                .flatMap(grades -> grades.stream())
                .mapToInt(grade -> grade)
                .average().getAsDouble();
    }
}
