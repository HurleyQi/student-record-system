package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class Connector {

    private String url;
    private String username;
    private String password;

    public Connector() {}

    public Connector(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("No Driver Error");
        }
        Connection result = null;
        try {
            result = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("Exceptions Error");
        }
        return result;
    }

    public Statement getStatement(Connection conn) throws SQLException {
        if (conn == null) {
            throw new IllegalStateException("Connection is null");
        }
        Statement result = conn.createStatement();
        return result;
    }

    public Statement getPreparedStatement(Connection conn, String query) throws SQLException {
        if (conn == null) {
            throw new IllegalStateException("Connection is null");
        }
        PreparedStatement result = conn.prepareStatement(query);
        return result;
    }

    public ResultSet getResultSet(Statement s, String query) throws SQLException {
        if (s == null) {
            throw new IllegalStateException("Statement is null");
        }
        ResultSet result = s.executeQuery(query);
        return result;
    }

    public Savepoint getSavepoint(Connection conn, String name) throws SQLException {
        Savepoint result = conn.setSavepoint(name);
        return result;
    }

    public void closeConnection(Connection conn, Statement s, ResultSet rs) throws SQLException { 
        if (rs != null) {
            rs.close();
        }
        if (s != null) {
            s.close();
        }
        if (conn != null) {
            conn.close();
        }
    } 

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
