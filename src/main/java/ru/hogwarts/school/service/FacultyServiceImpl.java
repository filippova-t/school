package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FacultyServiceImpl implements FacultyService{


    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    private final Logger logger = LoggerFactory.getLogger (FacultyServiceImpl.class);

    @Override
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(null);
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for find faculty");
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        if (facultyRepository.existsById(faculty.getId())) {
        return facultyRepository.save(faculty);
        }
        logger.error ("There is not faculty with id = " + faculty.getId());
        return null;
    }

    @Override
    public void deleteFaculty (long id) {
        facultyRepository.deleteById(id);
        logger.info("Was invoked method for delete faculty");
    }



    @Override
    public Collection <Faculty> findFacultyByNameOrColor(String nameOrColor) {
        logger.info("Was invoked method for find faculties by name or color");
        return facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(nameOrColor, nameOrColor);
    }
    @Override
    public Collection<Student> getListStudentsOfFaculty(Long id) {
        logger.info("Was invoked method for get list students of faculty");
        return studentRepository.findByFacultyId(id);

    }

    @Override
    public Optional<String> getLongestNameOfFaculty() {
        return facultyRepository.findAll().stream()
                .map (faculty -> faculty.getName())
                .max (Comparator.comparingInt(String :: length));
    }

    @Override
    public Integer getSum () {
        long time = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                //.parallel()
                .reduce(0, (a, b) -> a + b );
        time = System.currentTimeMillis() - time;
        logger.info ("time = {}", time);
        return sum;

    }



}
