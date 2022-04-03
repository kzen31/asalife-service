package com.asaproject.asalife.configurations;

import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.services.UserServiceImpl;
import com.asaproject.asalife.utils.filters.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Lazy
    private final UserServiceImpl userService;
    @Lazy
    private final JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String[] AUTH_LIST = {
            "/admin/**", "/catering/**", "/housekeeping/**", "/laundry/**", "/maintenance", "/mess/**", "/users/my"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and().httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers(AUTH_LIST).authenticated()
//                .and().authorizeRequests()
//                .antMatchers("/worker/**").hasAnyAuthority(ERole.Constants.WORKER)
                    .anyRequest().permitAll()
                .and().exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPointConfig())
                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
