package it.vige.labs.gc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Value("${keycloak.auth-server-url}")
	private String authServerUrl;

	@Value("${keycloak.realm}")
	private String realm;

	private static final String OAUTH_SCHEME_NAME = "Voting Papers Auth";

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().components(new Components().addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme()))
				.addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME))
				.info(new Info().title("Voting Papers").description("Web console").version("1.0"));
	}

	private SecurityScheme createOAuthScheme() {
		OAuthFlows flows = createOAuthFlows();
		return new SecurityScheme().type(SecurityScheme.Type.OAUTH2).flows(flows);
	}

	private OAuthFlows createOAuthFlows() {
		OAuthFlow flow = createAuthorizationCodeFlow();
		return new OAuthFlows().implicit(flow);
	}

	private OAuthFlow createAuthorizationCodeFlow() {
		return new OAuthFlow().authorizationUrl(authServerUrl + "/realms/" + realm + "/protocol/openid-connect/auth");
	}
}
