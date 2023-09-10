package dao.mybatis.interfaces;

import java.util.List;

import dao.models.*;
import web.dto.StudentSearchDTO;

public interface StudentMapper {

    int addStudent(Student student);
    int removeStudent(int id);
    int editStudent(Student student);
    List<Student> selectAll();
    List<Student> search(StudentSearchDTO ssd);
    List<Student> searchStudentById(int id);
}
