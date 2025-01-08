package com.foodmanagement.Service.impl;


import com.foodmanagement.Entity.Student;
import com.foodmanagement.Repository.StudentRepository;
import com.foodmanagement.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;


    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setName(studentDetails.getName());
            student.setPhoneNumber(studentDetails.getPhoneNumber());
            student.setMail(studentDetails.getMail());
            student.setStatus(studentDetails.getStatus());
            student.setUpdatedBy(studentDetails.getUpdatedBy());
            return studentRepository.save(student);
        }
        return null;
    }

    @Override
    public boolean deleteStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
