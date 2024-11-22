package africa.semicolon.application.port.output;

import africa.semicolon.domain.model.Wallet;

public interface WalletOutputPort {
    Wallet save(Wallet wallet);
}
