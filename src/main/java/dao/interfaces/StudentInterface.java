package dao.interfaces;

import java.sql.Connection;
import java.util.List;

import dao.models.Record;
import dao.models.Student;

public interface StudentInterface {

    boolean add(Student st);

    List<Student> search(String identifier);
    Student searchById(int id);
    List<Record> searchRecords(int id);

    boolean edit(Student st);
    void editRecord(Record record);

    boolean deleteById(int id);

    void setConnection(Connection conn);
    Connection getConnection();

}
