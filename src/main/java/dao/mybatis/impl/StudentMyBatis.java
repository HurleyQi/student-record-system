package dao.mybatis.impl;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import dao.models.Student;
import dao.models.Record;
import dao.mybatis.interfaces.StudentMapper;
import web.dto.StudentSearchDTO;

public class StudentMyBatis {

    public StudentMapper sm;
    public SqlSession s;

    public StudentMyBatis() {}

    public StudentMyBatis(StudentMapper sm) {
        this.sm = sm;
    }

    public StudentMyBatis(SqlSession s) {
        this.s = s;
        this.sm = s.getMapper(StudentMapper.class);
    }

    public boolean add(Student st) {
        if (isPresent(st.getId())) {
            throw new IllegalArgumentException();
        }
        int result = this.sm.addStudent(st);
        RecordMyBatis rmb = new RecordMyBatis(this.s);
        List<Record> r = st.getRecords();
        for (int i = 0; i < r.size(); i++) {
            rmb.add(r.get(i));
        }
        return (result != 0);
    }

    public boolean remove(int id) {
        if (!isPresent(id)) {
            throw new IllegalArgumentException();
        }
        RecordMyBatis rmb = new RecordMyBatis(this.s);
        boolean removeRecord = rmb.removeById(id);
        if (removeRecord) {
            int result = this.sm.removeStudent(id);
            return (result != 0);
        }
        return false;
    }

    public boolean edit(Student st) {
        if (isPresent(st.getId())) {
            RecordMyBatis rmb = new RecordMyBatis(this.s);
            List<Record> r = st.getRecords();
            for (int i = 0; i < r.size(); i++) {
                boolean recordEdit = rmb.edit(r.get(i));
                if (!recordEdit) {
                    return false;
                }
            }
            int result = this.sm.editStudent(st);
            return (result != 0);
        } else {
            boolean result = add(st);
            return result;
        }
    }

    public List<Student> search(StudentSearchDTO ssd) {
        List<Student> result = this.sm.search(ssd);
        RecordMyBatis rmb = new RecordMyBatis(this.s);
        for (int i = 0; i < result.size(); i++) {
            Student currS = result.get(i);
            List<Record> currRecords = rmb.searchById(currS.getId());
            currS.setRecords(currRecords);
        }
        return result;
    }

    public List<Student> searchAll() {
        List<Student> result = this.sm.selectAll();
        RecordMyBatis rmb = new RecordMyBatis(this.s);
        for (int i = 0; i < result.size(); i++) {
            Student currS = result.get(i);
            List<Record> currRecords = rmb.searchById(currS.getId());
            currS.setRecords(currRecords);
        }
        return result;
    }

    public List<Student> searchById(int id) {
        List<Student> result = this.sm.searchStudentById(id);
        RecordMyBatis rmb = new RecordMyBatis(this.s);
        List<Record> r = rmb.searchById(id);
        result.get(0).setRecords(r);
        return result;
    }

    // public List<Student> searchByName(String name) {
    //     List<Student> result = this.sm.searchStudentByName(name);
    //     RecordMyBatis rmb = new RecordMyBatis(this.s);
    //     for (int i = 0; i < result.size(); i++) {
    //         Student currS = result.get(i);
    //         List<Record> currRecords = rmb.searchById(currS.getId());
    //         currS.setRecords(currRecords);
    //     }
    //     return result;
    // }

    // public List<Student> searchByCourse(String course) {
    //     List<Student> result = this.sm.searchStudentByCourse(course);
    //     RecordMyBatis rmb = new RecordMyBatis(this.s);
    //     for (int i = 0; i < result.size(); i++) {
    //         Student currS = result.get(i);
    //         List<Record> currRecords = rmb.searchById(currS.getId());
    //         currS.setRecords(currRecords);
    //     }
    //     return result;
    // }

    // public List<Student> searchByNameAndCourse(String name, String course) {
    //     List<Student> result = this.sm.searchStudentByNameAndCourse(name, course);
    //     RecordMyBatis rmb = new RecordMyBatis(this.s);
    //     for (int i = 0; i < result.size(); i++) {
    //         Student currS = result.get(i);
    //         List<Record> currRecords = rmb.searchById(currS.getId());
    //         currS.setRecords(currRecords);
    //     }
    //     return result;
    // }

    private boolean isPresent(int id) {
        if (id <= 0) {
            return false;
        }
        List<Student> result = this.sm.searchStudentById(id);
        return result.size() != 0;
    }
}
