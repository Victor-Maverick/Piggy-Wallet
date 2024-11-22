package africa.semicolon.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    private UUID id;
    private BigDecimal amount;
    private Wallet wallet;
}
