package com.github.valeryad.entities;

import com.github.valeryad.exceptions.IncorrectGradeException;
import com.github.valeryad.exceptions.NoSubjectsExcetption;

import java.util.*;

public class Student {
    public static final int MIN_GRADE = 1;
    public static final int MAX_GRADE = 10;
    private static int lastId = 0;
    private final int id;
    private String name;
    private String lastName;
    private Faculty faculty;
    private Map<Subjects, List<Integer>> register;

    public Student() {
        id = ++lastId;
        register = new HashMap<>();
    }

    public Student(String name, String lastName) {
        id = ++lastId;
        this.name = name;
        this.lastName = lastName;
        register = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void giveGrade(Subjects subject, int grade) throws IncorrectGradeException {
        if (grade < MIN_GRADE || grade > MAX_GRADE) {
            throw new IncorrectGradeException(String.format("attempt to give incorrect grade: %d to student %s", grade, this));
        }
        if (register.containsKey(subject)) {
            register.get(subject).add(grade);
        } else {
            List<Integer> grades = new LinkedList<>();
            grades.add(grade);
            register.put(subject, grades);
        }
    }

    public void assignSubject(Subjects subject) {
        register.put(subject, new LinkedList<Integer>());
    }

    public void assignSubjects(Collection<Subjects> subjects) {
        for (Subjects subject : subjects) {
            register.put(subject, new LinkedList<Integer>());
        }
    }

    public List<Subjects> getSubjects() throws NoSubjectsExcetption {
        if (register.isEmpty()) {
            throw new NoSubjectsExcetption(String.format("Student id:%d %s %s don't have any subject",
                    id, lastName, name));
        }

        return new ArrayList<Subjects>(register.keySet());
    }

    public List<Integer> getGradesOfSubject(Subjects subject){
        if(register.containsKey(subject)){
            return register.get(subject);
        }else{
            return new ArrayList<Integer>();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;

        return id == student.getId();
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%s %s id: %d", lastName, name, id);
    }

}
