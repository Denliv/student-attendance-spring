package student_attendance.response;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.List;

@Data
public class ValidationResponse {
    private String timestamp;
    private int statusCode;
    private List<String> errors;

    public ValidationResponse(int statusCode, List<String> errors) {
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        ;
        this.statusCode = statusCode;
        this.errors = errors;
    }
}
