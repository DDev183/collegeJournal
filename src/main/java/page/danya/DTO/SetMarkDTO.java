package page.danya.DTO;

import java.time.LocalDate;
import java.util.Date;

public class SetMarkDTO extends createLessonDTO {

    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
