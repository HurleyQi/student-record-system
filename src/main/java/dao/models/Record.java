package dao.models;

import java.sql.Date;

public class Record {

    private Date start;
    private Date end;
    private int id;
    private int studentId;
    private int level;
    private String school;

    public Record() {}

    public Record(int studentId) {
        this.studentId = studentId;
    }

    public Record(int studentId, java.util.Date startD, java.util.Date endD,
            String school) {
        this.studentId = studentId;
        this.start = (startD != null) ? new Date(startD.getTime()) : null;
        this.end = (endD != null) ? new Date(endD.getTime()) : null;
        this.school = school;
    }

    public Record(int studentId, int id, java.util.Date startD, java.util.Date endD,
            String school, int level) {
        this.studentId = studentId;
        this.id = id;
        this.start = (startD != null) ? new Date(startD.getTime()) : null;
        this.end = (endD != null) ? new Date(endD.getTime()) : null;
        this.school = school;
        this.level = level;
    }

    public String toString() {
        String result = ("Student id: " + this.studentId + " Personal id: " + this.id + 
                    " Start Date: " + this.start + " End Date: " + this.end + " School: "
                    + this.school + " Level: " + this.level);
        return result;
    }

    public boolean equals(Record record) {
        return this.studentId == record.getStudentId();
    }

    public Date getStart() {
        return this.start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return this.end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return this.studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSchool() {
        return this.school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}