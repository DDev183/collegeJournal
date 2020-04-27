package page.danya.DTO;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public class createLessonDTO {

    private Map<String, String> students = new HashMap<>();
    private String group;
    private String subject;

    public createLessonDTO() {

    }

    public createLessonDTO(Map<String, String> students, String group, String subject) {
        this.students = students;
        this.group = group;
        this.subject = subject;
    }

    public Map<String, String> getStudents() {
        return students;
    }

    public void setStudents(Map<String, String> students) {
        this.students = students;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

