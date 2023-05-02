package com.javagrind.oauth2practice.config;

import com.javagrind.oauth2practice.security.jwt.AuthEntryPointJwt;
import com.javagrind.oauth2practice.security.jwt.JwtAuthFilter;
import com.javagrind.oauth2practice.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity()

/*
    Defined Spring Security Mechanism for Web Security Configuration (JWT)
        1. User want to access certain controller , but SecurityContextHolder needs Token to verified
            --SecurityContextHolder holds a bunch of Security Configuration, example : WebSecurityConfig--
            --For WebSecurityConfig, we can define order or sequence of filter that must be checked--
            --Each filter must be asking Detailed User Information to AuthenticationManager to create token--
        2. JwtAuthFilter ask AuthenticationManager for UserDetails
        3. JwtAuthFilter ask his provider (JwtAuthProvider) for UserDetails
            --There are many of Authentication type, so certain Provider handle their own type--
        4. Provider ask UserDetailsService to search if the requested user is existed, if yes it will build the UserDetails
        5. When building the UserDetails, PasswordEncoder helps to checking the Password
        6. Next, they return UserDetails to their own PIC to top until reach JwtAuthFilter
        7. JwtAuthFilter receives UserDetails and create the valid token
        8. Token handed to SecurityContextHolder
        9. Filter chain (ApplicationFilterChain) do internalFilter and handle the request method as their
            correspond privilege based on principal in the Token
*/

public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public JwtAuthFilter authenticationJwtTokenFilter() {
        return new JwtAuthFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/test/**").permitAll()
                .anyRequest().authenticated();

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}