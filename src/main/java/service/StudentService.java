package service;

import java.util.List;

import dao.models.Student;
import web.dto.StudentSearchDTO;

public interface StudentService {
    public boolean addStudent(Student st);
    public List<Student> searchStudent(StudentSearchDTO ssd);
    Student searchStudentById(int id);
    public boolean deleteStudent(int id);
    public boolean editStudent(Student st);

}
