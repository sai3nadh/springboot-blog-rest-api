package com.springboot.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    SecurityConfig(UserDetailsService userDetailsService){
        this.userDetailsService=userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{

        return configuration.getAuthenticationManager();
    }

    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
      /*  http.cscsrf().disable().authorizeHttpRequests(
                authorizationManagerRequestMatcherRegistry -> {
                    try {
                        authorizationManagerRequestMatcherRegistry.anyRequest().authenticated().and().httpBasic(Customizer.withDefaults());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );*/

        return http.build();
    }

    /*@Bean
    public UserDetailsService userDetailsService(){
        UserDetails userDetails = User.builder().username("trinadh").password(passwordEncoder().encode("trinadh"))
                .roles("ADMIN").build();
        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin"))
                .roles("ADMIN").build();
        return new InMemoryUserDetailsManager(userDetails,admin);
    }*/
}
