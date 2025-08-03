package io.lowcode.platform.security.jwt;

import io.lowcode.platform.config.ApplicationProperties;
import io.lowcode.platform.config.SecurityConfiguration;
import io.lowcode.platform.config.SecurityJwtConfiguration;
import io.lowcode.platform.config.WebConfigurer;
import io.lowcode.platform.management.SecurityMetersService;
import io.lowcode.platform.web.rest.AuthenticateController;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    properties = {
        "spring.security.authentication.jwt.base64-secret=fd54a45s65fds737b9aafcb3412e07ed99b267f33413274720ddbb7f6c5e64e9f14075f2d7ed041592f0b7657baf8",
        "spring.security.authentication.jwt.token-validity-in-seconds=60000",
    },
    classes = {
        ApplicationProperties.class,
        WebConfigurer.class,
        SecurityConfiguration.class,
        SecurityJwtConfiguration.class,
        SecurityMetersService.class,
        AuthenticateController.class,
        JwtAuthenticationTestUtils.class,
    }
)
public @interface AuthenticationIntegrationTest {
}
