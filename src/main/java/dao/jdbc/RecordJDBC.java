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
import dao.models.Record;

public class RecordJDBC implements RecordInterface {

    private Connection conn;
    private Connector c = new Connector();

    public RecordJDBC() {
    }

    public RecordJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean add(Record r) {
        if (r.getStudentId() <= 0 || isPresent(r.getStudentId())) {
            throw new IllegalStateException("Record must have student ID");
        }
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQLCommands.ADD_RECORD);
            ps.setInt(1, r.getStudentId());
            ps.setInt(2, r.getId());
            ps.setString(3, r.getSchool());
            ps.setDate(4, r.getStart());
            ps.setDate(5, r.getEnd());
            ps.setInt(6, r.getLevel());
            int result = ps.executeUpdate();
            this.c.closeConnection(null, ps, null);
            return result != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void edit(Record r) {
        try {
            if (isPresent(r.getStudentId())) {
                PreparedStatement ps = this.conn.prepareStatement(SQLCommands.EDIT_RECORD);
                ps.setInt(1, r.getId());
                ps.setString(2, r.getSchool());
                ps.setDate(3, r.getStart());
                ps.setDate(4, r.getEnd());
                ps.setInt(5, r.getLevel());
                ps.setInt(6, r.getStudentId());
                ps.executeUpdate();
                this.c.closeConnection(null, ps, null);
            } else {
                add(r);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    @Override
    public Record search(int studentId) {
        if (studentId < 0 || !isPresent(studentId)) {
            throw new IllegalArgumentException();
        }
        try {
            Record result = new Record();
            Statement s = this.conn.createStatement();
            ResultSet rs = s.executeQuery(SQLCommands.SELECT_ALL_RECORD + "WHERE student_id = " + studentId);
            if (rs.next()) {
                result.setId(rs.getInt(2));
                result.setSchool(rs.getString(3));
                result.setStart(rs.getDate(4));
                result.setEnd(rs.getDate(5));
                result.setLevel(rs.getInt(6));
            } else {
                c.closeConnection(null, s, rs);
                return null;
            }
            this.c.closeConnection(null, s, rs);
            return result;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Record> searchRecords(int id) {
        if (id < 0) {
            throw new IllegalArgumentException();
        }
        try {
            List<Record> result = new ArrayList<>();
            Statement s = this.conn.createStatement();
            ResultSet rs = s.executeQuery(SQLCommands.SELECT_ALL_RECORD + "WHERE id = " + id);
            while (rs.next()) {
                result.add(new Record(rs.getInt(1), rs.getInt(2),
                        rs.getDate(4), rs.getDate(5),
                        rs.getString(3), rs.getInt(6)));
            }
            if (result.isEmpty()) {
                c.closeConnection(null, s, rs);
                return null;
            }
            this.c.closeConnection(null, s, rs);
            return result;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteByStudentId(int studentId) {
        if (!isPresent(studentId)) {
            throw new IllegalArgumentException("Invalid ID");
        }
        try {
            Statement s = this.conn.createStatement();
            boolean result = s.execute(SQLCommands.DELETE_RECORD + "WHERE student_id = " + studentId);
            this.c.closeConnection(null, s, null);
            return result;
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        if (!isPresentById(id)) {
            return true;
        }
        try {
            Statement s = this.conn.createStatement();
            boolean result = s.execute(SQLCommands.DELETE_RECORD + "WHERE id = " + id);
            this.c.closeConnection(null, s, null);
            return result;
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
    }

    private boolean isPresent(int studentId) {
        try {
            Statement s = this.conn.createStatement();
            ResultSet rs = s.executeQuery(SQLCommands.SELECT_ALL_RECORD + "WHERE student_id = " + studentId);
            boolean result = rs.next();
            this.c.closeConnection(null, s, rs);
            return result;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return false;
    }

    private boolean isPresentById(int id) {
        try {
            Statement s = this.conn.createStatement();
            ResultSet rs = s.executeQuery(SQLCommands.SELECT_ALL_RECORD + "WHERE id = " + id);
            boolean result = rs.next();
            this.c.closeConnection(null, s, rs);
            return result;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return false;
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void setConnection(Connection conn) throws SQLException {
        this.conn = conn;
    }
}
