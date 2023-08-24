package ru.hogwarts.school.Service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exceptions.FacultyNotFoundException;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Repositories.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(Long id)  {
        return facultyRepository.findById(id).orElseThrow(()-> new FacultyNotFoundException("Faculty not found!"));
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public void removeFaculty(Long id) {
        facultyRepository.deleteById(id);

    }

    @Override
    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getAllFilteredByColor(String color) {
        return facultyRepository.findFacultiesByColorIgnoreCase(color);
    }

    @Override
    public void removeAllFaculty() {
        facultyRepository.deleteAll();
    }

    @Override
    public Collection<Faculty> findFacultyByNameAndColor(String name, String color) {
        return facultyRepository.findFacultiesByNameContainsIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public Collection<Faculty> findByName(String name) {
        return facultyRepository.findFacultiesByNameContainsIgnoreCase(name);
    }

    @Override
    public Collection<Student> findStudentByFaculty(Long facultyId) {
        return getFaculty(facultyId).getStudents();

    }

}