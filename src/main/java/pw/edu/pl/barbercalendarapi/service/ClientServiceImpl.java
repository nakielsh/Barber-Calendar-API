package pw.edu.pl.barbercalendarapi.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.pl.barbercalendarapi.dto.ClientDTO;
import pw.edu.pl.barbercalendarapi.model.Appointment;
import pw.edu.pl.barbercalendarapi.model.Client;
import pw.edu.pl.barbercalendarapi.repo.ClientRepo;

@Service
@Transactional
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepo clientRepo;

    @Autowired
    public ClientServiceImpl(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Override
    public Client addClient(ClientDTO clientDTO) {
        log.info("Saving new client: {} to the DB", clientDTO.getName());
        Optional<Client> existingClient = clientRepo.findClientByEmail(clientDTO.getEmail());
        if (existingClient.isEmpty()) {
            return clientRepo.save(
                    new Client(
                            clientDTO.getName(),
                            clientDTO.getEmail(),
                            clientDTO.getPhone(),
                            clientDTO.getImageUrl()));
        } else throw new IllegalArgumentException("Client already exists");
    }

    @Override
    public Client getClient(Long id) {
        log.info("Fetching client with id: {}", id);
        return clientRepo
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
    }

    @Override
    public List<Client> getAllClients() {
        log.info("Fetching all clients");
        return clientRepo.findAll();
    }

    @Override
    public List<Client> getClientsByName(String name) {
        log.info("Fetching clients by name: {}", name);
        return clientRepo.findClientsByNameIsContaining(name);
    }

    @Override
    public Client editClient(ClientDTO clientDTO) {
        log.info("Editing client: {}", clientDTO.getName());
        Optional<Client> clientToEdit = clientRepo.findById(clientDTO.getId());
        if (clientToEdit.isPresent()) {
            clientToEdit.get().setName(clientDTO.getName());
            clientToEdit.get().setEmail(clientDTO.getEmail());
            clientToEdit.get().setPhone(clientDTO.getPhone());
            clientToEdit.get().setImageUrl(clientDTO.getImageUrl());
            return clientToEdit.get();
        } else throw new IllegalArgumentException("Client not found");
    }

    @Override
    public Client deleteClient(Long id) {
        log.info("Deleting client with id: {}", id);
        Optional<Client> client = clientRepo.findById(id);
        if (client.isPresent()) {
            clientRepo.deleteById(id);
            return client.get();
        } else throw new IllegalArgumentException("Client not found");
    }

    @Override
    public void addAppointmentToClient(Appointment appointment, Long clientId) {
        log.info(
                "Adding appointment: {} to the client with id: {}",
                appointment.getCutType(),
                clientId);
        Optional<Client> clientToAdd = clientRepo.findById(clientId);
        if (clientToAdd.isPresent()) {
            clientToAdd.get().getAppointmentList().add(appointment);
        } else throw new IllegalArgumentException("Client not found");
    }

    public void deleteAppointmentFromClient(Long appointmentId, Long clientId) {
        log.info(
                "Deleting appointment with id: {} to the client with id: {}",
                appointmentId,
                clientId);
        Predicate<Appointment> isPresent =
                appointment -> Objects.equals(appointment.getId(), appointmentId);
        Optional<Client> clientToAdd = clientRepo.findById(clientId);
        if (clientToAdd.isPresent()) {
            clientToAdd.get().getAppointmentList().removeIf(isPresent);
        } else throw new IllegalArgumentException("Client not found");
    }
}
