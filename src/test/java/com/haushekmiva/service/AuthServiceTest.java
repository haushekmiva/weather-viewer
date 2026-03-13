package com.haushekmiva.service;

import com.haushekmiva.BaseIntegrationTest;
import com.haushekmiva.dto.UserRegisterRequest;
import com.haushekmiva.model.User;
import com.haushekmiva.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;

import java.util.Optional;

@TestConstructor(autowireMode = ALL)
@RequiredArgsConstructor
public class AuthServiceTest extends BaseIntegrationTest {


    private static final String TEST_USERNAME = "test_login";
    private static final String TEST_PASSWORD = "test_password";

    private final AuthService authService;
    private final UserRepository userRepository;


    @Test
    public void registerUser_correctInput_newUserCreated() {
        authService.registerUser(new UserRegisterRequest(TEST_USERNAME, TEST_PASSWORD, TEST_PASSWORD));
        Optional<User> user = userRepository.getByLogin(TEST_USERNAME);

        assertThat(user)
                .withFailMessage("Entry with login %s was not created.", TEST_USERNAME)
                .isPresent();

        assertThat(user.get().getLogin())
                .withFailMessage("Entry should have username equal to %s", TEST_USERNAME)
                .isEqualTo(TEST_USERNAME);
    }
}
