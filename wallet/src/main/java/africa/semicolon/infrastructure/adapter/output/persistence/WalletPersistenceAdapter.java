package africa.semicolon.infrastructure.adapter.output.persistence;

import africa.semicolon.application.port.output.WalletOutputPort;
import africa.semicolon.domain.model.Wallet;
import africa.semicolon.infrastructure.adapter.output.persistence.entity.WalletEntity;
import africa.semicolon.infrastructure.adapter.output.persistence.mapper.WalletPersistenceMapper;
import africa.semicolon.infrastructure.adapter.output.persistence.repository.WalletRepository;

public class WalletPersistenceAdapter implements WalletOutputPort {
    private final WalletRepository walletRepository;
    private final WalletPersistenceMapper walletPersistenceMapper;

    public WalletPersistenceAdapter(WalletRepository walletRepository, WalletPersistenceMapper walletPersistenceMapper) {
        this.walletRepository = walletRepository;
        this.walletPersistenceMapper = walletPersistenceMapper;
    }

    @Override
    public Wallet save(Wallet wallet) {
        WalletEntity walletEntity = walletPersistenceMapper.toWalletEntity(wallet);
        walletEntity = this.walletRepository.save(walletEntity);
        return walletPersistenceMapper.toWallet(walletEntity);
    }
}
