package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String LOGIN_ERROR_MESSAGE = "LOGIN_ERROR_MESSAGE";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/images/**",
                                "/styles.css"
                        ).permitAll()

                        .requestMatchers(
                                "/design",
                                "/orders/**"
                        ).hasRole("USER")

                        .anyRequest().permitAll()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/design", true)
                        .failureHandler((request, response, exception) -> {
                            request.getSession().setAttribute(
                                    LOGIN_ERROR_MESSAGE,
                                    "로그인에 실패했습니다."
                            );
                            response.sendRedirect("/login");
                        })
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                )

                .build();
    }
}