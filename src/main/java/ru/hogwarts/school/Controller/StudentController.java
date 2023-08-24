package ru.hogwarts.school.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }



    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student foundStudent = studentService.updateStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = {"age"})
    public Collection<Student> getAllFilteredByAge(@RequestParam (name = "age", required = false) int age) {
        return studentService.getAllFilteredByAge(age);
    }

    @GetMapping(params = {"min", "max"})
    public ResponseEntity<Collection<Student>> findByAgeBetween(@RequestParam(required = false) int min,
                                                                @RequestParam(required = false) int max) {
        if (min >= max || min <= 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
    }

    @DeleteMapping("/delete-all")
    public String deleteAllFaculty() {
        studentService.removeAllStudent();
        return "All students have been deleted.";
    }

    @GetMapping("/faculty")
    public ResponseEntity<Faculty> findFacultyByStudent(@RequestParam Long id) {
        Faculty faculty = studentService.findFacultyByStudent(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }


    @GetMapping
    public Collection<Student> getAll() {
        return studentService.getAll();
    }
}
