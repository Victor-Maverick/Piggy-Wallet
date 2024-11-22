package africa.semicolon.domain.model;

import africa.semicolon.domain.model.constant.Role;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUser {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String pin;
    private Role role;
}
