package ru.hogwarts.school.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.Model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findFacultiesByColorIgnoreCase(String color);

    Collection<Faculty> findFacultiesByNameContainsIgnoreCaseOrColorIgnoreCase(String name, String color);

    Collection<Faculty> findFacultiesByNameContainsIgnoreCase(String name);

    void deleteAll();
}