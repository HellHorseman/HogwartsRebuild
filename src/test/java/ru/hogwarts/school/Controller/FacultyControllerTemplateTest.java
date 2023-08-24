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
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Service.FacultyService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTemplateTest {


    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private FacultyService facultyService;


    private static Long id = 1L;
    private static String name = "Blowers";
    private static String color = "yellow";

    @Test
    void contextLoads() {
        assertThat(facultyController).isNotNull();
    }

    @Test
    void testPostCreatedNewFaculty() {
        Faculty facultyToAdd = new Faculty();
        facultyToAdd.setName(name);
        facultyToAdd.setColor(color);

        ResponseEntity<Faculty> response = testRestTemplate.postForEntity
                ("http://localhost:" + port + "/faculty", facultyToAdd, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Faculty createdFaculty = response.getBody();
        assertThat(createdFaculty).isNotNull();
        assertThat(createdFaculty.getId()).isNotNull();
        assertThat(createdFaculty.getName()).isEqualTo(name);
        assertThat(createdFaculty.getColor()).isEqualTo(color);

        Faculty fetchedFaculty = facultyService.getFaculty(createdFaculty.getId());
        assertThat(fetchedFaculty).isNotNull();
        assertThat(fetchedFaculty.getName()).isEqualTo(name);
        assertThat(fetchedFaculty.getColor()).isEqualTo(color);
    }

    @Test
    void testGetFacultyById() {
        Faculty facultyToAdd = new Faculty();
        facultyToAdd.setName(name);
        facultyToAdd.setColor(color);

        ResponseEntity<Faculty> postResponse = testRestTemplate.postForEntity
                ("http://localhost:" + port + "/faculty", facultyToAdd, Faculty.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty createdFaculty = postResponse.getBody();

        ResponseEntity<Faculty> getResponse = testRestTemplate.getForEntity
                ("http://localhost:" + port + "/faculty/" + createdFaculty.getId(), Faculty.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty fetchedFaculty = getResponse.getBody();

        assertThat(fetchedFaculty).isNotNull();
        assertThat(fetchedFaculty.getId()).isEqualTo(createdFaculty.getId());
        assertThat(fetchedFaculty.getName()).isEqualTo(name);
        assertThat(fetchedFaculty.getColor()).isEqualTo(color);
    }

    @Test
    void testGetFacultyByIdNotFound() {
        Long notExistId = 999L;

        ResponseEntity<Faculty> response = testRestTemplate.getForEntity
                ("http://localhost:" + port + "/faculty/" + notExistId, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    void testFacultyDeleteById() {

        ResponseEntity<Void> response = testRestTemplate.exchange
                ("http://localhost:" + port + "/faculty/" + id, HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);

    }

    @Test
    void testUpdateFacultyById() {
        Faculty facultyUpdate = new Faculty(id, "Lala", "Red");
        facultyService.createFaculty(new Faculty(id, "Waterfall", "Black"));

        ResponseEntity<Faculty> response = testRestTemplate.exchange
                ("http://localhost:" + port + "/faculty/" + id, HttpMethod.PUT, new HttpEntity<>(facultyUpdate), Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);

        Faculty updatedFaculty = facultyService.getFaculty(id);
        assertThat(updatedFaculty).isNotNull();
        assertThat(updatedFaculty.getId()).isEqualTo(id);
        assertThat(updatedFaculty.getName()).isEqualTo("Lala");
        assertThat(updatedFaculty.getColor()).isEqualTo("Red");
    }


}