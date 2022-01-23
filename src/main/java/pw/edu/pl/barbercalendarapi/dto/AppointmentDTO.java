package pw.edu.pl.barbercalendarapi.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentDTO {

    private Long id;
    private Date startDate;
    private String cutType;
    private Long clientId;
}
