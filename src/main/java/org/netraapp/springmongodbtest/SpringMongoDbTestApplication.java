package org.netraapp.springmongodbtest;

import org.netraapp.springmongodbtest.enums.Gender;
import org.netraapp.springmongodbtest.model.Address;
import org.netraapp.springmongodbtest.model.Student;
import org.netraapp.springmongodbtest.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@SpringBootApplication
public class SpringMongoDbTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMongoDbTestApplication.class, args);
    }

    @Bean
    CommandLineRunner start(StudentRepository studentRepository,
                            MongoTemplate mongoTemplate){
        return args -> {
            Address address = new Address(
                    "Afrique",
                    "Senegal",
                    "4908"
            );
            Student student = new Student(
                    "Moussa",
                    "THIOR",
                    "mousthior@gmail.com",
                    Gender.MALE,
                    address,
                    List.of("Computer Science", "Manga"),
                    BigDecimal.TEN,
                    LocalDateTime.now()
            );

            //usingMongoTemplateAndQuery(studentRepository, mongoTemplate, student);

            studentRepository.findStudentByEmail(student.getEmail())
                    .ifPresentOrElse(s -> {
                        System.out.println("Already exist : "+ s);
                    }, ()->{
                        System.out.println("Inserting student : "+ student);
                        studentRepository.save(student);
                    });

        };
    }

    private static void usingMongoTemplateAndQuery(StudentRepository studentRepository, MongoTemplate mongoTemplate, Student student) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(student.getEmail()));

        List<Student> students = mongoTemplate.find(query, Student.class);
        if (students.size() > 1){
            throw new IllegalStateException("Found many Student with email "+ student.getEmail());
        }
        if (students.isEmpty()){
            System.out.println("Inserting student : "+ student);
            studentRepository.save(student);
        }else {
            System.out.println("Already exist : "+ student);
        }
    }

}
