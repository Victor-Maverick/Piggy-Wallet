package africa.semicolon.domain.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserEvent {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String pin;
}
