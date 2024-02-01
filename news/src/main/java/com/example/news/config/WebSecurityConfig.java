package com.example.news.config;

import com.example.news.jwt.JwtFilter;
import com.example.news.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
//@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public GenericFilterBean genericFilterBean() {
//        return new JwtFilter(jwtTokenProvider);
//    }
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/registration").permitAll()
//                .antMatchers("/").permitAll()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/posts").permitAll()
//                .antMatchers("/posts/add").hasRole("ADMIN")
//                .antMatchers("/posts/{id}/patch").hasRole("ADMIN")
//                .antMatchers("/posts/{id}/delete").hasRole("ADMIN")
//                .antMatchers("/users").hasRole("ADMIN")
//                .anyRequest().authenticated();
////                .and()
////                .apply(new JwtConfigurer(jwtTokenProvider));
//
//        httpSecurity.addFilterBefore(genericFilterBean(), UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOriginPattern("*");
//        config.setAllowedMethods(List.of("POST", "GET", "DELETE", "PUT"));
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedHeader("Access-Control-Expose-Headers");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

}
