package pw.edu.pl.barbercalendarapi.controller;

import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pw.edu.pl.barbercalendarapi.dto.AppointmentDTO;
import pw.edu.pl.barbercalendarapi.model.Appointment;
import pw.edu.pl.barbercalendarapi.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointmentList = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable("id") Long id) {
        Appointment appointment = appointmentService.getAppointment(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping("/find/between")
    public ResponseEntity<List<Appointment>> getAppointment(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        List<Appointment> appointmentList = appointmentService.getAppointmentsByDate(start, end);
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Appointment> addAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        Appointment newAppointment = appointmentService.addAppointment(appointmentDTO);
        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Appointment> updateAppointment(
            @RequestBody AppointmentDTO appointmentDTO) {
        Appointment updatedAppointment = appointmentService.editAppointment(appointmentDTO);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Appointment> deleteAppointment(@PathVariable("id") Long id) {
        Appointment deletedAppointment = appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(deletedAppointment, HttpStatus.OK);
    }
}
