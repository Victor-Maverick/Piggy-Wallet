package africa.semicolon.domain.data.dtos.request;

import africa.semicolon.domain.data.constants.PaymentMethod;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializeTransactionRequest {
    private String email;
    private double amount;
    private String paymentDescription;
    private PaymentMethod paymentMethod;
}
