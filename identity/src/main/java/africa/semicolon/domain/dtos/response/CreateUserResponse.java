package africa.semicolon.domain.dtos.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateUserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String pin;
}
