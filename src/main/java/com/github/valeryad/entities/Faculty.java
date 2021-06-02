package com.github.valeryad.entities;

import com.github.valeryad.exceptions.NoStudentsInGroupException;
import com.github.valeryad.exceptions.NoSubjectsExcetption;
import com.github.valeryad.exceptions.NoGroupsInFacultyException;

import java.util.*;

public class Faculty {

    private String name;
    private Set<Group> groups;
    private EnumSet<Subjects> subjects;

    public Faculty(String name) {
        this.name = name;
        groups = new HashSet<>();

    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public Set<Group> getGroups() throws NoGroupsInFacultyException {
        if(groups.size() == 0){
            throw new NoGroupsInFacultyException("No groups assigned to faculty");
        }

        return groups;
    }

    public void addStudent(Group group, Student student) {
        group.addStudent(student);
        student.setFaculty(this);
    }

    public void addStudents(Group group, Collection<Student> students) {
        group.addStudents(students);
    }

    public String getFacultyName() {
        return name;
    }

    public EnumSet<Subjects> getSubjects() throws NoSubjectsExcetption {
        if(subjects == null){
            throw new NoSubjectsExcetption("No subjects assigned to faculty");
        }
        return subjects;
    }

    public boolean containsSubject(Subjects subject) {
        for (Subjects tempSubject : subjects) {
            if (tempSubject.equals(subject)) {
                return true;
            }
        }
        return false;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        for (Group group : groups) {
            try {
                students.addAll(group.getStudents());
            } catch (NoStudentsInGroupException e) {
                e.printStackTrace();
            }
        }
        return students;
    }
}
