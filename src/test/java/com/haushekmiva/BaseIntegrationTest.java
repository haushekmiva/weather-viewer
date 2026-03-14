package com.haushekmiva;

import com.haushekmiva.config.AppConfig;
import com.haushekmiva.config.TestDataBaseConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        AppConfig.class,          // без DataBaseConfig
        TestDataBaseConfig.class  // вместо него
})
@ActiveProfiles("test")
public class BaseIntegrationTest {
}
