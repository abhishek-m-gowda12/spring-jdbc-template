package com.abhishek.springjdbctemplate.service;

import com.abhishek.springjdbctemplate.dto.Student;
import com.abhishek.springjdbctemplate.repository.StudentRepository;
import com.abhishek.springjdbctemplate.repository.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudent() {
        return studentRepository.getAllStudent(new StudentMapper());
    }

    public void addStudent(Student student) {
        studentRepository.addStudent(student);
    }

    public void addAllStudents(List<Student> students) {
        studentRepository.addAllStudents(students);
    }

    public Student getStudentById(String id) {
        return studentRepository.getStudentById(id);
    }
}
