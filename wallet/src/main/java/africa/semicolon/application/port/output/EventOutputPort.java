package africa.semicolon.application.port.output;

import africa.semicolon.domain.data.dtos.request.InitiateDepositEvent;
import africa.semicolon.domain.data.dtos.response.TransferEvent;

public interface EventOutputPort {
    void sendDepositEvent(InitiateDepositEvent request);
    void sendTransferEvent(TransferEvent request);
}
