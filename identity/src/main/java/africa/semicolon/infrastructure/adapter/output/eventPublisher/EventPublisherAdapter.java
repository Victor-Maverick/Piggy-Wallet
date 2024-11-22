package africa.semicolon.infrastructure.adapter.output.eventPublisher;

import africa.semicolon.domain.dtos.response.CreateUserResponse;
import africa.semicolon.application.port.output.EventOutputPort;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

public class EventPublisherAdapter implements EventOutputPort {
    private final Producer<CreateUserResponse> producer;

    public EventPublisherAdapter(PulsarClient pulsarClient) throws PulsarClientException {
        this.producer = pulsarClient.newProducer(Schema.JSON(CreateUserResponse.class))
                .topic("create-wallet-pub")
                .create();
    }



    public void sendCreateMessage(CreateUserResponse userRegistrationDto) {
        try {
            producer.send(userRegistrationDto);
            producer.close();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

}
