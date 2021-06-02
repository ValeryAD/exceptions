package com.github.valeryad.entities;

import com.github.valeryad.exceptions.NoStudentsInGroupException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Group {
    private String name;
    private Set<Student> students;

    public Group(String name) {
        this.name = name;
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
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public boolean isStudentInGroup(Student student) {
        return students.contains(student);
    }

    public Set<Student> getStudents() throws NoStudentsInGroupException {
        if(students.size() == 0){
            throw new NoStudentsInGroupException("Theres no students in group " + name);
        }
        return students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return name.equals(group.getName()) && students.equals(group.students);
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + students.hashCode();
    }
}
