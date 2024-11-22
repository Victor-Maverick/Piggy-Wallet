package africa.semicolon.infrastructure.adapter.output;

import africa.semicolon.application.port.output.EventOutputPort;
import africa.semicolon.application.port.output.IdentityManagerPort;
import africa.semicolon.domain.dtos.response.LoginResponse;
import africa.semicolon.domain.model.AuthUser;
import africa.semicolon.domain.model.constant.Role;
import africa.semicolon.infrastructure.adapter.exception.WalletApiException;
import africa.semicolon.infrastructure.adapter.mapper.UserRestMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

public class KeycloakAdapter implements IdentityManagerPort {
    private final Keycloak keycloak;
    private final UserRestMapper userRestMapper;
    private final EventOutputPort eventOutputPort;

    @Value("${app.keycloak.realm}")
    private String realm;
    @Value("${app.keycloak.admin.clientId}")
    private String clientId;
    @Value("${token.url}")
    private String tokenUrl;
    @Value("${app.keycloak.admin.secretKey}")
    private String clientSecret;

    public KeycloakAdapter(Keycloak keycloak, UserRestMapper userRestMapper, EventOutputPort eventOutputPort) {
        this.keycloak = keycloak;
        this.userRestMapper = userRestMapper;
        this.eventOutputPort = eventOutputPort;
    }

    @Override
    public AuthUser register(AuthUser user) {
        UserRepresentation userRepresentation = buildUserRepresentation(user);
        buildCredentialRepresentation(user, userRepresentation);
        getUsersResource().create(userRepresentation);
        UserRepresentation userRepresentation1 = getUserRepresentation(user.getEmail());
        assignRole(userRepresentation1.getId(), user.getRole());
        user.setId(userRepresentation1.getId());
        eventOutputPort.sendCreateMessage(userRestMapper.toCreateUserResponse(user));
        return user;
    }

    @Override
    public void delete(String username) {
        UserRepresentation userRepresentation = getUserRepresentation(username);
        keycloak.realm(realm).users().get(userRepresentation.getId()).remove();
    }

    @Override
    public AuthUser update(String username, AuthUser user) {
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = updateUserDetails(username, user);
        updateUserCredentialRepresentation(user, userRepresentation);
        usersResource.get(userRepresentation.getId()).update(userRepresentation);
        return user;
    }

    private static void updateUserCredentialRepresentation(AuthUser user, UserRepresentation userRepresentation) {
        CredentialRepresentation passwordRepresentation = new CredentialRepresentation();
        passwordRepresentation.setType(CredentialRepresentation.PASSWORD);
        passwordRepresentation.setTemporary(false);
        passwordRepresentation.setValue(user.getPassword());
        userRepresentation.setCredentials(List.of(passwordRepresentation));
    }

    private @NotNull UserRepresentation updateUserDetails(String email, AuthUser user) {
        UserRepresentation userRepresentation = getUserRepresentation(email);
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        return userRepresentation;
    }
    @Override
    public LoginResponse login(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = buildParams(username,password);
        return getLoginResponse(restTemplate, params, tokenUrl);
    }

    private static LoginResponse getLoginResponse(RestTemplate restTemplate, MultiValueMap<String, String> params, String tokenUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        try {
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), LoginResponse.class);
        } catch (JsonProcessingException e) {
            throw new WalletApiException("Failed to process login response");
        } catch (HttpClientErrorException e) {
            throw new WalletApiException("Invalid credentials");
        }
    }

    private @NotNull MultiValueMap<String, String> buildParams(String username,String password) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("username", username);
        params.add("password", password);
        return params;
    }

    private static void buildCredentialRepresentation(AuthUser user, UserRepresentation userRepresentation) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(user.getPassword());
        credentialRepresentation.setTemporary(false);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
    }
    private static UserRepresentation buildUserRepresentation(AuthUser user) {
        UserRepresentation userRepresentation = new UserRepresentation();
        setUserUserRepresentation(user, userRepresentation);
        return userRepresentation;
    }

    private static void setUserUserRepresentation(AuthUser user, UserRepresentation userRepresentation) {
        userRepresentation.setEnabled(true);
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setUsername(user.getEmail());
        userRepresentation.setEmailVerified(false);
    }

    private UsersResource getUsersResource(){
        return keycloak.realm(realm).users();
    }
    private RolesResource getRolesResource(){
        return keycloak.realm(realm).roles();
    }


    private void assignRole(String userId, Role role) {
        RolesResource rolesResource = getRolesResource();
        UserResource user = getUser(userId);
        RoleRepresentation keycloakRole = rolesResource.get(String.valueOf(role)).toRepresentation();
        user.roles().realmLevel().add(Collections.singletonList(keycloakRole));
    }

    private UserResource getUser(String userId) {
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    private UserRepresentation getUserRepresentation(String email) {
        List<UserRepresentation> users = getUsersResource().searchByUsername(email,true);
        return users.get(0);
    }
}
