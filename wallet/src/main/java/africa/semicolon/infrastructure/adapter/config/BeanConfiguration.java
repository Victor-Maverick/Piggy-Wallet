package africa.semicolon.infrastructure.adapter.config;

import africa.semicolon.application.port.output.UserOutputPort;
import africa.semicolon.application.port.output.WalletOutputPort;
import africa.semicolon.domain.service.WalletService;
import africa.semicolon.infrastructure.adapter.input.eventListener.EventListenerAdapter;
import africa.semicolon.infrastructure.adapter.mapper.EventMapper;
import africa.semicolon.infrastructure.adapter.output.eventPublisher.EventPublisher;
import africa.semicolon.infrastructure.adapter.output.persistence.UserPersistenceAdapter;
import africa.semicolon.infrastructure.adapter.output.persistence.WalletPersistenceAdapter;
import africa.semicolon.infrastructure.adapter.output.persistence.mapper.UserPersistenceMapper;
import africa.semicolon.infrastructure.adapter.output.persistence.mapper.WalletPersistenceMapper;
import africa.semicolon.infrastructure.adapter.output.persistence.repository.UserRepository;
import africa.semicolon.infrastructure.adapter.output.persistence.repository.WalletRepository;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WalletPersistenceAdapter walletPersistenceAdapter(final WalletRepository walletRepository, final WalletPersistenceMapper mapper) {
        return new WalletPersistenceAdapter(walletRepository, mapper);
    }

    @Bean
    public UserPersistenceAdapter userPersistenceAdapter(final UserRepository userRepository, final UserPersistenceMapper userPersistenceMapper) {
        return new UserPersistenceAdapter(userRepository, userPersistenceMapper);
    }

    @Bean
    public PulsarClient pulsarClient() throws PulsarClientException {
        return PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .build();
    }


    @Bean
    public EventListenerAdapter eventListener(PulsarClient pulsarClient, WalletService walletService) throws PulsarClientException{
        return new EventListenerAdapter(pulsarClient,walletService);
    }

    @Bean
    public WalletService walletService(final WalletOutputPort walletOutputPort, final UserOutputPort userOutputPort, final PasswordEncoder passwordEncoder, final EventPublisher publisher, final EventMapper mapper, final WebClient.Builder webClientBuilder){
        return new WalletService(walletOutputPort, userOutputPort, passwordEncoder,publisher,mapper,webClientBuilder);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public EventPublisher eventPublisher(PulsarClient pulsarClient) throws PulsarClientException {
        return new EventPublisher(pulsarClient);
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
