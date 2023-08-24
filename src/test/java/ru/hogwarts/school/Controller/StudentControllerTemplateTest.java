package ru.hogwarts.school.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Service.StudentService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentService studentService;


    private static Long id = 1L;
    private static String name = "Bob";
    private static int age = 22;

    @Test
    void contextLoads() {
        assertThat(studentController).isNotNull();
    }

    @Test
    void testGetStudentById() {
        Student student = new Student(id, name, age);
        studentService.createStudent(student);

        ResponseEntity<Student> response = testRestTemplate.getForEntity
                ("http://localhost:" + port + "/student/" + id, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getName()).isEqualTo(name);
        assertThat(response.getBody().getAge()).isEqualTo(age);
    }

    @Test
    void testGetStudentByIdNotFound() {
        Long idExist = 999L;

        ResponseEntity<Student> response = testRestTemplate.getForEntity
                ("http://localhost:" + port + "/student/" + idExist, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void testAddStudent() {
        Student student = new Student(id, name, age);

        ResponseEntity<Student> response = testRestTemplate.postForEntity
                ("http://localhost:" + port + "/student", student, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getName()).isEqualTo(name);
        assertThat(response.getBody().getAge()).isEqualTo(age);

        Student addedStudent = studentService.getStudent(id);
        assertThat(addedStudent).isNotNull();
        assertThat(addedStudent.getId()).isEqualTo(id);
        assertThat(addedStudent.getName()).isEqualTo(name);
        assertThat(addedStudent.getAge()).isEqualTo(age);
    }

    @Test
    void updateStudentById() {
        Student studentUpdate = new Student(id, name, age);

        studentService.createStudent(studentUpdate);

        Student uptadetStudent = new Student(id, "Pol", 91); //у нас всем возрастам рады :)

        ResponseEntity<Student> response = testRestTemplate.exchange
                ("http://localhost:" + port + "/student/" + id, HttpMethod.PUT, new HttpEntity<>(uptadetStudent), Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getName()).isEqualTo("Pol");
        assertThat(response.getBody().getAge()).isEqualTo(91);

        Student retrievedStudent = studentService.getStudent(id);
        assertThat(retrievedStudent).isNotNull();
        assertThat(retrievedStudent.getId()).isEqualTo(id);
        assertThat(retrievedStudent.getName()).isEqualTo("Pol");
        assertThat(retrievedStudent.getAge()).isEqualTo(91);
    }
}