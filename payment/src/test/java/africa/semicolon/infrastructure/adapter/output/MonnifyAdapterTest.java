package africa.semicolon.infrastructure.adapter.output;

import africa.semicolon.domain.data.dto.request.InitializeTransactionRequest;
import africa.semicolon.domain.data.dto.request.InitializeTransferRequest;
import africa.semicolon.domain.data.dto.response.InitializeMonnifyTransferResponse;
import africa.semicolon.domain.data.dto.response.InitializeTransactionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static africa.semicolon.domain.data.constant.PaymentMethod.CARD;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MonnifyAdapterTest {

    @Autowired
    private MonnifyAdapter monnifyAdapter;

    @Test
    public void depositSuccessfulTest(){
        InitializeTransactionRequest request =InitializeTransactionRequest.builder()
                .email("test@email.com")
                .amount(500)
                .firstName("first name")
                .lastName("last name")
                .paymentMethod(CARD)
                .paymentDescription("trial transaction")
                .build();
        InitializeTransactionResponse response = monnifyAdapter.deposit(request);
        assertThat(response).isNotNull();
    }

    @Test
    public void transferSuccessfulTest(){
        InitializeTransferRequest request = InitializeTransferRequest.builder()
                .amount(500)
                .receiverAccount("8148624687")
                .destinationCode("919")
                .reference("unique-reference")
                .senderEmail("test@email.com")
                .senderAccount("2093500199")
                .build();
        InitializeMonnifyTransferResponse response = monnifyAdapter.transfer(request);
        assertThat(response).isNotNull();
    }

}