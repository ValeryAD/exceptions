package com.github.valeryad.actions;

import com.github.valeryad.entities.Student;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class StudentCreator {
    private Random random;

    public StudentCreator(){
        random = new Random();
    }

    public List<Student> createStudents(int amount) {
        List<Student> students = new LinkedList<>();
        for(int i = 0; i < amount; i++){
            students.add(createStudent());
        }
        return students;
    }

    public Student createStudent(){
        Student student = new Student();
        fillNames(student);
        return student;
    }

    private void fillNames(Student student) {
        boolean isMale = random.nextBoolean();

        String[] maleNames = {"Валерий", "Дмитрий", "Владимир",
                "Андрей", "Никита", "Павел", "Александр",
                "Виталий", "Валентин", "Георгий", "Анатолий"};
        String[] femaleNames = {"Ольга", "Вера", "Наталья", "Александра",
                "Валентина", "Галина", "Мальвина", "Нина", "Оксана"};

        String[] maleLastNames = {"Иванов", "Петров", "Cидоров", "Колбасов", "Гончаров",
                "Селезнев", "Пушкарев", "Пушкин", "Пугачев", "Свердлов", "Хрущев",
                "Лещев", "Помидоров", "Картунков", "Соловьев", "Комаров"};

        if (isMale) {
            student.setName(maleNames[random.nextInt(maleNames.length)]);
            student.setLastName(maleLastNames[random.nextInt(maleLastNames.length)]);
        } else {
            student.setName(femaleNames[random.nextInt(femaleNames.length)]);
            student.setLastName(maleLastNames[random.nextInt(maleLastNames.length)] + "а");
        }
    }
}
