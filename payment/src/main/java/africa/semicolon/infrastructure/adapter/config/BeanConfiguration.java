package africa.semicolon.infrastructure.adapter.config;

import africa.semicolon.domain.service.MonnifyPaymentService;
import africa.semicolon.infrastructure.adapter.input.eventListener.EventListenerAdapter;
import africa.semicolon.infrastructure.adapter.mapper.EventMapper;
import africa.semicolon.infrastructure.adapter.output.MonnifyAdapter;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {

    @Bean
    public MonnifyAdapter monnifyAdapter(final RestTemplate restTemplate){
        return new MonnifyAdapter(restTemplate);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public EventListenerAdapter eventListenerAdapter(PulsarClient pulsarClient, MonnifyAdapter monnifyAdapter, EventMapper mapper) throws PulsarClientException {
        return new EventListenerAdapter(pulsarClient,monnifyAdapter,mapper);
    }

    @Bean
    public MonnifyPaymentService paymentService(final MonnifyAdapter monnifyAdapter) {
        return new MonnifyPaymentService(monnifyAdapter);
    }
}
