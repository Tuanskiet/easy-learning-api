package com.poly.EasyLearning.config.security;

import com.poly.EasyLearning.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Autowired
    private OAuth2UserService oauthUserService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    private static String[] publicUrls = {"", ""};

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-type", "enctype", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .cors(cors -> cors.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth ->
                        auth
                                .requestMatchers(
                        "/api/v1/login",
                        "/api/v1/sign-up",
                        "/oauth2/**",
                        "/api/v1/check-account-exists",
                        "/api/v1/lesson/all",
                        "/api/v1/authenticate",
                        "/api/v1/quiz/get/**",
                        "/api/v1/pay",
                        "/api/v1/payment-callback",
                        "/api/v1/payment-callback/**"
                                ).permitAll()
                                .requestMatchers("/api/v1/admin/**").hasAuthority("admin")
                                .anyRequest().authenticated())
//                .oauth2Login(oauth -> oauth
////                        .defaultSuccessUrl("/result")
//                        .userInfoEndpoint(endpoint -> endpoint.userService(oauthUserService))
//                        .successHandler(authenticationSuccessHandler)
//                )
//                .formLogin(form -> form
////                                .loginPage("/login")
//                                .loginProcessingUrl("/api/v1/login")
//                                .successHandler(authenticationSuccessHandler)
//                                .failureHandler(authenticationFailureHandler)
//                )
//                .logout(log -> log.logoutUrl("/api/v1/logout").permitAll())
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .httpBasic(Customizer.withDefaults())
        ;
        return http.build();
    }
}
