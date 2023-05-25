package com.example.shooosonlineshop.config;

import com.example.shooosonlineshop.model.enums.Role;
import com.example.shooosonlineshop.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Basic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {

    private UserService userService;
    @Autowired
    public void setUserService(@Lazy UserService userService){
        this.userService = userService;
    }

 /*   @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }*/
    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    DaoAuthenticationProvider authProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(PasswordEncoder());
        return authProvider;
    }
    @Bean
    public UserDetailsService userDetailsService( PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.withUsername("admin")
                .password(passwordEncoder.encode("adminPass"))
                .roles("CLIENT", "MANAGER", "ADMIN")
                .build());
        userDetailsManager.createUser(User.withUsername("manager")
                .password(passwordEncoder.encode("manager"))
                .roles("MANAGER")
                .build());
        userDetailsManager.createUser(User.withUsername("client")
                .password(passwordEncoder.encode("clientPass"))
                .roles("CLIENT")
                .build());
        return userDetailsManager;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        //.requestMatchers("/admin/**").hasRole("ADMIN")
                        //.requestMatchers("/manager/**").hasRole("MANAGER")
                        //.requestMatchers("/client/**").hasRole("CLIENT")
                        .requestMatchers("/users/new").hasAuthority(Role.ADMIN.name())
                        .anyRequest()
                        .permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/auth")
                        .failureUrl("/login-error")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher(
                                "/logout"
                        ))
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/"))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}