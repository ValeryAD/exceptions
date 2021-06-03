package com.github.valeryad.entities;

import com.github.valeryad.exceptions.NoFacultiesInUniversityException;

import java.util.HashSet;
import java.util.Set;

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

    public void addFaculty(Faculty faculty){
        faculties.add(faculty);
    }

    public Set<Faculty> getFaculties() throws NoFacultiesInUniversityException {
        if(faculties.size() == 0){
            throw new NoFacultiesInUniversityException("There's no any faculty in the university");
        }

        return faculties;
    }

}
