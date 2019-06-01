package com.boot.kaizen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsUtils;

import com.boot.kaizen.filter.TokenFilter;

/**
 * spring security 配置
 * 
 * @author weichengz
 * @date 2018年9月2日 上午2:01:45
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private TokenFilter tokenFilter;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 处理跨域请求中的Preflight请求
		http.csrf().disable().authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
		/** 基于token 屏蔽session */
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		/** 静态资源不拦截 */
		http.authorizeRequests().antMatchers("/", "/app/**", 
				"/*.html", //
				"/css/**", //
				"/js/**", //
				"/fonts/**", //
				"/layui/**", //
				"/img/**", //
				"/pages/**", //
				"/druid/**", //
				"/favicon.ico", //
				"/statics/**",

				// swagger配置
				"/swagger-resources/**", "/swagger-ui.html", "/images/**", "/webjars/**", "/v2/api-docs",
				"/configuration/ui", "/configuration/security").permitAll().anyRequest().authenticated();

		http.formLogin()//
				.loginProcessingUrl("/login")//
				.successHandler(authenticationSuccessHandler)//
				.failureHandler(authenticationFailureHandler).and().exceptionHandling()//
				.authenticationEntryPoint(authenticationEntryPoint);//

		http.logout()//
				.logoutUrl("/logout")//
				.logoutSuccessHandler(logoutSuccessHandler);//

		/** 解决不允许显示在iframe的问题 */
		http.headers().frameOptions().disable();
		http.headers().cacheControl();

		http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

}
