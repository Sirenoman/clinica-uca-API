package com.sv.clinica.uca.clinica_uca.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
//@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class SecurityConfigurations {
	
	@Autowired
	private SecurityFilter securityFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Http Login and cors disabled
	    http.httpBasic(withDefaults()).csrf(csrf -> csrf.disable());
	    

	    //Route filter
	    http.authorizeHttpRequests(auth -> 
	    	auth
	    		.requestMatchers("auth/**").permitAll()
	    		.requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
	    		.anyRequest().authenticated()
	    );
	    
	    //Statelessness
	    http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	    
	    //UnAunthorized handler
	    http.exceptionHandling(handling -> handling.authenticationEntryPoint((req, res, ex) -> {
	        res.sendError(
	        		HttpServletResponse.SC_UNAUTHORIZED,
	        		"Auth fail!"
	        	);
	    }));
	    
	    //JWT filter
	    http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
	    
		// CORS Configuration
		http.cors(withDefaults());
	    
	    return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
			throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	
	//return httpSecurity.csrf().disable().sessionManagement()
    //	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    //	.and()
    //	.authorizeHttpRequests()
    //	.requestMatchers(HttpMethod.POST, "/login").permitAll()
    //	.requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
    //	.anyRequest().authenticated()
    //	.and()
    //	.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
    //	.build();
}
