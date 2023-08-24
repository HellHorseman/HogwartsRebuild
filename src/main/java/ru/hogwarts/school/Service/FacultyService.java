package ru.hogwarts.school.Service;

import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;

import java.util.Collection;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Faculty getFaculty(Long id);

    Faculty updateFaculty(Faculty faculty);

    void removeFaculty(Long id);

    Collection<Faculty> getAll();

    Collection<Faculty> getAllFilteredByColor(String color);

    void removeAllFaculty();

    Collection<Faculty> findFacultyByNameAndColor(String name, String color);

    Collection<Faculty> findByName(String name);

    Collection<Student> findStudentByFaculty(Long facultyId);

}
