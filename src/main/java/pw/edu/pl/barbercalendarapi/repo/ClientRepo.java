package pw.edu.pl.barbercalendarapi.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pw.edu.pl.barbercalendarapi.model.Client;

public interface ClientRepo extends JpaRepository<Client, Long> {
    List<Client> findClientsByNameIsContaining(String name);

    Optional<Client> findClientByEmail(String email);
}
