package com.example.fitnesproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    private final DataSource dataSource;

    @Autowired
    WebSecurityConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Bean
    JdbcUserDetailsManager users(){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login", "/registration", "/static/**").permitAll();
                    auth.requestMatchers("/new", "/save", "/edit/*", "/delete/*").hasAuthority("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .formLogin(l -> l.loginPage("/login")
                            .usernameParameter("login")
                            .passwordParameter("password")
                            .permitAll()
                )
                .logout((logout) ->
                        logout.deleteCookies("remove")
                                .invalidateHttpSession(false)
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}

