package pw.edu.pl.barbercalendarapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false, updatable = false)
    private Long id;

    @NonNull private String name;
    @NonNull private String email;
    @NonNull private String phone;
    @NonNull private String imageUrl;

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Appointment> appointmentList = new ArrayList<>();

    @Override
    public String toString() {
        return "Client{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", email='"
                + email
                + '\''
                + ", phone='"
                + phone
                + '\''
                + ", imageUrl='"
                + imageUrl
                + '\''
                + '}';
    }
}
