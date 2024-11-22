package africa.semicolon.application.port.input.identityManagementUseCases;

import africa.semicolon.domain.model.AuthUser;

public interface UpdateUserUseCase {
    AuthUser update(String username, AuthUser authUser);
}
