package africa.semicolon.infrastructure.adapter.mapper;

import africa.semicolon.domain.dtos.request.RegisterRequest;
import africa.semicolon.domain.dtos.response.CreateUserResponse;
import africa.semicolon.domain.model.AuthUser;
import org.mapstruct.Mapper;

@Mapper
public interface UserRestMapper {
    AuthUser toAuthUser(RegisterRequest registerRequest);
    CreateUserResponse toCreateUserResponse(AuthUser user);
}
