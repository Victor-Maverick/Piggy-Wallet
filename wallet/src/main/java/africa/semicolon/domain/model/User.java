package africa.semicolon.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
