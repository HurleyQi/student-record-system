package web.dto;

public class StudentSearchDTO {

    private String name;
    private String course;

    public StudentSearchDTO() {}

    public StudentSearchDTO(String name, String course) {
        if (name == null || name.equals("")) {
            this.name = null;
        } else {
            this.name = name;
        }
        if (course == null || course.equals("")) {
            this.course = null;
        } else {
            this.course = course;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return this.course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public boolean isFull() {
        return (this.name != null && this.course != null);
    }

    public boolean isEmpty() {
        return (this.name == null && this.course == null);
    }

    public String toString() {
        return ("name: " + this.name + "  course: " + this.course);
    }
}
