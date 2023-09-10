package dao.models;

import java.util.List;
import java.sql.Date;

public class Student {
    private String name;
    private int id;
    private int age;
    private Date dob;
    private int gender;
    private String address;
    private String course;
    List<Record> records;

    public Student() {}

    public Student(String name, int id) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.id = id;
    }

    public Student(String name, int id, int age, java.util.Date date, 
                int gender, String address, String course) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.dob = (date == null) ? null : new Date(date.getTime());
        this.gender = gender;
        this.address = address;
        this.course = course;
    }

    public Student(String name, int id, int age, java.util.Date date, 
                int gender, String address, String course, List<Record> records) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.dob = (date == null) ? null : new Date(date.getTime());
        this.gender = gender;
        this.address = address;
        this.course = course;
        this.records = records;
    }

    public void addRecord(Record data) {
        if (this.records.contains(data)) {
            throw new IllegalArgumentException();
        }
        this.records.add(data);
    }

    public boolean equals(Student st) {
        return this.id == st.getId();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("名字: " + this.name + "\n");
        result.append("身份证号码: " + this.id + "\n");
        result.append("年龄: " + this.age + "\n");
        result.append("出生日期: " + this.dob + "\n");
        result.append("地址: " + this.address + "\n");
        result.append("所属班级: " + this.course + "\n");
        result.append("教育经历： " + this.records + "\n");
        return result.toString();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return this.dob;
    }

    public void setDate(Date dob) {
        this.dob = dob;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCourse() {
        return this.course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public List<Record> getRecords() {
        return this.records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}