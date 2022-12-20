package ru.hogwarts.school;


import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarServiceImpl;
import ru.hogwarts.school.service.FacultyServiceImpl;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentTests {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private StudentRepository studentRepository;

        @MockBean
        private AvatarRepository avatarRepository;

        @MockBean
        private FacultyRepository facultyRepository;


        @SpyBean
        private StudentServiceImpl studentService;

        @SpyBean
        private AvatarServiceImpl avatarService;

        @SpyBean
        private FacultyServiceImpl facultyService;


        @InjectMocks
        private StudentController studentController;


        @Test
        public void testStudents() throws Exception {
                final long id = 1;
                final String name = "testStudent";
                final int age = 21;
                final long idOfFaculty = 2;
                final String nameOfFaculty = "testFaculty";
                final String colorOfFaculty = "testColor";
                final Faculty faculty = new Faculty(idOfFaculty, nameOfFaculty, colorOfFaculty);
                final int min = 20;
                final int max = 22;



                JSONObject studentObject = new JSONObject();
                studentObject.put("id", id);
                studentObject.put("name", name);
                studentObject.put("age", age);
                studentObject.put("faculty", faculty);

                Student student = new Student();
                student.setId(id);
                student.setName(name);
                student.setAge(age);
                student.setFaculty(faculty);

                when(studentRepository.save(any(Student.class))).thenReturn(student);
                when(studentRepository.findByAgeBetween(min , max)).thenReturn(Collections.singleton(student));
                when(studentRepository.findById(id)).thenReturn(Optional.of(student));
                when(studentRepository.existsById(id)).thenReturn(true);

                mockMvc.perform(MockMvcRequestBuilders
                                .post("/student")
                                .content(studentObject.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(id))
                        .andExpect(jsonPath("$.name").value(name))
                        .andExpect(jsonPath("$.age").value(age));

                mockMvc.perform(MockMvcRequestBuilders
                                .put("/student")
                                .content(studentObject.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(id))
                        .andExpect(jsonPath("$.name").value(name))
                        .andExpect(jsonPath("$.age").value(age));

                mockMvc.perform(MockMvcRequestBuilders
                                .get("/student/" + id)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(id))
                        .andExpect(jsonPath("$.name").value(name))
                        .andExpect(jsonPath("$.age").value(age));

                mockMvc.perform(MockMvcRequestBuilders
                                .get("/student?min=" + min + "&max=" + max)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].id").value(id))
                        .andExpect(jsonPath("$[0].name").value(name))
                        .andExpect(jsonPath("$[0].age").value(age));

                mockMvc.perform(MockMvcRequestBuilders
                                .get("/student/" + id + "/faculty")
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(idOfFaculty))
                        .andExpect(jsonPath("$.name").value(nameOfFaculty))
                        .andExpect(jsonPath("$.color").value(colorOfFaculty));

                mockMvc.perform(MockMvcRequestBuilders
                                .delete("/student/" + id)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
        }

}




