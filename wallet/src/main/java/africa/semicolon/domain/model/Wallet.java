package africa.semicolon.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallet {
    private UUID id;
    private BigDecimal balance;
    private String pin;
    private User user;
}
