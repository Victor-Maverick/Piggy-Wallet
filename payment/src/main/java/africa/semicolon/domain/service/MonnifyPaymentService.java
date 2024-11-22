package africa.semicolon.domain.service;

import africa.semicolon.application.port.input.paymentUseCase.DepositUseCase;
import africa.semicolon.application.port.input.paymentUseCase.TransferUseCase;
import africa.semicolon.domain.data.dto.request.InitializeTransactionRequest;
import africa.semicolon.domain.data.dto.request.InitializeTransferRequest;
import africa.semicolon.domain.data.dto.response.InitializeMonnifyTransferResponse;
import africa.semicolon.domain.data.dto.response.InitializeTransactionResponse;
import africa.semicolon.infrastructure.adapter.output.MonnifyAdapter;

public class MonnifyPaymentService implements DepositUseCase<InitializeTransactionResponse, InitializeTransactionRequest>, TransferUseCase<InitializeMonnifyTransferResponse, InitializeTransferRequest> {
    private final MonnifyAdapter monnifyAdapter;

    public MonnifyPaymentService(MonnifyAdapter monnifyAdapter) {
        this.monnifyAdapter = monnifyAdapter;
    }

    @Override
    public InitializeTransactionResponse deposit(InitializeTransactionRequest request) {
        return monnifyAdapter.deposit(request);
    }

    @Override
    public InitializeMonnifyTransferResponse transfer(InitializeTransferRequest request) {
        return monnifyAdapter.transfer(request);
    }
}
