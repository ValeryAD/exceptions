package com.github.valeryad;

import com.github.valeryad.entities.Faculty;
import com.github.valeryad.entities.University;
import com.github.valeryad.exceptions.NoFacultiesInUniversityException;

public class Runner {
    public static void main(String[] args) {
        University university = University.getInstance();

        try {
            university.getFaculties();
        } catch (NoFacultiesInUniversityException e) {
            System.err.println(e.getMessage());
        }

        Faculty faculty = new Faculty("Faculty of Applied Mathematics and Computer Science");
        university.addFaculty(faculty);
        faculty = new Faculty("Faculty of History");
        university.addFaculty(faculty);
        faculty = new Faculty("Faculty of law");
        university.addFaculty(faculty);


        System.out.println("Hello");
    }
}
