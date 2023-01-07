package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private final Logger logger = LoggerFactory.getLogger (StudentServiceImpl.class);

    @Override
    public Student createStudent(Student student) {
        student.setId(null);
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(long id) {
        logger.info("Was invoked method for find student");
        return studentRepository.findById(id).orElse(null);

    }

    @Override
    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student");
        if (studentRepository.existsById(student.getId())) {
            return studentRepository.save(student);
        }
        logger.error ("There is not student with id = " + student.getId());
        return null;
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
        logger.info("Was invoked method for delete student");
    }

    @Override
    public Collection<Student> findStudentByAge(int age) {
        logger.info("Was invoked method for find students by age");
        return studentRepository.findByAge(age);

    }

    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for find students by age between two values");
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getFacultyOfStudent(Long id) {
        Student foundStudent = studentRepository.findById(id).orElse(null);
        if (foundStudent == null) {
            logger.error ("There is not student with id = " + id);
            return null;
        }
        logger.info("Was invoked method for get faculty of student");
        return foundStudent.getFaculty();
    }

    @Override
    public Long getCountOfStudents() {
        logger.info("Was invoked method for get count of students");
        return studentRepository.getCountOfStudents();
    }

    @Override
    public Double getAverageAge() {
        logger.info("Was invoked method for get average age of students");
        return studentRepository.getAverageAge();
    }

    @Override
    public List<Student> getListFiveLastIdStudents() {
        logger.info("Was invoked method for get list of five last students id");
        return studentRepository.getListFiveLastIdStudents();
    }

    @Override
    public Collection<String> getStudentsNamesStartWithA (){
        return studentRepository.findAll().stream()
                .map (student -> student.getName())
                .map (s -> s.toUpperCase())
                .filter (e -> e.startsWith("A"))
                .sorted (Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageAgeOfStudents () {
        return studentRepository.findAll().stream()
                .mapToInt (student -> student.getAge())
                .average()
                .orElse(0);
    }

    @Override
    public void printNames1() {
        List<String> names = studentRepository.getNamesStudentsOrderById();

        System.out.println(names.get(0));
        System.out.println(names.get(1));

        new Thread(() -> {
            System.out.println(names.get(1));
            System.out.println(names.get(2));
        }).start();

        new Thread(() -> {
            System.out.println(names.get(4));
            System.out.println(names.get(5));
        }).start();

    }

    @Override
    public void printNames2() {

        print(0);
        print(1);


        new Thread(() -> {
            print(2);
            print(3);
        }).start();

        new Thread(() -> {
           print(3);
           print(4);
        }).start();

    }

    public synchronized void print (int num) {

        List<String> names = studentRepository.getNamesStudentsOrderById();

        System.out.println(names.get(num));
    }
}
