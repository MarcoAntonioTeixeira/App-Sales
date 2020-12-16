package com.app.vendas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.vendas.config.security.jwt.JwtAuthFilter;
import com.app.vendas.config.security.jwt.JwtService;
import com.app.vendas.service.UserServiceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;




@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserServiceImpl  userService;
	@Autowired
	private JwtService  jwtService;
	
	
	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(jwtService, userService);
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { //autenticação
        auth
            .userDetailsService(userService)
            .passwordEncoder(passwordEncoder());

		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { //autorização
		 http
         .csrf().disable()
         .authorizeRequests()
             .antMatchers("/api/clientes/**")
                 .hasAnyRole("USER", "ADMIN")
             .antMatchers("/api/pedidos/**")
                 .hasAnyRole("USER", "ADMIN")
             .antMatchers("/api/produtos/**")
                 .hasRole("ADMIN")
             .antMatchers(HttpMethod.POST, "/api/usuarios/**")
                 .permitAll()
             .anyRequest().authenticated()
         .and()
             .sessionManagement()
             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
             .addFilterBefore( jwtFilter(), UsernamePasswordAuthenticationFilter.class);
     
	}
	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
		
	}
	
}
