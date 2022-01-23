package pw.edu.pl.barbercalendarapi.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pw.edu.pl.barbercalendarapi.model.Appointment;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    List<Appointment> findAppointmentsByStartDateBetween(Date start, Date end);

    Optional<Appointment> findAppointmentByStartDate(Date startDate);
}
