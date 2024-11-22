package africa.semicolon.domain.data.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InitializeTransferRequest {
    private String senderEmail;
    private double amount;
    private String reference;
    private String narration;
    private String destinationCode;
    private String receiverAccount;
    private String senderAccount;
}
