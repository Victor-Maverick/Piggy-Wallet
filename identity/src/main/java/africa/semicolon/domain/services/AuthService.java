package africa.semicolon.domain.services;

import africa.semicolon.application.port.input.identityManagementUseCases.DeleteUseCase;
import africa.semicolon.application.port.input.identityManagementUseCases.LoginUseCase;
import africa.semicolon.application.port.input.identityManagementUseCases.RegisterUseCase;
import africa.semicolon.application.port.input.identityManagementUseCases.UpdateUserUseCase;
import africa.semicolon.domain.dtos.response.LoginResponse;
import africa.semicolon.domain.model.AuthUser;
import africa.semicolon.infrastructure.adapter.output.KeycloakAdapter;

public class AuthService implements RegisterUseCase, UpdateUserUseCase, DeleteUseCase, LoginUseCase {
    private final KeycloakAdapter keycloakAdapter;

    public AuthService(KeycloakAdapter keycloakAdapter) {
        this.keycloakAdapter = keycloakAdapter;
    }

    @Override
    public void deleteUser(String username) {
        keycloakAdapter.delete(username);
    }

    @Override
    public LoginResponse login(String username, String password) {
        return keycloakAdapter.login(username,password);
    }

    @Override
    public AuthUser register(AuthUser user) {
        return keycloakAdapter.register(user);
    }

    @Override
    public AuthUser update(String username, AuthUser authUser) {
        return keycloakAdapter.update(username, authUser);
    }
}
