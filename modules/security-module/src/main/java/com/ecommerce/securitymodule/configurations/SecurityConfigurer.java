package com.ecommerce.securitymodule.configurations;

import com.ecommerce.securitymodule.filters.AuthRequestFilter;
import com.ecommerce.securitymodule.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	private final AppSecurityConfig appSecurityConfig;
	private final SecurityUserService securityUserService;
	private final AuthRequestFilter authRequestFilter;
	private final BCryptPasswordEncoder encoder;
	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(securityUserService);
		authProvider.setPasswordEncoder(encoder);
		return authProvider;
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		List<String> allowedEndPoints = new ArrayList<>(Arrays.asList(
				"/swagger-ui/**",
				"/swagger-resources/**",
				"/swagger-ui.html",
				"/v2/api-docs",
				"/webjars/**"
		));
		allowedEndPoints.addAll(Arrays.asList(appSecurityConfig.getAllowedEndpoints()));
		http.csrf().disable()
				.authorizeRequests().antMatchers((String[]) allowedEndPoints.toArray(new String[0]))
				.permitAll().anyRequest().authenticated().and().cors()
				.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/res/**");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
