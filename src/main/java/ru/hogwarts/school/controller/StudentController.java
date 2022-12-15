package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;


import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo (@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent (@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent (@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent (@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();

    }


    @GetMapping
    public ResponseEntity<Collection<Student>> findStudentByAgeBetween
            (@RequestParam int min, @RequestParam int max) {
        if (min >= max || min < 0 || max <=0) {
            ResponseEntity.badRequest().build();
        }
        return  ResponseEntity.ok(studentService.findByAgeBetween(min, max));

    }

    @GetMapping("{id}/faculty")
    public ResponseEntity <Faculty> getFacultyOfStudent
            (@PathVariable Long id) {
        if (studentService.getFacultyOfStudent(id) == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(studentService.getFacultyOfStudent(id));

    }

}
