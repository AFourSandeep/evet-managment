/**
 * 
 */
package com.afour.emgmt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.afour.emgmt.service.UserDetailsServiceImpl;
import com.afour.emgmt.util.CustomAccessDeniedHandler;
import com.afour.emgmt.util.CustomAuthenticationEntryPoint;

/**
 * 
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig {

	@Autowired
	private JwtAuthFilter authFilter;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
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

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf((csrf) -> csrf.disable())
				.authorizeHttpRequests((authorize) -> authorize.antMatchers("/auth/**").permitAll()
						.antMatchers("/swagger-ui/**", "/v2/api-docs**").permitAll()
						.antMatchers("/organizer/**").hasAuthority("ORGANIZER")
						.antMatchers("/visitor/**").hasAuthority("VISITOR")
						.antMatchers("/event/create**","/event/update**","/event/deleted**").hasAuthority("ORGANIZER")
						.antMatchers("/session/create**","/session/update**","/session/deleted**").hasAuthority("ORGANIZER")
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
