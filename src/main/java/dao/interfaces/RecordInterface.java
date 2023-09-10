package dao.interfaces;


import java.util.List;

import dao.models.Record;

public interface RecordInterface {
    boolean add(Record r);

    void edit(Record r);

    Record search(int studentId);
    List<Record> searchRecords(int id);

    boolean deleteById(int id);
    boolean deleteByStudentId(int studentId);

}
