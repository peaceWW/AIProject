package com.example.ai_project.service;

import com.example.ai_project.dto.StudentDTO;
import com.example.ai_project.entity.Student;
import com.example.ai_project.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setStudentId(studentDTO.getStudentId());
        student.setEmail(studentDTO.getEmail());
        student.setPhone(studentDTO.getPhone());
        student.setMajor(studentDTO.getMajor());
        student.setGrade(studentDTO.getGrade());
        student.setStatus(studentDTO.getStatus());
        student.setCreatedAt(System.currentTimeMillis());
        student.setUpdatedAt(System.currentTimeMillis());

        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    public StudentDTO getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(this::convertToDTO).orElse(null);
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            student.setName(studentDTO.getName());
            student.setEmail(studentDTO.getEmail());
            student.setPhone(studentDTO.getPhone());
            student.setMajor(studentDTO.getMajor());
            student.setGrade(studentDTO.getGrade());
            student.setStatus(studentDTO.getStatus());
            student.setUpdatedAt(System.currentTimeMillis());

            Student updatedStudent = studentRepository.save(student);
            return convertToDTO(updatedStudent);
        }
        return null;
    }

    public boolean deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setStudentId(student.getStudentId());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());
        dto.setMajor(student.getMajor());
        dto.setGrade(student.getGrade());
        dto.setStatus(student.getStatus());
        dto.setCreatedAt(student.getCreatedAt());
        dto.setUpdatedAt(student.getUpdatedAt());
        return dto;
    }
}