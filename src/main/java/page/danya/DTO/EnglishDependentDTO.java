package page.danya.DTO;

import java.util.List;

public class EnglishDependentDTO {

    List<String> students;
    List<String> value;
    String group;
    String subject;

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

    public EnglishDependentDTO() {
    }

    public EnglishDependentDTO(List<String> students, List<String> value) {
        this.students = students;
        this.value = value;
    }

    public EnglishDependentDTO(List<String> students, List<String> value, String group, String subject) {
        this.students = students;
        this.value = value;
        this.group = group;
        this.subject = subject;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
