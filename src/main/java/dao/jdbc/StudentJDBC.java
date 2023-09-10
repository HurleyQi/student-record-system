package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.Connector;
import dao.SQLCommands;
import dao.interfaces.RecordInterface;
import dao.interfaces.StudentInterface;
import dao.models.Record;
import dao.models.Student;

public class StudentJDBC implements StudentInterface{

    private Connection conn;
    private Connector c = new Connector();

    public StudentJDBC() {}

    public StudentJDBC(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public List<Student> search(String identifier) {
        try {
            List<Student> result = new ArrayList<>();
            Statement s = this.conn.createStatement();
            ResultSet rs;
            rs = s.executeQuery(SQLCommands.SELECT_ALL_STUDENT + identifier);
            while (rs.next()) {
                Student curr = new Student(rs.getString(2), rs.getInt(1),
                rs.getInt(3), rs.getDate(4),rs.getInt(6),
                rs.getString(5), rs.getString(7));
                curr.setRecords(searchRecords(rs.getInt(1)));
                result.add(curr);
            }
            return result;
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }

    }

    @Override
    public Student searchById(int id) {
        try {
            Statement s = this.conn.createStatement();
            ResultSet rs = s.executeQuery(SQLCommands.SELECT_ALL_STUDENT + " WHERE id = " + id);
            Student result = new Student();
            if (rs.next()) {
                result.setId(id);
                result.setName(rs.getString(2));
                result.setAge(rs.getInt(3));
                result.setDate(rs.getDate(4));
                result.setAddress(rs.getString(5));
                result.setGender(rs.getInt(6));
                result.setCourse(rs.getString(7));
            } else {
                return null;
            }
            result.setRecords(searchRecords(id));
            this.c.closeConnection(null, s, rs);
            return result;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Record> searchRecords(int id) {
        RecordInterface r = new RecordJDBC(this.conn);
        List<Record> result = r.searchRecords(id);
        return result;
    }

    @Override
    public boolean add(Student st) {
        if (st.getId() == 0 || isPresent(st.getId())) {
            throw new IllegalArgumentException("Invalid ID");
        }
        if (st.getRecords() == null) {
            throw new IllegalArgumentException();
        }
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQLCommands.ADD_STUDENT);
            ps.setInt(1, st.getId());
            ps.setString(2, st.getName());
            ps.setInt(3, st.getAge());
            ps.setDate(4, st.getDate());
            ps.setString(5, st.getAddress());
            ps.setInt(6, st.getGender());
            ps.setString(7, st.getCourse());
            int result = ps.executeUpdate();
            RecordInterface r = new RecordJDBC(this.conn);
            for (int i = 0; i < st.getRecords().size(); i++) {
                Record curr = st.getRecords().get(i);
                boolean currSuccess = r.add(curr);
                if (!currSuccess) {
                    return false;
                }
            }
            this.c.closeConnection(null, ps, null);
            return result != 0;
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean edit(Student st) {
        if (st.getId() == 0 || !isPresent(st.getId())) {
            throw new IllegalArgumentException("Invalid ID");
        }
        try {
            PreparedStatement ps =
                            this.conn.prepareStatement(SQLCommands.EDIT_STUDENT + st.getId());
            ps.setString(1, st.getName());
            ps.setInt(2, st.getAge());
            ps.setDate(3, st.getDate());
            ps.setString(4, st.getAddress());
            ps.setInt(5, st.getGender());
            ps.setString(6, st.getCourse());
            ps.executeUpdate();
            if (st.getRecords() != null) {
                for (int i = 0; i < st.getRecords().size(); i++){
                    editRecord(st.getRecords().get(i));
                }
            }
            this.c.closeConnection(null, ps, null);
            return true;
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
    }

    @Override
    public void editRecord(Record record) {
        if (record.getId() <= 0) {
            throw new IllegalArgumentException();
        }
        RecordInterface r = new RecordJDBC(this.conn);
        r.edit(record);
    }

    @Override
    public boolean deleteById(int id) {
        if (id == 0 || !isPresent(id)) {
            throw new IllegalArgumentException("Invalid ID");
        }
        try {
            RecordInterface r = new RecordJDBC(this.conn);
            Statement s = this.conn.createStatement();
            boolean result = r.deleteById(id);
            if (result) {
                s.execute(SQLCommands.DELETE_STUDENT + " WHERE id = " + id);
            }
            this.c.closeConnection(null, s, null);
            return result;
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
    }

    private boolean isPresent(int id) {
        try {
            Statement s = this.conn.createStatement();
            ResultSet rs = s.executeQuery(SQLCommands.SELECT_ALL_STUDENT + "WHERE id = " + id);
            boolean result = rs.next();
            c.closeConnection(null, s, rs);
            return result;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return false;
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }
}
