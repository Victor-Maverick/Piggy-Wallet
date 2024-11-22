package africa.semicolon.application.port.input.identityManagementUseCases;

import africa.semicolon.domain.model.AuthUser;

public interface RegisterUseCase {
    AuthUser register(AuthUser user);
}
