package dao.mybatis.impl;

import java.util.List;
import dao.models.Record;
import dao.mybatis.interfaces.RecordMapper;

import org.apache.ibatis.session.SqlSession;

public class RecordMyBatis {
    public RecordMapper rm;
    public SqlSession s;

    public RecordMyBatis() {}

    public RecordMyBatis(RecordMapper rm) {
        this.rm = rm;
    }

    public RecordMyBatis(SqlSession s) {
        this.s = s;
        this.rm = s.getMapper(RecordMapper.class);
    }

    public boolean add(Record r) {
        if (isPresent(r.getStudentId())) {
            throw new IllegalArgumentException();
        }
        int result = this.rm.addRecord(r);
        return (result != 0);
    }

    public boolean edit(Record r) {
        if (isPresent(r.getStudentId())) {
            int result = this.rm.editRecord(r);
            return (result != 0);
        } else {
            int result = this.rm.addRecord(r);
            return (result != 0);
        }
    }

    public boolean removeByStudentId(int studentId) {
        if (!isPresent(studentId)) {
            return true;
        }
        int result = this.rm.removeRecordByStudentId(studentId);
        return (result != 0);
    }

    public boolean removeById(int id) {
        if (!isPresentById(id)) {
            return true;
        }
        int result = this.rm.removeRecordById(id);
        return (result != 0);
    }

    public Record searchByStudentId(int studentId) {
        List<Record> result = this.rm.searchRecordByStudentId(studentId);
        if (result.size() == 0) {
            return null;
        } else {
            return result.get(0);
        }
    }

    public List<Record> searchById(int id) {
        List<Record> result = this.rm.searchRecordById(id);
        return result;
    }

    private boolean isPresent(int studentId) {
        if (studentId <= 0) {
            return false;
        }
        List<Record> result = this.rm.searchRecordByStudentId(studentId);
        return result.size() != 0;
    }

    private boolean isPresentById(int id) {
        if (id <= 0) {
            return false;
        }
        List<Record> result = this.rm.searchRecordById(id);
        return result.size() != 0;
    }
}
