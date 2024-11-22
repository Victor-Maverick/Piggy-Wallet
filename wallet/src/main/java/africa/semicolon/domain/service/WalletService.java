package africa.semicolon.domain.service;

import africa.semicolon.application.port.input.walletUseCase.CreateWalletUseCase;
import africa.semicolon.application.port.input.walletUseCase.InitializeDepositUseCase;
import africa.semicolon.application.port.input.walletUseCase.initializeTransferUseCase;
import africa.semicolon.application.port.output.UserOutputPort;
import africa.semicolon.application.port.output.WalletOutputPort;
import africa.semicolon.domain.data.dtos.request.InitializeTransactionRequest;
import africa.semicolon.domain.data.dtos.request.InitializeTransferRequest;
import africa.semicolon.domain.data.dtos.request.InitiateDepositEvent;
import africa.semicolon.domain.data.dtos.response.CreateUserEvent;
import africa.semicolon.domain.data.dtos.response.InitializeMonnifyTransferResponse;
import africa.semicolon.domain.data.dtos.response.InitializeTransactionResponse;
import africa.semicolon.domain.model.User;
import africa.semicolon.domain.model.Wallet;
import africa.semicolon.infrastructure.adapter.mapper.EventMapper;
import africa.semicolon.infrastructure.adapter.output.eventPublisher.EventPublisher;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


public class WalletService implements CreateWalletUseCase,InitializeDepositUseCase<InitializeTransactionResponse,InitializeTransactionRequest>, initializeTransferUseCase<InitializeMonnifyTransferResponse, InitializeTransferRequest> {
    private final WalletOutputPort walletOutputPort;
    private final UserOutputPort userOutputPort;
    private final PasswordEncoder passwordEncoder;
    private final EventPublisher eventPublisher;
    private final EventMapper eventMapper;
    private final WebClient.Builder webClientBuilder;

    public WalletService(WalletOutputPort walletOutputPort, UserOutputPort userOutputPort, PasswordEncoder passwordEncoder, EventPublisher eventPublisher, EventMapper eventMapper, WebClient.Builder webClientBuilder) {
        this.walletOutputPort = walletOutputPort;
        this.userOutputPort = userOutputPort;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
        this.eventMapper = eventMapper;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public void createWallet(CreateUserEvent response) {
        User user = User.builder().firstName(response.getFirstName())
                .lastName(response.getLastName())
                .email(response.getEmail())
                .password(passwordEncoder.encode(response.getPassword())).build();
        user = userOutputPort.save(user);
        Wallet wallet = Wallet.builder()
                .pin(passwordEncoder.encode(response.getPin()))
                .balance(new BigDecimal("0.00"))
                .build();
        wallet.setUser(user);
        walletOutputPort.save(wallet);
    }

    @Override
    public InitializeTransactionResponse deposit(InitializeTransactionRequest request) {
        userOutputPort.getByEmail(request.getEmail());
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:7777").build();
        Mono<InitializeTransactionResponse> responseMono = buildDeposit(webClient, request);
        return responseMono.block();

    }

    private static Mono<InitializeTransactionResponse> buildDeposit(WebClient webClient, InitializeTransactionRequest depositEvent) {
        return webClient
                .method(HttpMethod.POST)
                .uri("/api/v1/payment/deposit")
                .bodyValue(depositEvent)
                .retrieve()
                .bodyToMono(InitializeTransactionResponse.class);
    }

    @Override
    public InitializeMonnifyTransferResponse transfer(InitializeTransferRequest request) {
        userOutputPort.getByEmail(request.getSenderEmail());
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:7777").build();
        Mono<InitializeMonnifyTransferResponse> monoResponse = buildTransfer(webClient, request);
        return monoResponse.block();
    }

    private Mono<InitializeMonnifyTransferResponse> buildTransfer(WebClient webClient, InitializeTransferRequest request) {
        return webClient
                .method(HttpMethod.POST)
                .uri("/api/v1/payment/transfer")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(InitializeMonnifyTransferResponse.class);
    }

}
