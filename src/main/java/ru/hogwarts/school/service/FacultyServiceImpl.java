package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService{
    private final Map<Long, Faculty> faculties;
    private long lastId = 0;


    public FacultyServiceImpl() {
        this.faculties = new HashMap<>();
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++lastId);
        faculties.put(lastId, faculty);
        return faculty;
    }

    @Override
    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    @Override
    public Faculty deleteFaculty (long id) {
        return faculties.remove(id);
    }

    @Override
    public Collection<Faculty> findFacultyByColor(String color) {
        ArrayList<Faculty> result = new ArrayList<>();
        for (Faculty faculty : faculties.values()){
            if (Objects.equals(faculty.getColor(), color)) {
                //faculty.getAge() == age) {
                result.add(faculty);
            }
        } return result;
    }

}
