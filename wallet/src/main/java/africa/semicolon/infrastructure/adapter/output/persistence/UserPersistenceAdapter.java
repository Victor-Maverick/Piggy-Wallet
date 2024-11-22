package africa.semicolon.infrastructure.adapter.output.persistence;

import africa.semicolon.application.port.output.UserOutputPort;
import africa.semicolon.domain.model.User;
import africa.semicolon.domain.exception.WalletException;
import africa.semicolon.infrastructure.adapter.output.persistence.entity.UserEntity;
import africa.semicolon.infrastructure.adapter.output.persistence.mapper.UserPersistenceMapper;
import africa.semicolon.infrastructure.adapter.output.persistence.repository.UserRepository;

public class UserPersistenceAdapter implements UserOutputPort {
    private final UserRepository userRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    public UserPersistenceAdapter(UserRepository userRepository, UserPersistenceMapper userPersistenceMapper) {
        this.userRepository = userRepository;
        this.userPersistenceMapper = userPersistenceMapper;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userPersistenceMapper.toUserEntity(user);
        userRepository.save(userEntity);
        return userPersistenceMapper.toUser(userEntity);
    }

    @Override
    public User getByEmail(String email) {
        UserEntity userEntity = userRepository.getByEmail(email)
                .orElseThrow(()->new WalletException(email + "not found"));
        return userPersistenceMapper.toUser(userEntity);
    }
}
