package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Optional;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    Faculty editFaculty(Faculty faculty);

    void deleteFaculty(long id);

    Collection <Faculty> findFacultyByNameOrColor(String nameOrColor);

    Collection<Student> getListStudentsOfFaculty(Long id);


    Optional<String> getLongestNameOfFaculty();

    Integer getSum();
}
