package com.example.finalproject.config;

import com.example.finalproject.jwt.JwtTokenFilter;
import com.example.finalproject.repository.BuyerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private BuyerRepo buyerRepo;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> buyerRepo.findBuyerByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Buyer " + username + " not found.")));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/api/v1/fresh-products/buyer/new").permitAll()
                .antMatchers("/api/v1/fresh-products/buyer/login").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/fresh-products*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/fresh-products/list/{category}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/fresh-products/due-date").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/fresh-products/due-date/list/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/fresh-products/list/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/fresh-products/inboundorder").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/fresh-products/orders/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/fresh-products/inboundorder").permitAll()

                .antMatchers(HttpMethod.POST,"/api/v1/fresh-products/orders").hasAuthority("Buyer")
                .antMatchers(HttpMethod.PUT,"/api/v1/fresh-products/orders/*").hasAuthority("Buyer")
                .antMatchers("/api/v1/fresh-products/orders").hasAnyAuthority("Buyer", "ADMIN")

                .anyRequest().authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                );

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}