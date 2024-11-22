package africa.semicolon.infrastructure.adapter.output.persistence;

import africa.semicolon.domain.dtos.response.LoginResponse;
import africa.semicolon.domain.model.AuthUser;
import africa.semicolon.infrastructure.adapter.output.KeycloakAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static africa.semicolon.domain.model.constant.Role.ADMIN;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class KeycloakAdapterTest {
    @Autowired
    private KeycloakAdapter keycloakAdapter;



//    @AfterEach
//    public void setUp(){
//        keycloakAdapter.delete("john@gmail.com");
//    }

    @Test
    public void registerUserSuccessfulTest() {
        AuthUser user = createNewUser();
        assertThat(user.getId()).isNotNull();
    }

    private AuthUser createNewUser() {
        AuthUser user = AuthUser.builder()
                .firstName("john")
                .lastName("doe")
                .email("john@gmail.com")
                .password("password")
                .role(ADMIN)
                .pin("1111")
                .build();
        user = keycloakAdapter.register(user);
        return user;
    }

    @Test
    public void updateExistingUserSuccessfulTest() {
        AuthUser user = createNewUser();
        AuthUser userToUpdate = AuthUser.builder()
                        .firstName("vic")
                .lastName("doe")
                .password("password")
                                .build();
        user = keycloakAdapter.update(user.getEmail(), userToUpdate);
        assertThat(user.getFirstName()).isEqualTo("vic");
    }

    @Test
    public void loginWithCorrectCredentialsTest_successful() {
        AuthUser user = createNewUser();
        LoginResponse response = keycloakAdapter.login(user.getEmail(), "password");
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isNotNull();
    }
}