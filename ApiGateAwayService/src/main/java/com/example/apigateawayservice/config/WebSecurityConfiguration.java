package com.example.apigateawayservice.config;

import com.example.apigateawayservice.enums.RouteRules;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AuthenticationFilter authenticationFilter;

    public WebSecurityConfiguration(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        //TODO Написать комментарии рядом с каждым методом или группой методов.
        // Ничего не понятно.
        http.cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                )
                .and()
                .addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                //TODO Вынести это все дело в отдельный ENUM (Сделать метод getPaths() который возвращает результат antMatchers().hasRole())
//                .antMatchers("/auth/**", "/client/api/account/register").permitAll()
//                .antMatchers("/employee/api/employee/update").hasRole(UserType.EMPLOYEE.name())
//                .antMatchers("/client/api/clients/**").hasAnyRole(UserType.CLIENT.name(), UserType.ADMIN.name(), UserType.SUPER_ADMIN.name())
//                .antMatchers("/client/**", "/employee/**").hasAnyRole(UserType.ADMIN.name(), UserType.SUPER_ADMIN.name())
                .antMatchers(RouteRules.FOR_ALL.getPaths()).permitAll()
                .antMatchers(RouteRules.FOR_CLIENT.getPaths()).hasRole(RouteRules.FOR_CLIENT.getRole().name())
                .antMatchers(RouteRules.FOR_SUPER_ADMIN.getPaths()).hasRole(RouteRules.FOR_SUPER_ADMIN.getRole().name())
                .antMatchers(RouteRules.FOR_ADMIN.getPaths()).hasRole(RouteRules.FOR_ADMIN.getRole().name())
                .anyRequest().authenticated();
               // .anyRequest().permitAll();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
