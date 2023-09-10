package service;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import dao.SQLCommands;
import dao.models.Student;
import dao.mybatis.impl.StudentMyBatis;
import web.dto.StudentSearchDTO;

public class StudentServiceMyBatisImpl implements StudentService{

    @Override
    public boolean addStudent(Student st) {
        try {
            Reader reader = Resources.getResourceAsReader(SQLCommands.CONFIG_XML);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession s = sqlSessionFactory.openSession();
            try {
                StudentMyBatis smb = new StudentMyBatis(s);
                boolean result = smb.add(st);
                if (result) {
                    s.commit();
                }
                s.close();
                reader.close();
                return result;
            } catch (Exception e) {
                s.rollback();
                s.close();
                reader.close();
                e.printStackTrace();
                return false;
            }
        } catch (IOException ie) {
            ie.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Student> searchStudent(StudentSearchDTO ssd) {
        try {
            Reader reader = Resources.getResourceAsReader(SQLCommands.CONFIG_XML);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession s = sqlSessionFactory.openSession();
            StudentMyBatis smb = new StudentMyBatis(s);
            List<Student> result = new ArrayList<>();
            result = smb.search(ssd);
            return result;
        } catch (IOException ie) {
            ie.printStackTrace();
            return null;
        }
    }

    @Override
    public Student searchStudentById(int id) {
        try {
            Reader reader = Resources.getResourceAsReader(SQLCommands.CONFIG_XML);
            SqlSessionFactory sqlsessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession s = sqlsessionFactory.openSession();
            StudentMyBatis smb = new StudentMyBatis(s);
            List<Student> result = smb.searchById(id);
            return result.get(0);
        } catch (IOException ie) {
            ie.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteStudent(int id) {
        try {
            Reader reader = Resources.getResourceAsReader(SQLCommands.CONFIG_XML);
            SqlSessionFactory sqlsessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession s = sqlsessionFactory.openSession();
            try {
                StudentMyBatis smb = new StudentMyBatis(s);
                boolean result = smb.remove(id);
                if (result) {
                    s.commit();
                }
                s.close();
                reader.close();
                return result;
            } catch (Exception e) {
                s.rollback();
                s.close();
                reader.close();
                e.printStackTrace();
                return false;
            }
        } catch (IOException ie) {
            ie.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editStudent(Student st) {
        try {
            Reader reader = Resources.getResourceAsReader(SQLCommands.CONFIG_XML);
            SqlSessionFactory sqlsessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession s = sqlsessionFactory.openSession();
            try {
                StudentMyBatis smb = new StudentMyBatis(s);
                boolean result = smb.edit(st);
                if (result) {
                    s.commit();
                }
                s.close();
                reader.close();
                return result;
            } catch (Exception e) {
                s.rollback();
                s.close();
                reader.close();
                e.printStackTrace();
                return false;
            }
        } catch (IOException ie) {
            ie.printStackTrace();
            return false;
        }
    }
    
}
