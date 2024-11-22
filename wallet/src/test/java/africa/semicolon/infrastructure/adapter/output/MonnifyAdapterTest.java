package africa.semicolon.infrastructure.adapter.output;

import africa.semicolon.domain.data.dtos.request.InitializeTransactionRequest;
import africa.semicolon.domain.data.dtos.response.InitializeTransactionResponse;
import africa.semicolon.domain.service.WalletService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static africa.semicolon.domain.data.constants.PaymentMethod.CARD;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MonnifyAdapterTest {
    private static final Logger log = LoggerFactory.getLogger(MonnifyAdapterTest.class);
    @Autowired
    private WalletService walletService;

    @Test
    public void successfulDepositTest() {
        InitializeTransactionRequest request =InitializeTransactionRequest
                .builder()
                .email("asa@gmail.com")
                .amount(200.00)
                .paymentDescription("trial transaction")
                .paymentMethod(CARD)
                .build();
        InitializeTransactionResponse response = walletService.deposit(request);
        assertThat(response).isNotNull();
        log.info(response.toString());

    }

    @Test
    public void transfer() {
    }
}