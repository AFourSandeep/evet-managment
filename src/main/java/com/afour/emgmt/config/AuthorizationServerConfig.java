/**
 * 
 */
package com.afour.emgmt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private final String CLIEN_ID = "afour";
	private final String CLIENT_SECRET = "$2a$10$iGdYCsr3b9kq1glWqlgnQuOilEQbfd6EOFvCACHjx7G2TahbkOZgG";
	private final String PASSWORD = "password";
	private final String AUTHORIZATION_CODE = "authorization_code";
	private final String REFRESH_TOKEN = "refresh_token";
	private final String IMPLICIT = "implicit";
	private final String SCOPE_READ = "read";
	private final String SCOPE_WRITE = "write";
	private final String TRUST = "trust";
	private final int ACCESS_TOKEN_VALIDITY_SECONDS = 1 * 60 * 60;
	private final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6 * 60 * 60;
	private static final String RESOURCE_ID = "api";

	@Autowired

	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		security.tokenKeyAccess("permitAll()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(CLIEN_ID).secret(CLIENT_SECRET)
				.authorizedGrantTypes(PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
				.scopes(SCOPE_READ, SCOPE_WRITE, TRUST).accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
				.refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS).resourceIds(RESOURCE_ID);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService);
	}
}