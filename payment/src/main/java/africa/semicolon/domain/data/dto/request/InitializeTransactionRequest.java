package africa.semicolon.domain.data.dto.request;

import africa.semicolon.domain.data.constant.PaymentMethod;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializeTransactionRequest{
    private UUID senderId;
    private String email;
    private String firstName;
    private String lastName;
    private double amount;
    private String paymentDescription;
    private PaymentMethod paymentMethod;
}
