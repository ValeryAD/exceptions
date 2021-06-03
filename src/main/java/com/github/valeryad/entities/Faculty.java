package com.github.valeryad.entities;

import com.github.valeryad.exceptions.NoGroupsInFacultyException;
import com.github.valeryad.exceptions.NoStudentsInGroupException;
import com.github.valeryad.exceptions.NoSubjectsExcetption;

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
        if (groups.size() == 0) {
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

    public void setSubject(Subjects subject) {
        if (subjects == null) {
            subjects = EnumSet.of(subject);
        } else {
            subjects.add(subject);
        }
    }

    public EnumSet<Subjects> getSubjects() throws NoSubjectsExcetption {
        if (subjects == null) {
            throw new NoSubjectsExcetption("No subjects assigned to faculty");
        }
        return subjects;
    }

    public boolean containsSubject(Subjects subject) {
        return subjects.contains(subject);
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

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Faculty faculty = (Faculty) o;
        return name.equals(faculty.name);
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }

    @Override
    public String toString(){
        return name;
    }
}
