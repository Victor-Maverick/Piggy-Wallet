package africa.semicolon.application.port.input.identityManagementUseCases;

import africa.semicolon.domain.dtos.response.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(String username, String password);
}
