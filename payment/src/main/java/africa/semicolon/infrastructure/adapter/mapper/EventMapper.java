package africa.semicolon.infrastructure.adapter.mapper;

import africa.semicolon.domain.data.dto.request.InitializeTransactionRequest;
import africa.semicolon.domain.data.dto.request.InitiateDepositEvent;
import org.mapstruct.Mapper;

@Mapper
public interface EventMapper {
    InitializeTransactionRequest toInitializeTransactionRequest(InitiateDepositEvent event);
}
