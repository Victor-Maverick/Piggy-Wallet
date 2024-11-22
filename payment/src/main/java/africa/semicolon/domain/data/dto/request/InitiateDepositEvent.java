package africa.semicolon.domain.data.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitiateDepositEvent {
    private String senderId;
    private String email;
    private String firstName;
    private String lastName;
    private double amount;
    private String paymentDescription;
    private String paymentMethod;
}
