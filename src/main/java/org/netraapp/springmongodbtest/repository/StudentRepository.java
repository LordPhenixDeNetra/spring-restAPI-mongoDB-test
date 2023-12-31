package org.netraapp.springmongodbtest.repository;

import org.netraapp.springmongodbtest.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String> {

    Optional<Student> findStudentByEmail(String email);
}
