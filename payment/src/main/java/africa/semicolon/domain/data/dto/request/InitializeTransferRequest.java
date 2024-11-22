package africa.semicolon.domain.data.dto.request;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializeTransferRequest{
    private UUID walletId;
    private String senderEmail;
    private double amount;
    private String reference;
    private String narration;
    private String destinationCode;
    private String receiverAccount;
    private String senderAccount;
}
