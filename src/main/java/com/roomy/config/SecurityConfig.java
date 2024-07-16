package com.roomy.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(antMatcher("/h2-console/**"))
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers(antMatcher("/h2-console/**")).permitAll())
                .csrf(csrf ->
                        csrf.ignoringRequestMatchers(antMatcher("/h2-console/**")))
                .headers(headers ->
                        headers.frameOptions(frame -> frame.sameOrigin())
                )
                .securityMatcher("/**")
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers(
                                PathRequest.toStaticResources().atCommonLocations(),
                                antMatcher("/hello"),
                                antMatcher("/world"),
                                antMatcher("/now"),
                                antMatcher("/users"),
                                antMatcher("/home"),
                                antMatcher("/")
                        ).permitAll().anyRequest().authenticated()
                ).formLogin(form ->
                        form.loginPage("/login").permitAll()
                ).logout(logout ->
                        logout.permitAll()
                ).csrf(csrf ->
                        csrf.ignoringRequestMatchers(
                                antMatcher("/hello")
                        ).csrfTokenRepository(new CookieCsrfTokenRepository())
                );
        return http.build();
    }
}
