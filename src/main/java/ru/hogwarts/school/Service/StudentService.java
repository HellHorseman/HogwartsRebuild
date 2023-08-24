package ru.hogwarts.school.Service;

import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;

import java.util.Collection;

public interface StudentService {

    Student createStudent(Student student);

    Student getStudent(Long id);

    Student updateStudent(Student student);

    void removeStudent(Long id);

    Collection<Student> getAll();

    Collection<Student> getAllFilteredByAge(int age);
    void removeAllStudent();
    Collection<Student> findByAgeBetween(int min, int max);

    Faculty findFacultyByStudent(Long id);

}