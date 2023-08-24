package ru.hogwarts.school.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id) {
        Faculty faculty = facultyService.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable Long id) {
        facultyService.removeFaculty(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.updateFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("/delete-all")
    public String deleteAllFaculty() {
        facultyService.removeAllFaculty();
        return "All faculties have been deleted.";
    }

    @GetMapping(params = {"color"})
    public Collection<Faculty> getAllFilteredByColor(@RequestParam(name = "color", required = false) String color) {
        return facultyService.getAllFilteredByColor(color);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String color) {
        Collection<Faculty> faculties;
        if ((name != null && !name.isBlank() && (color != null && !color.isBlank()))) {
            faculties = facultyService.findFacultyByNameAndColor(name, color);
        } else if (name != null && !name.isBlank()) {
            faculties = facultyService.findByName(name);
        } else if (color != null && !color.isBlank()) {
            faculties = facultyService.getAllFilteredByColor(color);
        } else {
            faculties = facultyService.getAll();
        }

        if (faculties.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/student")
    public ResponseEntity<Collection<Student>> findStudentByFaculty(@RequestParam Long facultyId) {
        Collection<Student> studentByFaculty = facultyService.findStudentByFaculty(facultyId);
        if (studentByFaculty.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentByFaculty);
    }

}