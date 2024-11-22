package africa.semicolon.application.port.output;

import africa.semicolon.domain.model.User;

public interface UserOutputPort {
    User save(User user);

    User getByEmail(String email);
}
