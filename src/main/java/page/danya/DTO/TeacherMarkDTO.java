package page.danya.DTO;

import java.time.LocalDate;
import java.util.*;

public class TeacherMarkDTO {

    List<String> students;
    List<String> dates;
    List<List <String>> marks;
    List<List<String>> absents;

    public List<List<String>> getAbsents() {
        return absents;
    }

    public void setAbsents(List<List<String>> absents) {
        this.absents = absents;
    }

    public TeacherMarkDTO() {
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public List<List <String>> getMarks() {
        return marks;
    }

    public void setMarks(List<List <String>> marks) {
        this.marks = marks;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }
}
