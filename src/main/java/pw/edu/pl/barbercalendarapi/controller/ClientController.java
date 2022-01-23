package pw.edu.pl.barbercalendarapi.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.edu.pl.barbercalendarapi.dto.ClientDTO;
import pw.edu.pl.barbercalendarapi.model.Client;
import pw.edu.pl.barbercalendarapi.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clientList = clientService.getAllClients();
        return new ResponseEntity<>(clientList, HttpStatus.OK);
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") Long id) {
        Client client = clientService.getClient(id);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity<List<Client>> getClient(@PathVariable("name") String name) {
        List<Client> clientList = clientService.getClientsByName(name);
        return new ResponseEntity<>(clientList, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Client> addClient(@RequestBody ClientDTO clientDTO) {
        Client newClient = clientService.addClient(clientDTO);
        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Client> updateClient(@RequestBody ClientDTO clientDTO) {
        Client updatedClient = clientService.editClient(clientDTO);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable("id") Long id) {
        Client deletedClient = clientService.deleteClient(id);
        return new ResponseEntity<>(deletedClient, HttpStatus.OK);
    }
}
