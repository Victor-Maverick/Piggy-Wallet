package africa.semicolon.application.port.output;

import africa.semicolon.domain.dtos.response.LoginResponse;
import africa.semicolon.domain.model.AuthUser;

public interface IdentityManagerPort {
    AuthUser register(AuthUser user);
    void delete(String username);
    AuthUser update(String username, AuthUser user);
    LoginResponse login(String username, String password);
}
