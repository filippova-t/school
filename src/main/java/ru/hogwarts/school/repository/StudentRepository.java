package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository <Student, Long> {
    Collection <Student> findByAge (int age);
    Collection <Student> findByAgeBetween(int min, int max);
    Collection <Student> findByFacultyId(Long facultyId);

    @Query(value = "SELECT COUNT (*) FROM student", nativeQuery = true)
    Long getCountOfStudents ();

    @Query (value = "SELECT AVG (age) as averageAge FROM student", nativeQuery = true)
    Double getAverageAge ();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true )
    List<Student> getListFiveLastIdStudents ();

    @Query(value = "SELECT NAME FROM student ORDER BY id", nativeQuery = true )
    List<String> getNamesStudentsOrderById ();
}
