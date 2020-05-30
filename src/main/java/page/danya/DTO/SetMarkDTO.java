package page.danya.DTO;

import java.time.LocalDate;
import java.util.Date;

public class SetMarkDTO extends createLessonDTO {

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
