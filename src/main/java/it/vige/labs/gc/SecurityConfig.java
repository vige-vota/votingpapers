/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.vige.labs.gc;

import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/**
 * Application security configuration.
 *
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

	public final static String ADMIN_ROLE = "admin";

	public final static String CITIZEN_ROLE = "citizen";

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
		SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
		grantedAuthorityMapper.setPrefix("ROLE_");
		grantedAuthorityMapper.setConvertToUpperCase(true);
		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
		auth.authenticationProvider(keycloakAuthenticationProvider);
	}

	@Autowired
	public KeycloakClientRequestFactory keycloakClientRequestFactory;

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public KeycloakRestTemplate keycloakRestTemplate() {
		return new KeycloakRestTemplate(keycloakClientRequestFactory);
	}

	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.authorizeRequests().antMatchers("*").hasAnyRole(ADMIN_ROLE, CITIZEN_ROLE).anyRequest().permitAll().and()
				.csrf().disable();

	}

}
