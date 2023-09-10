package dao.mybatis.interfaces;

import java.util.List;
import dao.models.Record;

public interface RecordMapper {
    
    int addRecord(Record record);
    int removeRecordByStudentId(int studentId);
    int removeRecordById(int id);
    int editRecord(Record record);
    List<Record> searchRecordById(int id);
    List<Record> searchRecordByStudentId(int studentId);
}
