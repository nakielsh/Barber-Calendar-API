package pw.edu.pl.barbercalendarapi.service;

import java.util.List;
import pw.edu.pl.barbercalendarapi.dto.ClientDTO;
import pw.edu.pl.barbercalendarapi.model.Appointment;
import pw.edu.pl.barbercalendarapi.model.Client;

public interface ClientService {
    Client addClient(ClientDTO clientDTO);

    Client getClient(Long id);

    List<Client> getAllClients();

    List<Client> getClientsByName(String name);

    Client editClient(ClientDTO clientDTO);

    Client deleteClient(Long id);

    void addAppointmentToClient(Appointment appointment, Long id);

    void deleteAppointmentFromClient(Long appointmentId, Long clientId);
}
