package com.isd.Homebanking.configurations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(ant-> ant
                        .requestMatchers("/", "/index.html" , "/web/assets/images/**" , "/web/assets/pages/login.html" ,
                                "/web/assets/js/index.js" , "/web/assets/js/login.js" , "/web/assets/style/style.css").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/clients" , "/api/login").permitAll()
                        .requestMatchers("/administrator/**" , "/h2-console/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/rest/**" , "/api/admin/loans").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/clients").hasAuthority("ADMIN")
                        .requestMatchers("/web/**").hasAuthority("CLIENT")
                        .requestMatchers(HttpMethod.POST, "/api/clients/current/accounts" , "/api/clients/current/cards", "/api/clients/current/export-pdf" , "/api/clients/current/transfers" , "/api/loans" , "/api/loans/payments").hasAuthority("CLIENT")
                        .requestMatchers(HttpMethod.GET, "/api/clients/current" , "/api/accounts/{id}" , "/api/clients/current/accounts" , "/api/loans").hasAuthority("CLIENT")
                        .requestMatchers(HttpMethod.PATCH, "/api/clients/current/cards" , "/api/clients/current/accounts").hasAuthority("CLIENT")
                        .anyRequest().denyAll())
                        .csrf(csrf -> csrf.disable())
                .headers(header -> header.frameOptions((options -> options.disable())))
                .formLogin(formLogin -> formLogin.loginPage("/login.html")
                        .loginProcessingUrl("/api/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> clearAuthenticationAttributes(request))
                        .failureHandler((request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/api/logout")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint((request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                .rememberMe(Customizer.withDefaults());
        return http.build();
    }
    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }
}
