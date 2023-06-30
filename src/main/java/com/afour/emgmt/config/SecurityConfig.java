/**
 * 
 */
package com.afour.emgmt.config;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.afour.emgmt.util.CustomAccessDeniedHandler;
//import com.afour.emgmt.util.CustomAuthenticationEntryPoint;

/**
 * 
 */
@SuppressWarnings("deprecation")
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig /* extends WebSecurityConfigurerAdapter */{

//	@Qualifier("userDetailsService")
//	@Autowired
//	private UserDetailsService userDetailsService;
//
//	@Autowired
//	CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
//
//	@Autowired
//	PasswordEncoder passwordEncoder;
//
////	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(passwordEncoder);
//		provider.setUserDetailsService(userDetailsService);
//		return provider;
//	}
//
//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
//		.antMatchers("/oauth/**").permitAll()
//		.antMatchers("/swagger-ui/**", "/v2/api-docs**").permitAll()
//				.antMatchers("/swagger-ui/**").permitAll().antMatchers("/visitor/**").hasAnyAuthority("VISITOR")
//				.antMatchers("/organizer/**").hasAuthority("ORGANIZER")
//				.antMatchers("/api/**").authenticated().anyRequest()
//				.authenticated().and().exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
//				.accessDeniedHandler(new CustomAccessDeniedHandler());
//		http.csrf().disable();
//	}

}
