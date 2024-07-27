package com.roomy.config;

import com.roomy.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity


public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

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
//                                antMatcher("/auth/**")
//                                antMatcher("/world"),
//                                antMatcher("/now"),
//                                antMatcher("/users"),
//                                antMatcher("/home"),
                                antMatcher("/**")
//                                antMatcher("/login"),
//                                antMatcher("/inventory/**")
                        ).permitAll().anyRequest().authenticated()
                ).formLogin(form ->
                        form.loginPage("/auth/login").permitAll()
                ).logout(logout ->
                        logout.permitAll()
                ).csrf(csrf ->
                        csrf.ignoringRequestMatchers(
                                antMatcher("/h2-console/**"),
                                antMatcher("/auth/**")
                        ).csrfTokenRepository(new CookieCsrfTokenRepository())
                );
        return http.build();
    }

//    @Bean
//    public UserDetailsService users() {
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("password")
//                .roles("ADMIN")
//                .build();
//        UserDetails user = User.builder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    //TODO UserDetailsService and PasswordEncoder in the AuthenticationManager
    //TODO WebSecurityConfigurationController

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
