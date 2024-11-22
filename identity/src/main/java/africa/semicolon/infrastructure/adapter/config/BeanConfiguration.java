package africa.semicolon.infrastructure.adapter.config;

import africa.semicolon.application.port.output.EventOutputPort;
import africa.semicolon.domain.services.AuthService;
import africa.semicolon.infrastructure.adapter.mapper.UserRestMapper;
import africa.semicolon.infrastructure.adapter.output.eventPublisher.EventPublisherAdapter;
import africa.semicolon.infrastructure.adapter.output.KeycloakAdapter;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Value("${app.keycloak.admin.clientId}")
    private String clientId;
    @Value("${app.keycloak.admin.secretKey}")
    private String secretKey;
    @Value("${app.keycloak.realm}")
    private String realm;
    @Value("${app.keycloak.serverUrl}")
    private String serverUrl;
    @Value("${spring.pulsar.client.service-url}")
    private String pulsarUrl;

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .clientSecret(secretKey)
                .clientId(clientId)
                .grantType("client_credentials")
                .realm(realm)
                .serverUrl(serverUrl)
                .build();
    }

    @Bean
    public KeycloakAdapter keycloakAdapter(final Keycloak keycloak, final UserRestMapper userRestMapper, final EventOutputPort eventOutputPort){
        return new KeycloakAdapter(keycloak,userRestMapper,eventOutputPort);
    }

    @Bean
    public AuthService authService(final KeycloakAdapter keycloakAdapter){
        return new AuthService(keycloakAdapter);
    }
    @Bean
    public PulsarClient pulsarClient() throws PulsarClientException {
        return PulsarClient.builder()
                .serviceUrl(pulsarUrl)
                .build();
    }

    @Bean
    public EventPublisherAdapter pulsarProducer(PulsarClient pulsarClient) throws PulsarClientException {
        return new EventPublisherAdapter(pulsarClient);
    }
}
