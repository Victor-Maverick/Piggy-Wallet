package africa.semicolon.infrastructure.adapter.output.eventPublisher;

import africa.semicolon.application.port.output.EventOutputPort;
import africa.semicolon.domain.data.dtos.request.InitiateDepositEvent;
import africa.semicolon.domain.data.dtos.response.TransferEvent;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

public class EventPublisher implements EventOutputPort {
    private final Producer<InitiateDepositEvent> producer;
    private final Producer<TransferEvent>transferProducer;

    public EventPublisher(PulsarClient pulsarClient) throws PulsarClientException {
        this.producer = pulsarClient.newProducer(Schema.JSON(InitiateDepositEvent.class))
                .topic("initialize_deposit-pub")
                .create();
        this.transferProducer = pulsarClient.newProducer(Schema.JSON(TransferEvent.class))
                .topic("transfer-pub")
                .create();
    }



    @Override
    public void sendDepositEvent(InitiateDepositEvent request) {
        try{
            producer.send(request);
            producer.close();
        }
        catch (PulsarClientException e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendTransferEvent(TransferEvent request) {
        try{
            transferProducer.send(request);
            transferProducer.close();
        }
        catch (PulsarClientException e){
            e.printStackTrace();
        }
    }
}
