package com.haushekmiva.service;

import com.haushekmiva.BaseIntegrationTest;
import com.haushekmiva.dto.*;
import com.haushekmiva.model.Session;
import com.haushekmiva.model.User;
import com.haushekmiva.repository.SessionRepository;
import com.haushekmiva.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@TestConstructor(autowireMode = ALL)
@RequiredArgsConstructor
public class AuthServiceTest extends BaseIntegrationTest {

    private static final String TEST_PASSWORD = "test_password";
    private static final LocalDateTime OLD_DATE = LocalDateTime.of(1979, 1, 1, 1, 1);

    private final AuthService authService;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;


    @Test
    public void registerUser_correctInput_newUserCreated() {
        String test_username = getUniqueUsername();

        authService.registerUser(new UserRegisterRequest(test_username, TEST_PASSWORD, TEST_PASSWORD));
        Optional<User> user = userRepository.getByLogin(test_username);

        assertThat(user)
                .withFailMessage("Entry with login %s was not created.", test_username)
                .isPresent();

        assertThat(user.get().getLogin())
                .withFailMessage("Entry should have username equal to %s", test_username)
                .isEqualTo(test_username);
    }

    @Test
    public void registerUser_correctInput_newSessionCreated() {
        String test_username = getUniqueUsername();

        AuthResponse authResponse = authService.registerUser(
                new UserRegisterRequest(test_username, TEST_PASSWORD, TEST_PASSWORD)
        );

        assertThat(authResponse).isInstanceOf(AuthSuccess.class);

        AuthSuccess authSuccess = (AuthSuccess) authResponse;

        Optional<Session> session = sessionRepository.getById(authSuccess.sessionId());

        assertThat(session)
                .withFailMessage("Session for user with username %s no exists.", test_username)
                .isPresent();
    }

    @Test
    public void registerUser_nonUniqueLogin_authError() {
        String test_username = getUniqueUsername();
        authService.registerUser(new UserRegisterRequest(test_username, TEST_PASSWORD, TEST_PASSWORD));
        AuthResponse errorAuth = authService.registerUser(new UserRegisterRequest(test_username, TEST_PASSWORD, TEST_PASSWORD));

        assertThat(errorAuth)
                .withFailMessage("Create user with non-unique username should send error.")
                .isInstanceOf(AuthError.class);

    }

    @Test
    public void getUserBySession_sessionExpired_emptyResponse() {
        String test_username = getUniqueUsername();

        userRepository.create(new User(test_username, TEST_PASSWORD));
        User user = userRepository.getByLogin(test_username).get();

        UUID sessionId = UUID.randomUUID();
        sessionRepository.create(new Session(
                sessionId,
                OLD_DATE,
                user
        ));

        Optional<UserDto> userFromExpiredSession = authService.getUser(sessionId);
        assertThat(userFromExpiredSession)
                .withFailMessage("User from expired session should be empty.")
                .isEmpty();
    }

    private String getUniqueUsername() {
        return "test_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
