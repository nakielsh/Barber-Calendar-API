package pw.edu.pl.barbercalendarapi.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.time.DateUtils;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Appointment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false, updatable = false)
    private Long id;

    private Date startDate;
    private Date endDate;
    private String cutType;
    private Long clientId;

    public Appointment(Date startDate, String cutType, Long clientId) {
        this.startDate = startDate;
        this.endDate = DateUtils.addHours(startDate, 1);
        this.cutType = cutType;
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Appointment{"
                + "id="
                + id
                + ", startDate="
                + startDate
                + ", endDate="
                + endDate
                + ", cutType='"
                + cutType
                + '\''
                + ", clientId="
                + clientId
                + '}';
    }
}
