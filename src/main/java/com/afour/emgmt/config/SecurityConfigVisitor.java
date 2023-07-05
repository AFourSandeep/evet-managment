/**
 * 
 */
package com.afour.emgmt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.afour.emgmt.exception.CustomAccessDeniedHandler;
import com.afour.emgmt.service.UserDetailsServiceVisitor;

/**
 * 
 */
//@Order(1)
//@Configuration
public class SecurityConfigVisitor {

	@Autowired
	private JwtAuthFilter authFilter;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceVisitor();
	};

	@Autowired
	CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(userDetailsService());
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	private static final String[] AUTH_WHITELIST = { "/auth/**", "/v2/api-docs", "/swagger-resources",
			"/swagger-resources/**", "/webjars/**", "/v3/api-docs/**", "/swagger-ui/**" };

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf((csrf) -> csrf.disable())
				.authorizeHttpRequests((authorize) -> authorize.antMatchers(AUTH_WHITELIST).permitAll()
						.antMatchers("/visitor/**").hasAuthority("VISITOR")
						.antMatchers("/event/create**", "/event/update**", "/event/delete**").hasAuthority("ORGANIZER")
						.antMatchers("/session/create**", "/session/update**", "/session/delete**")
						.hasAuthority("ORGANIZER")
						.anyRequest().authenticated())
				.authenticationProvider(authenticationProvider())
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling((exceptions) -> exceptions.authenticationEntryPoint(customAuthenticationEntryPoint)
						.accessDeniedHandler(accessDeniedHandler()));
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/swagger-ui/**", "/v2/api-docs");
	}

}
