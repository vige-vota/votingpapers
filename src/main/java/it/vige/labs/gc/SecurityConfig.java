/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.vige.labs.gc;

import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Application security configuration.
 *
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
public class SecurityConfig {

	public final static String ADMIN_ROLE = "admin";

	public final static String CITIZEN_ROLE = "citizen";

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter) throws Exception {

        // @formatter:off
		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("*").hasAnyRole(ADMIN_ROLE, CITIZEN_ROLE)
				.anyRequest().permitAll()).csrf(csrf -> csrf.disable());
        // @formatter:on

        return http.build();
    }

}
