package africa.semicolon.infrastructure.adapter.input.eventListener;

import africa.semicolon.domain.data.dtos.response.CreateUserEvent;
import africa.semicolon.domain.data.dtos.response.DepositResponseEvent;
import africa.semicolon.domain.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;

@Slf4j
public class EventListenerAdapter {
    private final PulsarClient pulsarClient;
    private final WalletService walletService;

    public EventListenerAdapter(PulsarClient pulsarClient, WalletService walletService) throws PulsarClientException {
        this.pulsarClient = pulsarClient;
        this.walletService = walletService;
        startEventListeners();
    }

    private void startEventListeners() throws PulsarClientException {
        handleCreatedUserEvent();

    }

    private void handleCreatedUserEvent() throws PulsarClientException {
        Consumer<CreateUserEvent> consumer = pulsarClient.newConsumer(Schema.JSON(CreateUserEvent.class))
                .topic("create-wallet-pub")
                .subscriptionName("create-wallet-sub")
                .subscriptionType(SubscriptionType.Shared)  // Optional: Set subscription type explicitly
                .messageListener(this::processCreateUserEvent)
                .subscribe();

        log.info("Consumer for CreateUserEvent subscribed successfully.");
    }

    private void processCreateUserEvent(Consumer<CreateUserEvent> consumer, Message<CreateUserEvent> msg) {
        try {
            CreateUserEvent createUserEvent = msg.getValue();
            processUserRegistration(createUserEvent);
            log.info("CreateUserEvent received: {}", createUserEvent);
            consumer.acknowledge(msg);
        } catch (PulsarClientException e) {
            log.error("Error processing CreateUserEvent: {}", e.getMessage(), e);
            consumer.negativeAcknowledge(msg);
        }
    }

    private void processUserRegistration(CreateUserEvent createUserEvent) {
        walletService.createWallet(createUserEvent);
    }

    public void handleDepositEvent() throws PulsarClientException {
        Consumer<DepositResponseEvent> consumer = pulsarClient.newConsumer(Schema.JSON(DepositResponseEvent.class))
                .topic("initialize_deposit-pub")
                .subscriptionName("initialize_deposit-sub")
                .subscriptionType(SubscriptionType.Shared)  // Optional: Set subscription type explicitly
                .messageListener(this::processDepositEvent)
                .subscribe();

        log.info("Consumer for DepositResponseEvent subscribed successfully.");
    }

    private void processDepositEvent(Consumer<DepositResponseEvent> consumer, Message<DepositResponseEvent> msg) {
        try {
            DepositResponseEvent depositResponseEvent = msg.getValue();
            log.info("DepositResponseEvent received: {}", depositResponseEvent);
            consumer.acknowledge(msg);
        } catch (PulsarClientException e) {
            log.error("Error processing DepositResponseEvent: {}", e.getMessage(), e);
            consumer.negativeAcknowledge(msg);
        }
    }

    public void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                pulsarClient.close();
                log.info("Pulsar client closed successfully.");
            } catch (PulsarClientException e) {
                log.error("Error closing Pulsar client: {}", e.getMessage(), e);
            }
        }));
    }
}
