package org.netraapp.springmongodbtest.Service;

import lombok.AllArgsConstructor;
import org.netraapp.springmongodbtest.model.Student;
import org.netraapp.springmongodbtest.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }
}
