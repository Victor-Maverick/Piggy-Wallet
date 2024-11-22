package africa.semicolon.infrastructure.adapter.output.persistence.mapper;

import africa.semicolon.domain.model.Wallet;
import africa.semicolon.infrastructure.adapter.output.persistence.entity.WalletEntity;
import org.mapstruct.Mapper;

@Mapper
public interface WalletPersistenceMapper {
    WalletEntity toWalletEntity(Wallet wallet);

    Wallet toWallet(WalletEntity walletEntity);
}
