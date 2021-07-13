package com.example.autorizeservice.config;

import com.example.autorizeservice.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String signingKey;
    private final String urlClient;
    private final String urlEmployee;

    @Autowired
    public WebSecurityConfiguration(@Value("${security.jwt.signing-key}") String signingKey,
                                    @Value("${url.auth.client}") String urlClient,
                                    @Value("${url.auth.employee}") String urlEmployee)  {
        this.signingKey = signingKey;
        this.urlClient = urlClient;
        this.urlEmployee = urlEmployee;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("username").password("password").roles(UserRole.CLIENT.name(), UserRole.EMPLOYEE.name());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, ex) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtClientFilter(authenticationManager(), signingKey, urlClient), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtEmployeeFilter(authenticationManager(), signingKey, urlEmployee), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/v1/login/client").permitAll()
                .antMatchers("/v1/login/employee").permitAll()
                .antMatchers("/v1/jwt/parse").permitAll()
                .antMatchers("/v1/jwt/is-valid").permitAll()
                .anyRequest().authenticated();
    }
}
