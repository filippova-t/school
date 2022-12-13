package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent (Student student) {
        return studentRepository.save(student);
    }
    @Override
    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent (long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> findStudentByAge(int age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection <Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getFacultyOfStudent (Long studentId) {
        return studentRepository.findById(studentId).get().getFaculty();

    }

}
