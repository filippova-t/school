package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    Faculty editFaculty(Faculty faculty);

    void deleteFaculty(long id);

    Collection<Faculty> findFacultyByColor(String color);
}
