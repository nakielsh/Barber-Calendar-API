package pw.edu.pl.barbercalendarapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String imageUrl;
}
