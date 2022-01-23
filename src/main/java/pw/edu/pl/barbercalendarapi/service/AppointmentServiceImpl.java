package pw.edu.pl.barbercalendarapi.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.pl.barbercalendarapi.dto.AppointmentDTO;
import pw.edu.pl.barbercalendarapi.model.Appointment;
import pw.edu.pl.barbercalendarapi.repo.AppointmentRepo;

@Service
@Transactional
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final ClientService clientService;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepo appointmentRepo, ClientService clientService) {
        this.appointmentRepo = appointmentRepo;
        this.clientService = clientService;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    @Override
    public Appointment addAppointment(AppointmentDTO appointmentDTO) {
        log.info("Saving new appointment: {} to the DB", appointmentDTO.getCutType());
        Optional<Appointment> existingAppointment =
                appointmentRepo.findAppointmentByStartDate(appointmentDTO.getStartDate());
        if (existingAppointment.isEmpty()) {
            Appointment savedAppointment =
                    appointmentRepo.save(
                            new Appointment(
                                    appointmentDTO.getStartDate(),
                                    appointmentDTO.getCutType(),
                                    appointmentDTO.getClientId()));
            clientService.addAppointmentToClient(savedAppointment, savedAppointment.getClientId());
            return savedAppointment;
        } else throw new IllegalArgumentException("Appointment already exists");
    }

    @Override
    public Appointment getAppointment(Long id) {
        log.info("Fetching appointment with id: {}", id);
        return appointmentRepo
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
    }

    @Override
    public List<Appointment> getAppointmentsByDate(Date start, Date end) {
        log.info("Fetching appointments between: {}, and {}", start.toString(), end.toString());
        return appointmentRepo.findAppointmentsByStartDateBetween(start, end);
    }

    @Override
    public Appointment editAppointment(AppointmentDTO appointmentDTO) {
        log.info("Editing appointment: {}", appointmentDTO.getCutType());
        Optional<Appointment> appointmentToEdit = appointmentRepo.findById(appointmentDTO.getId());
        if (appointmentToEdit.isPresent()) {
            Long previousClientId = appointmentToEdit.get().getClientId();

            appointmentToEdit.get().setStartDate(appointmentDTO.getStartDate());
            appointmentToEdit
                    .get()
                    .setEndDate(DateUtils.addHours(appointmentDTO.getStartDate(), 1));
            appointmentToEdit.get().setCutType(appointmentDTO.getCutType());
            appointmentToEdit.get().setClientId(appointmentDTO.getClientId());

            exchangeAppointmentsInClients(
                    previousClientId,
                    appointmentDTO.getClientId(),
                    appointmentToEdit.get().getId());

            return appointmentToEdit.get();
        } else throw new IllegalArgumentException("Appointment not found");
    }

    @Override
    public Appointment deleteAppointment(Long id) {
        log.info("Deleting appointment with id: {}", id);
        Optional<Appointment> appointment = appointmentRepo.findById(id);
        if (appointment.isPresent()) {
            clientService.deleteAppointmentFromClient(id, appointment.get().getClientId());
            appointmentRepo.deleteById(id);
            return appointment.get();
        } else throw new IllegalArgumentException("Appointment not found");
    }

    public void exchangeAppointmentsInClients(Long idToRemove, Long idToAdd, Long appointmentId) {
        Appointment appointment = appointmentRepo.getById(appointmentId);
        clientService.deleteAppointmentFromClient(appointmentId, idToRemove);
        clientService.addAppointmentToClient(appointment, idToAdd);
    }
}
