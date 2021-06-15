package com.github.valeryad;

import com.github.valeryad.actions.StudentCreator;
import com.github.valeryad.entities.*;
import com.github.valeryad.exceptions.*;

import java.util.EnumSet;
import java.util.Random;

public class Runner {
    private static final String EXCEPTION_DEMO_MESSAGE = "Demonstration of catching exception: ";
    private static final int GRADES_AMOUNT = 5;
    private static final String MATHEMATICS_FACULTY_NAME = "Faculty of Applied Mathematics and Computer Science";
    private static final String HISTORY_FACULTY_NAME = "Faculty of History";
    private static final String LAW_FACULTY_NAME = "Faculty of law";
    private static final int MAX_GROUP_AMOUNT = 5;
    private static final int MIN_GROUP_AMOUNT = 2;
    private static final int MIN_STUDENTS_AMOUNT = 10;
    private static final int MAX_STUDENTS_AMOUNT = 16;
    private static final int MIN_ACTUAL_GRADE = 5;
    private static final String AVERAGE_GRADE_OF_STUDENT_REPORT = "TASK 1:%n" +
            "Student %s has average grade: %2.2f\n";
    public static final String AVERAGE_GRADE_IN_SUBJECT_OF_GROUP_OF_FACULTY_REPORT = "TASK 2:%n" +
            "Average grade in subject \"%s\" of group %s of faculty \"%s\" is %2.2f\n";
    public static final String AVERAGE_GRADE_IN_SUBJECT_OF_UNIVERSITY_REPORT = "TASK 3:%n" +
            "Average grade in subject \"%s\" of all university is %2.2f";

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
        countAndPrintAverageGradeInSubjectOfUniversity(university);
    }

    private static void createFacultiesAndAssignSubjects(University university) {
        try {
            university.getFaculties();
        } catch (NoFacultiesInUniversityException e) {
            System.err.println(EXCEPTION_DEMO_MESSAGE + e.getMessage() + "\n");
        }

        Faculty faculty = new Faculty(MATHEMATICS_FACULTY_NAME);
        faculty.assignSubjects(EnumSet.range(Subjects.MATHEMATICAL_ANALYSIS, Subjects.COMPUTER_NETWORKS));
        university.addFaculty(faculty);
        faculty = new Faculty(HISTORY_FACULTY_NAME);
        faculty.assignSubjects(EnumSet.range(Subjects.FOREIGN_LANGUAGE, Subjects.THEORY_AND_PRACTICE_OF_INTERCULTURAL_COMMUNICATIONS));
        university.addFaculty(faculty);
        faculty = new Faculty(LAW_FACULTY_NAME);
        faculty.assignSubjects(EnumSet.range(Subjects.GENERAL_THEORY_OF_LAW, Subjects.CRIMINOLOGY));
        university.addFaculty(faculty);
    }

    private static void createGroups(University university) {
        try {
            university.getFaculties().get(0).getGroups();
        } catch (NoFacultiesInUniversityException | NoGroupsInFacultyException e) {
            System.err.println(EXCEPTION_DEMO_MESSAGE + e.getMessage() + "\n");
        }

        try {
            for (Faculty faculty : university.getFaculties()) {
                for (int i = 0; i < random.nextInt(MAX_GROUP_AMOUNT - MIN_GROUP_AMOUNT) + MIN_GROUP_AMOUNT; i++) {
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
            System.err.println(EXCEPTION_DEMO_MESSAGE + e.getMessage() + "\n");
        }

        try {
            for (Faculty faculty : university.getFaculties()) {
                for (Group group : faculty.getGroups()) {
                    group.addStudents(new StudentCreator().createStudents(random.
                            nextInt(MAX_STUDENTS_AMOUNT - MIN_STUDENTS_AMOUNT) + MIN_STUDENTS_AMOUNT));
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
            System.err.println(EXCEPTION_DEMO_MESSAGE + e.getMessage() + "\n");
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
            System.err.println(EXCEPTION_DEMO_MESSAGE + e.getMessage() + "\n");
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
                    student.giveGrade(subject,
                            random.nextInt(Student.MAX_GRADE - MIN_ACTUAL_GRADE + 1) + MIN_ACTUAL_GRADE);
                }
            }
        } catch (NoSubjectsExcetption | IncorrectGradeException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void countAndPrintAverageGradeOfStudent(University university) {
        Student student = null;

        try {
            student = university.getFaculties().get(0).getGroups().get(0).getStudents().get(0);
        } catch (NoStudentsInGroupException | NoFacultiesInUniversityException | NoGroupsInFacultyException e) {
            System.err.println(e.getMessage());
        }
            System.out.printf(AVERAGE_GRADE_OF_STUDENT_REPORT,
                    student, university.countAverageGradeOfStudent(student));


    }

    private static void countAndPrintAverageGradeInSubjectOfGroupOfFaculty(University university) {
        Faculty faculty = null;
        Group group = null;
        Subjects subject = null;

        try {
            faculty = university.getFaculties().get(0);
            group = faculty.getGroups().get(0);
            subject = group.getStudents().get(0).getSubjects().get(0);

        } catch (NoFacultiesInUniversityException | NoGroupsInFacultyException |
                NoSubjectsExcetption | NoStudentsInGroupException e) {
            System.err.println(e.getMessage());
        }

            System.out.printf(AVERAGE_GRADE_IN_SUBJECT_OF_GROUP_OF_FACULTY_REPORT,
                    subject, group, faculty,
                    university.countAverageGradeInSubjectOfGroup(group, subject));
    }

    private static void countAndPrintAverageGradeInSubjectOfUniversity(University university){
        Subjects randomSubject = Subjects.values()[random.nextInt(Subjects.values().length)];
        System.out.printf(AVERAGE_GRADE_IN_SUBJECT_OF_UNIVERSITY_REPORT,
                randomSubject, university.countAverageGradeInSubjectOfUniversity(randomSubject));
    }
}
