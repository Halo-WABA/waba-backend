package com.halo.eventer.common;

import com.halo.eventer.security.exception.CustomAccessDeniedHandler;
import com.halo.eventer.security.exception.CustomAuthenticationEntryPoint;
import com.halo.eventer.security.filter.JwtAuthenticationFilter;
import com.halo.eventer.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .httpBasic().disable()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()
                .antMatchers("/", "/swagger-ui/**", "/v3/**","/swagger-ui.html").permitAll()
//                .antMatchers(HttpMethod.POST, "/concert","/concertInfo/**","/duration/**", "/festival/**","/mapCategory/**","/map/**", "/menu/**","/notice/**","/widget").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PATCH, "/concert","/concertInfo/**","/duration/**", "/festival/**","/mapCategory/**","/map/**", "/menu/**","/notice/**","/widget").hasRole("ADMIN")                .antMatchers(HttpMethod.DELETE, "/concert/**","/festival/**","/mapCategory/**","/menu/**","/notice/**").hasRole("ADMIN")
 //               .antMatchers(HttpMethod.DELETE, "/concert","/concertInfo/**","/duration/**", "/festival/**","/mapCategory/**","/map/**", "/menu/**","/notice/**","/widget").hasRole("ADMIN")
                .anyRequest().permitAll();

        http
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
