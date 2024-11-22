package africa.semicolon.application.port.input.walletUseCase;

import africa.semicolon.domain.data.dtos.response.CreateUserEvent;

public interface CreateWalletUseCase {
    void createWallet(CreateUserEvent createUserEvent);
}
