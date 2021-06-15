package com.github.valeryad.entities;

import com.github.valeryad.exceptions.NoStudentsInGroupException;

import java.util.*;

public class Group {
    private static final String NO_STUDENTS_IN_GROUP_MESSAGE = "There's no students in group \"%s\" of %s";

    private String name;
    private Faculty faculty;
    private Set<Student> students;


    public Group(String name, Faculty faculty) {
        this.name = name;
        this.faculty = faculty;
        students = new HashSet<Student>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addStudents(Collection<Student> students) {
        this.students.addAll(students);
        students.stream().forEach(student -> student.setFaculty(faculty));
    }

    public void addStudent(Student student) {
        students.add(student);
        student.setFaculty(faculty);
    }

    public boolean isStudentInGroup(Student student) {
        return students.contains(student);
    }

    public List<Student> getStudents() throws NoStudentsInGroupException {
        if (students.size() == 0) {
            throw new NoStudentsInGroupException(String.format(NO_STUDENTS_IN_GROUP_MESSAGE, name, faculty));
        }
        return new ArrayList<>(students);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return name.equals(group.getName()) && faculty.equals(group.faculty);
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + faculty.hashCode();
    }

    @Override
    public String toString() {
        return "\"Group " + name + "\"";
    }
}
