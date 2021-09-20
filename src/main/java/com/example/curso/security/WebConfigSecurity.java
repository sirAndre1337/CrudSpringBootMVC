package com.example.curso.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.curso.service.ImplUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplUserDetailsService implUserDetailsService;

	// configura as solicitacoes de acesso por HTTP
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable() // Desativa as configuracoes padrao de memoria
		.authorizeRequests() // Permitir restrigir acessos
		.antMatchers(HttpMethod.GET , "/").permitAll() // Qualquer usuario acessa a pagina inicial
		.antMatchers(HttpMethod.GET , "/cadastropessoa").hasAnyRole("ADMIN") // deixando somente quem tem Role de admin acessar essa url.
		.anyRequest().authenticated().and().formLogin().permitAll() // permite qualquer usuario
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")); // mapeia URL de logout e invalida o usuario autenticado
	}
	
	// Cria autenticacao do usuario com banco de dados ou em memoria
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(implUserDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
		
		
//		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
//		.withUser("Andre")
//		.password("admin")
//		.roles("ADMIN");
	}
	
	// Ignora URL especificas
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/materialize**"); // deixa acessar tudo que estiver na pasta materialize para qualquer usuario.
	}
	
}
