package dao;

public class SQLCommands {
    // login info
    public static final String URL = "jdbc:mysql://localhost:3306/my_db";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "MySQL1234!";
    public static final String CONFIG_XML = "sqlMapConfig.xml";

    public static final String ADD_RECORD = "INSERT INTO record VALUES(?, ?, ?, ?, ?, ?)";
    public static final String EDIT_RECORD = "UPDATE record SET id = ?, school = ?," + 
                                        " start = ?, end = ?, level = ? WHERE student_id = ?";
    public static final String SELECT_ALL_RECORD = "SELECT * FROM record ";
    public static final String DELETE_RECORD = "DELETE FROM record ";

    public static final String SELECT_ALL_STUDENT = "SELECT * FROM student_info ";
    public static final String DELETE_STUDENT = "DELETE FROM student_info ";
    public static final String ADD_STUDENT = "INSERT INTO student_info VALUES(?,?,?,?,?,?,?)";
    public static final String EDIT_STUDENT = "UPDATE student_info SET " + 
    "name = ?, age = ?, date_of_birth = ?, adress = ?, gender = ?, course = ? WHERE id = ";

}