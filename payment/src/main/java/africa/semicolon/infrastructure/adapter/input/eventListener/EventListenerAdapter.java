package africa.semicolon.infrastructure.adapter.input.eventListener;

import africa.semicolon.domain.data.dto.request.InitializeTransactionRequest;
import africa.semicolon.domain.data.dto.request.InitiateDepositEvent;
import africa.semicolon.domain.data.dto.response.InitializeTransactionResponse;
import africa.semicolon.infrastructure.adapter.mapper.EventMapper;
import africa.semicolon.infrastructure.adapter.output.MonnifyAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class EventListenerAdapter {
    private final PulsarClient pulsarClient;
    private final MonnifyAdapter monnifyAdapter;
    private final EventMapper mapper;

    public EventListenerAdapter(PulsarClient pulsarClient, MonnifyAdapter monnifyAdapter, EventMapper mapper) throws PulsarClientException {
        this.pulsarClient = pulsarClient;
        this.monnifyAdapter = monnifyAdapter;
        this.mapper = mapper;
        handleDepositEvent();
    }

    private void handleDepositEvent() throws PulsarClientException {

        Consumer<InitiateDepositEvent> consumer1 = pulsarClient.newConsumer(Schema.JSON(InitiateDepositEvent.class))
                .topic("initialize_deposit-pub")
                .subscriptionName("initialize_deposit-sub")
                .messageListener((consumer, msg) -> {
                    try {
                        InitiateDepositEvent depositEvent = msg.getValue();
                        processDepositEvent(depositEvent);
                        log.info("Message Received");
                        consumer.acknowledge(msg);

                    } catch (PulsarClientException e) {
                        consumer.negativeAcknowledge(msg);
                    }
                })
                .subscribe();
    }

    private void processDepositEvent(InitiateDepositEvent depositEvent) {
        InitializeTransactionRequest transactionRequest = mapper.toInitializeTransactionRequest(depositEvent);
        monnifyAdapter.deposit(transactionRequest);
    }
}
