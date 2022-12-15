package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;


import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultytInfo (@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty createFaculty (@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty (@RequestBody Faculty faculty) {
        if (facultyService.editFaculty(faculty) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.editFaculty(faculty));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty (@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Faculty> findFaculty(@RequestParam String nameOrColor) {
        return ResponseEntity.ok(facultyService.findFacultyByNameOrColor(nameOrColor));
    }

   @GetMapping("{id}/students")
    public ResponseEntity <Collection<Student>> getListStudentsOfFaculty
            (@PathVariable Long id) {

        return ResponseEntity.ok(facultyService.getListStudentsOfFaculty(id));

    }
}
