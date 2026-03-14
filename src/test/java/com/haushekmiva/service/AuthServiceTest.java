package com.haushekmiva.service;

import com.haushekmiva.BaseIntegrationTest;
import com.haushekmiva.dto.*;
import com.haushekmiva.model.Session;
import com.haushekmiva.model.User;
import com.haushekmiva.repository.SessionRepository;
import com.haushekmiva.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;

@TestConstructor(autowireMode = ALL)
@RequiredArgsConstructor
public class AuthServiceTest extends BaseIntegrationTest {

    private static final String TEST_PASSWORD = "test_password";
    private static final String TEST_WRONG_PASSWORD = "test_invalid_password";
    private static final LocalDateTime OLD_DATE = LocalDateTime.of(1979, 1, 1, 1, 1);

    private final AuthService authService;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;


    @Test
    public void registerUser_correctInput_newUserCreated() {
        String testUsername = getUniqueUsername();

        authService.registerUser(new UserRegisterRequest(testUsername, TEST_PASSWORD, TEST_PASSWORD));
        Optional<User> user = userRepository.getByLogin(testUsername);

        assertThat(user)
                .withFailMessage("Entry with login %s was not created.", testUsername)
                .isPresent();

        assertThat(user.get().getLogin())
                .withFailMessage("Entry should have username equal to %s", testUsername)
                .isEqualTo(testUsername);
    }

    @Test
    public void registerUser_correctInput_newSessionCreated() {
        String testUsername = getUniqueUsername();

        AuthResponse authResponse = authService.registerUser(
                new UserRegisterRequest(testUsername, TEST_PASSWORD, TEST_PASSWORD)
        );

        assertThat(authResponse).isInstanceOf(AuthSuccess.class);

        AuthSuccess authSuccess = (AuthSuccess) authResponse;

        Optional<Session> session = sessionRepository.getById(authSuccess.sessionId());

        assertThat(session)
                .withFailMessage("Session for user with username %s no exists.", testUsername)
                .isPresent();
    }

    @Test
    public void registerUser_nonUniqueLogin_authError() {
        String testUsername = getUniqueUsername();
        authService.registerUser(new UserRegisterRequest(testUsername, TEST_PASSWORD, TEST_PASSWORD));
        AuthResponse errorAuth = authService.registerUser(new UserRegisterRequest(testUsername, TEST_PASSWORD, TEST_PASSWORD));

        assertThat(errorAuth)
                .withFailMessage("Create user with non-unique username should send error.")
                .isInstanceOf(AuthError.class);

    }

    @Test
    public void getUserBySession_sessionExpired_emptyResponse() {
        String testUsername = getUniqueUsername();

        userRepository.create(new User(testUsername, TEST_PASSWORD));
        User user = userRepository.getByLogin(testUsername).get();

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

    @Test
    public void loginUser_validData_authSuccess() {
        String testUsername = getUniqueUsername();
        authService.registerUser(new UserRegisterRequest(testUsername, TEST_PASSWORD, TEST_PASSWORD));

        AuthResponse authResponse = authService.loginUser(new UserLoginRequest(testUsername, TEST_PASSWORD));

        assertThat(authResponse)
                .withFailMessage("Sign-in should be success with valid data.")
                .isInstanceOf(AuthSuccess.class);
    }

    @Test
    public void loginUser_invalidData_authError() {
        String testUsername = getUniqueUsername();
        authService.registerUser(new UserRegisterRequest(testUsername, TEST_PASSWORD, TEST_PASSWORD));

        AuthResponse authResponse = authService.loginUser(new UserLoginRequest(testUsername, TEST_WRONG_PASSWORD));

        assertThat(authResponse)
                .withFailMessage("Sign-in should be unsuccess with invalid data.")
                .isInstanceOf(AuthError.class);
    }

    @Test
    public void logoutUser_validSession_sessionRemoved() {
        String testUsername = getUniqueUsername();
        AuthResponse authResponse = authService.registerUser(new UserRegisterRequest(testUsername, TEST_PASSWORD, TEST_PASSWORD));
        AuthSuccess authSuccess = (AuthSuccess) authResponse;

        authService.logoutUser(authSuccess.sessionId());

        Optional<Session> session = sessionRepository.getById(authSuccess.sessionId());

        assertThat(session)
                .withFailMessage("User session should be removed after logout")
                .isEmpty();
    }

    private String getUniqueUsername() {
        return "test_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
