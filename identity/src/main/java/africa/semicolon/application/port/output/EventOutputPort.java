package africa.semicolon.application.port.output;

import africa.semicolon.domain.dtos.response.CreateUserResponse;

public interface EventOutputPort {
    void sendCreateMessage(CreateUserResponse response);
}
