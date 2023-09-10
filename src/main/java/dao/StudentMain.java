package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.jdbc.StudentJDBC;
import dao.models.Record;
import dao.models.Student;

public class StudentMain {

    public static void main(String[] args) throws SQLException {
        Connector c = new Connector(SQLCommands.URL, SQLCommands.USERNAME, SQLCommands.PASSWORD);
        Connection conn = c.getConnection();
        Student test = 
        new Student("Kale", 72, 12, new Date(282983920), 1, "kale st","2-3");
        test.setRecords(new ArrayList<Record>());
        StudentJDBC s = new StudentJDBC();
        s.setConnection(conn);
    }

}