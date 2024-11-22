package africa.semicolon.infrastructure.adapter.mapper;

import africa.semicolon.domain.data.dtos.request.InitializeTransactionRequest;
import africa.semicolon.domain.data.dtos.request.InitiateDepositEvent;
import africa.semicolon.domain.data.dtos.response.DepositResponseEvent;
import africa.semicolon.domain.data.dtos.response.InitializeTransactionResponse;
import org.mapstruct.Mapper;

@Mapper
public interface EventMapper {
    InitiateDepositEvent toInitiateDepositEvent(InitializeTransactionRequest initializeTransactionRequest);

    InitializeTransactionResponse toInitializeTransactionResponse(DepositResponseEvent depositResponseEvent);
}
