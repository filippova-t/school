package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> students;
    private long lastId = 0;

    public StudentServiceImpl() {
        this.students = new HashMap<>();
    }

    @Override
    public Student createStudent (Student student) {
        student.setId(++lastId);
        students.put(lastId, student);
        return student;
    }
    @Override
    public Student findStudent(long id) {
        return students.get(id);
    }

    @Override
    public Student editStudent(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    @Override
    public Student deleteStudent (long id) {
        return students.remove(id);
    }

    @Override
    public Collection<Student> findStudentByAge(int age) {
        ArrayList <Student> result = new ArrayList<>();
        for (Student student : students.values()){
            if (student.getAge() == age) {
                result.add(student);
        }
    } return result;
    }

}
