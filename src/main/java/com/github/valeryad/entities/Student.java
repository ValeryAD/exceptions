package com.github.valeryad.entities;

import com.github.valeryad.exceptions.IncorrectGradeException;
import com.github.valeryad.exceptions.NoSubjectsExcetption;

import java.util.*;

public class Student {
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
        if (grade <= 0 || grade > 10) {
            throw new IncorrectGradeException(String.format("attempt to give incorrect grade: %d", grade));
        }

        if (faculty.containsSubject(subject)) {
            if (register.containsKey(subject)) {
                register.get(subject).add(grade);
            } else {
                List<Integer> grades = new LinkedList<>();
                grades.add(grade);
                register.put(subject, grades);
            }
        }
    }

    public void setSubject(Subjects subject) {
        register.put(subject, new LinkedList<Integer>());
    }

    public EnumSet<Subjects> getSubjects() throws NoSubjectsExcetption {
        EnumSet<Subjects> assignedSubjects = EnumSet.copyOf(register.keySet());

        if (assignedSubjects.size() == 0) {
            throw new NoSubjectsExcetption(String.format("Student id:%d %s %s don't have any subject",
                    id, lastName, name));
        }

        return EnumSet.copyOf(register.keySet());
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
