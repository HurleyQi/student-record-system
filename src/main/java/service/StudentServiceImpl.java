package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Connector;
import dao.SQLCommands;
import dao.interfaces.StudentInterface;
import dao.jdbc.StudentJDBC;
import dao.models.Student;
import web.dto.StudentSearchDTO;

public class StudentServiceImpl implements StudentService {

    @Override
    public boolean addStudent(Student st) {
        Connector c = new Connector(SQLCommands.URL, SQLCommands.USERNAME, SQLCommands.PASSWORD);
        Connection conn = c.getConnection();
        try {
            StudentInterface sJ = new StudentJDBC();
            conn.setAutoCommit(false);
            boolean result;
            sJ.setConnection(conn);
            result = sJ.add(st);
            if (result) {
                conn.commit();
                conn.setAutoCommit(true);
            } 
            conn.close();
            return result;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public List<Student> searchStudent(StudentSearchDTO ssd) {
        Connector c = new Connector(SQLCommands.URL, SQLCommands.USERNAME, SQLCommands.PASSWORD);
        Connection conn = c.getConnection();
        try {
            StudentInterface sJ = new StudentJDBC();
            conn.setAutoCommit(false);
            sJ.setConnection(conn);
            List<Student> result = new ArrayList<>();
            String sqlIdentifier = getSQLCommand(ssd);
            result.addAll(sJ.search(sqlIdentifier));
            conn.commit();
            conn.setAutoCommit(true);
            conn.close();
            return result;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public Student searchStudentById(int id) {
        Connector c = new Connector(SQLCommands.URL, SQLCommands.USERNAME, SQLCommands.PASSWORD);
        Connection conn = c.getConnection();
        try {
            StudentInterface sJ = new StudentJDBC();
            conn.setAutoCommit(false);
            sJ.setConnection(conn);
            Student result = sJ.searchById(id);
            conn.commit();
            conn.setAutoCommit(true);
            conn.close();
            return result;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public boolean deleteStudent(int id) {
        Connector c = new Connector(SQLCommands.URL, SQLCommands.USERNAME, SQLCommands.PASSWORD);
        Connection conn = c.getConnection();
        try {
            StudentInterface sJ = new StudentJDBC();
            conn.setAutoCommit(false);
            sJ.setConnection(conn);
            boolean result = sJ.deleteById(id);
            if (result) {
                conn.commit();
                conn.setAutoCommit(true);
            } 
            conn.close();
            return result;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public boolean editStudent(Student st) {
        Connector c = new Connector(SQLCommands.URL, SQLCommands.USERNAME, SQLCommands.PASSWORD);
        Connection conn = c.getConnection();
        try {
            StudentInterface sJ = new StudentJDBC();
            conn.setAutoCommit(false);
            sJ.setConnection(conn);
            boolean result = sJ.edit(st);
            if (result) {
                conn.commit();
                conn.setAutoCommit(true);
            } 
            conn.close();
            return result;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return false;
        }
    }

    private String getSQLCommand(StudentSearchDTO ssd) {
        String result = "";
        if (ssd.isEmpty()) {
            return result;
        } 
        if (ssd.getName() != null) {
            result += "WHERE name LIKE '%" + ssd.getName() + "%'";
        }
        if (ssd.isFull()) {
            result += " AND course = '" + ssd.getCourse() + "'";
            return result;
        }
        if (ssd.getCourse() != null) {
            result += " WHERE course = '" + ssd.getCourse() + "'";
        }
        return result;
    }

}
