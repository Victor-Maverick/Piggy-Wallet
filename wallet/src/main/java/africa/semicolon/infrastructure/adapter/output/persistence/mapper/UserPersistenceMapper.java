package africa.semicolon.infrastructure.adapter.output.persistence.mapper;

import africa.semicolon.domain.model.User;
import africa.semicolon.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserPersistenceMapper {
    UserEntity toUserEntity(User user);
    User toUser(UserEntity userEntity);
}
