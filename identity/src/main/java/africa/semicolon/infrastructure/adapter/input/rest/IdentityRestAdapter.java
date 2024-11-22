package africa.semicolon.infrastructure.adapter.input.rest;

import africa.semicolon.application.port.input.identityManagementUseCases.RegisterUseCase;
import africa.semicolon.domain.dtos.request.RegisterRequest;
import africa.semicolon.domain.dtos.response.ApiResponse;
import africa.semicolon.domain.model.AuthUser;
import africa.semicolon.infrastructure.adapter.mapper.UserRestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/identity")
public class IdentityRestAdapter {
    private static final Logger log = LoggerFactory.getLogger(IdentityRestAdapter.class);
    private final RegisterUseCase registerUseCase;
    private final UserRestMapper userRestMapper;

    public IdentityRestAdapter(RegisterUseCase registerUseCase, UserRestMapper userRestMapper) {
        this.registerUseCase = registerUseCase;
        this.userRestMapper = userRestMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        log.info("here too");
        AuthUser user =userRestMapper.toAuthUser(registerRequest);
        log.info("user: " + user);
        user = registerUseCase.register(user);
        return new ResponseEntity<>(new ApiResponse<>(true,userRestMapper.toCreateUserResponse(user)), CREATED);

    }
}
