package com.github.valeryad.entities;

import com.github.valeryad.exceptions.NoGroupsInFacultyException;
import com.github.valeryad.exceptions.NoStudentsInGroupException;
import com.github.valeryad.exceptions.NoSubjectsExcetption;

import java.util.*;

public class Faculty {

    private static final String NO_GROUPS_IN_FACULTY_MESSAGE = "No groups assigned to faculty";
    private static final String NO_SUBJECTS_MESSAGE = "No subjects assigned to faculty";

    private String name;
    private Set<Group> groups;
    private EnumSet<Subjects> subjects;

    public Faculty(String name) {
        this.name = name;
        groups = new HashSet<>();
    }

    public String getFacultyName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public List<Group> getGroups() throws NoGroupsInFacultyException {
        if (groups.size() == 0) {
            throw new NoGroupsInFacultyException(NO_GROUPS_IN_FACULTY_MESSAGE);
        }
        return new ArrayList<>(groups);
    }

    public void assignSubjects(EnumSet<Subjects> subjects) {
        if (this.subjects == null) {
            this.subjects = EnumSet.copyOf(subjects);
        } else {
            this.subjects.addAll(subjects);
        }
    }

    public void assignSubject(Subjects subject) {
        if (subjects == null) {
            subjects = EnumSet.of(subject);
        } else {
            subjects.add(subject);
        }
    }

    public EnumSet<Subjects> getSubjects() throws NoSubjectsExcetption {
        if (subjects == null) {
            throw new NoSubjectsExcetption(NO_SUBJECTS_MESSAGE);
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
                System.err.println(e.getMessage());
            }
        }
        return students;
    }

    public boolean containsGroup(Group group) {
        return groups.contains(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faculty faculty = (Faculty) o;
        return name.equals(faculty.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
