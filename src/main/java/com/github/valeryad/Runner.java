package com.github.valeryad;

import com.github.valeryad.actions.StudentCreator;
import com.github.valeryad.entities.*;
import com.github.valeryad.exceptions.*;
import org.w3c.dom.ls.LSOutput;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Runner {
    public static final int GRADES_AMOUNT = 5;
    private static Random random;

    static {
        random = new Random();
    }

    public static void main(String[] args) {
        University university = University.getInstance();

        createFacultiesAndAssignSubjects(university);
        createGroups(university);
        createStudents(university);
        assignSubjectsToStudents(university);
        giveGradesForEachStudent(university);

        countAndPrintAverageGradeOfStudent(university);
        countAndPrintAverageGradeInSubjectOfGroupOfFaculty(university);
    }

    private static void createFacultiesAndAssignSubjects(University university) {
        try {
            university.getFaculties();
        } catch (NoFacultiesInUniversityException e) {
            System.err.println(e.getMessage());
        }

        Faculty faculty = new Faculty("Faculty of Applied Mathematics and Computer Science");
        faculty.assignSubjects(EnumSet.range(Subjects.MATHEMATICAL_ANALYSIS, Subjects.COMPUTER_NETWORKS));
        university.addFaculty(faculty);
        faculty = new Faculty("Faculty of History");
        faculty.assignSubjects(EnumSet.range(Subjects.FOREIGN_LANGUAGE, Subjects.THEORY_AND_PRACTICE_OF_INTERCULTURAL_COMMUNICATIONS));
        university.addFaculty(faculty);
        faculty = new Faculty("Faculty of law");
        faculty.assignSubjects(EnumSet.range(Subjects.GENERAL_THEORY_OF_LAW, Subjects.CRIMINOLOGY));
        university.addFaculty(faculty);
    }

    private static void createGroups(University university) {
        try {
            university.getFaculties().get(0).getGroups();
        } catch (NoFacultiesInUniversityException | NoGroupsInFacultyException e) {
            System.err.println(e.getMessage());
        }

        try {
            for (Faculty faculty : university.getFaculties()) {
                for (int i = 0; i < random.nextInt(3) + 2; i++) {
                    char groupName = 'A';
                    faculty.addGroup(new Group(String.valueOf((char) (groupName + i)), faculty));
                }
            }
        } catch (NoFacultiesInUniversityException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void createStudents(University university) {
        try {
            university.getFaculties().get(0).getGroups().get(0).getStudents();
        } catch (NoFacultiesInUniversityException | NoGroupsInFacultyException | NoStudentsInGroupException e) {
            System.err.println(e.getMessage());
        }

        try {
            for (Faculty faculty : university.getFaculties()) {
                for (Group group : faculty.getGroups()) {
                    group.addStudents(new StudentCreator().createStudents(random.nextInt(6) + 10));
                }
            }
        } catch (NoFacultiesInUniversityException | NoGroupsInFacultyException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void assignSubjectsToStudents(University university) {
        try {
            university.getFaculties().get(0).getGroups().get(0).getStudents().get(0).getSubjects();
        } catch (NoFacultiesInUniversityException | NoGroupsInFacultyException
                | NoStudentsInGroupException | NoSubjectsExcetption e) {
            System.err.println(e.getMessage());
        }

        try {
            for (Faculty faculty : university.getFaculties()) {
                for (Group group : faculty.getGroups()) {
                    for (Student student : group.getStudents()) {
                        student.assignSubjects(faculty.getSubjects());
                    }
                }
            }
        } catch (NoFacultiesInUniversityException | NoGroupsInFacultyException
                | NoStudentsInGroupException | NoSubjectsExcetption e) {
            System.err.println(e.getMessage());
        }

    }


    private static void giveGradesForEachStudent(University university) {
        try {
            Student student = university.getFaculties().get(0).getGroups().get(0).getStudents().get(0);
            student.giveGrade(student.getSubjects().get(0), Student.MAX_GRADE + 1);
        } catch (NoFacultiesInUniversityException | NoGroupsInFacultyException
                | NoStudentsInGroupException | NoSubjectsExcetption
                | IncorrectGradeException e) {
            System.err.println(e.getMessage());
        }


        try {
            for (Faculty faculty : university.getFaculties()) {
                for (Group group : faculty.getGroups()) {
                    for (Student student : group.getStudents()) {
                        giveRandomGrades(student);
                    }
                }
            }
        } catch (NoFacultiesInUniversityException | NoGroupsInFacultyException
                | NoStudentsInGroupException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void giveRandomGrades(Student student) {
        try {
            for (Subjects subject : student.getSubjects()) {
                for (int i = 0; i < GRADES_AMOUNT; i++) {
                    student.giveGrade(subject, random.nextInt(Student.MAX_GRADE-3) + 4);
                }
            }
        } catch (NoSubjectsExcetption | IncorrectGradeException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void countAndPrintAverageGradeOfStudent(University university) {
        Student firstStudent = null;

        try {
            firstStudent = university.getFaculties().get(0).getGroups().get(0).getStudents().get(0);
        } catch (NoStudentsInGroupException | NoFacultiesInUniversityException | NoGroupsInFacultyException e) {
            e.printStackTrace();
        }

        System.out.printf("Student %s has average grade: %2.2f\n",
                firstStudent, university.countAverageGradeOfStudent(firstStudent));
    }

    //TODO Check this method
    private static void countAndPrintAverageGradeInSubjectOfGroupOfFaculty(University university) {
        Faculty faculty = null;
        Group group = null;
        Subjects subject = null;


        try {
            faculty = university.getFaculties().get(0);
            group = faculty.getGroups().get(0);
            System.out.println(faculty.getGroups().contains(group));
            Set<Group> set = new HashSet<>(faculty.getGroups());
            System.out.println(set.contains(group));

            System.out.println("!" + set.equals(faculty.getGroups()));
            subject = faculty.getSubjects().stream().findAny().get();

            for(Group tempGroup : set){
                System.out.println(tempGroup);
            }

            for(Group tempGroup : faculty.getGroups()){
                System.out.println(tempGroup);
            }

        } catch (NoFacultiesInUniversityException | NoGroupsInFacultyException | NoSubjectsExcetption e) {
            System.err.println(e.getMessage());
        }


        if(faculty.containsGroup(group)){
            System.out.printf("Average grade in subject \"%s\" of group %s of faculty \"%s\" is %2.2f",
                    subject, group, faculty,
                    university.countAverageGradeInSubjectOfGroup(group, subject));
        }else{
            System.out.printf("There's no group %s in faculty \"%s\"\n", group, faculty);
        }

    }

    // todo Make the last method
}
