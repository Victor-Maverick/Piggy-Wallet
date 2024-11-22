package africa.semicolon.infrastructure.adapter.output.persistence.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.GenerationType.AUTO;
import static java.time.LocalDateTime.now;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID id;
    private BigDecimal balance;
    private String pin;
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateCreated;
    @OneToOne
    private UserEntity user;

    @PrePersist
    public void prePersist() {
        dateCreated = now();
    }
}
