package com.foodmanagement.Service;


import com.foodmanagement.Entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface StudentService {
    Student saveStudent(Student student);

    Page<Student> getAllStudents(Pageable pageable);

    Optional<Student> getStudentById(Long id);

    Student updateStudent(Long id, Student studentDetails);

    boolean deleteStudent(Long id);
}

