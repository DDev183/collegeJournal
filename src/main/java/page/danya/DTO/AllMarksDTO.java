package page.danya.DTO;

import page.danya.models.Subject;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class AllMarksDTO {

    List<String> subjects;
    List<LocalDate> dates;
    List<List <String>> marks;
    List<List<String>> absents;
    LocalDate startDate;
    LocalDate endDate;

    public AllMarksDTO() {
    }

    public AllMarksDTO(List<String> subjects, List<LocalDate> dates, List<List<String>> marks, List<List<String>> absents, LocalDate startDate, LocalDate endDate) {
        this.subjects = subjects;
        this.dates = dates;
        this.marks = marks;
        this.absents = absents;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public List<LocalDate> getDates() {
        return dates;
    }

    public void setDates(List<LocalDate> dates) {
        this.dates = dates;
    }

    public List<List<String>> getMarks() {
        return marks;
    }

    public void setMarks(List<List<String>> marks) {
        this.marks = marks;
    }

    public List<List<String>> getAbsents() {
        return absents;
    }

    public void setAbsents(List<List<String>> absents) {
        this.absents = absents;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
