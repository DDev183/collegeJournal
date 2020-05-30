package page.danya.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class MarkHistoryDTO {

    private String value;
    private String teacher;
    private String subject;
    private String absent;
    private String date;
    private String time;


    public MarkHistoryDTO(String value, String teacher, String subject, String absent) {
        this.value = value;
        this.teacher = teacher;
        this.subject = subject;
        this.absent = absent;
    }

    public MarkHistoryDTO(String value, String teacher, String subject, String absent, String date, String time) {
        this.value = value;
        this.teacher = teacher;
        this.subject = subject;
        this.absent = absent;
        this.date = date;
        this.time = time;
    }

    public MarkHistoryDTO() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
