package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
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
        Faculty foundFaculty = studentService.getFacultyOfStudent(id);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundFaculty);

    }

    @PostMapping (value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity <String> uploadAvatar (@PathVariable Long id,
                                                 @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 100) {
            return ResponseEntity.badRequest().body("Слишком большой файл");
        }
        avatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar/fromDatabase")
    public ResponseEntity<byte[] > downloadAvatar (@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping (value = "/{id}/avatar")
    public void downloadAvatar (@PathVariable Long id,
                                HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((avatar.getFileSize().intValue()));
            is.transferTo(os);
        }
    }


}
