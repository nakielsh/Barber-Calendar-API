package pw.edu.pl.barbercalendarapi.service;

import java.util.Date;
import java.util.List;
import pw.edu.pl.barbercalendarapi.dto.AppointmentDTO;
import pw.edu.pl.barbercalendarapi.model.Appointment;

public interface AppointmentService {
    Appointment addAppointment(AppointmentDTO appointmentDTO);

    List<Appointment> getAllAppointments();

    Appointment getAppointment(Long id);

    List<Appointment> getAppointmentsByDate(Date start, Date end);

    Appointment editAppointment(AppointmentDTO appointmentDTO);

    Appointment deleteAppointment(Long id);
}
