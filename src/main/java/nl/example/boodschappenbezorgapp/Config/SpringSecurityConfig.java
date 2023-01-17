package nl.example.boodschappenbezorgapp.Config;

import nl.example.boodschappenbezorgapp.Service.CustomUserDetailsService;
import nl.example.boodschappenbezorgapp.filter.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // PasswordEncoderBean. Deze kun je overal in je applicatie injecteren waar nodig.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }



    // Authorizatie met jwt
    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
                // Wanneer je deze uncomments, staat je hele security open. Je hebt dan alleen nog een jwt nodig.
//                .antMatchers("/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/users").permitAll()
//                .antMatchers(HttpMethod.GET,"/users").hasAnyRole("ADMIN", "USER")
//                .antMatchers(HttpMethod.POST,"/users/**").hasAnyRole("ADMIN", "USER")
//                .antMatchers(HttpMethod.PUT,"/users/**").hasAnyRole("ADMIN", "USER")
//                .antMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
//
//                .antMatchers(HttpMethod.POST, "/accounts").hasAnyRole("ADMIN", "USER")
//                .antMatchers(HttpMethod.GET,"/accounts").hasAnyRole("ADMIN", "USER")
//                .antMatchers(HttpMethod.POST,"/accounts/**").hasAnyRole("ADMIN", "USER")
//                .antMatchers(HttpMethod.DELETE, "/accounts/**").hasRole("ADMIN")
//
//                .antMatchers(HttpMethod.POST, "/bills").permitAll()
//                .antMatchers(HttpMethod.GET,"/bills").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST,"/bills/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/bills/**").hasRole("ADMIN")
//
//                .antMatchers(HttpMethod.POST, "/grocerylists").permitAll()
//                .antMatchers(HttpMethod.GET,"/grocerylists").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST,"/grocerylists/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/grocerylists/**").hasRole("ADMIN")
//
//                .antMatchers(HttpMethod.POST, "/deliveryrequest").permitAll()
//                .antMatchers(HttpMethod.GET,"/deliveryrequest").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST,"/deliveryrequest/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/deliveryrequest/**").hasRole("ADMIN")
//
//                .antMatchers(HttpMethod.POST, "/upload").hasAnyRole("USER","ADMIN")
//                .antMatchers(HttpMethod.GET,"/files").hasAnyRole("USER","ADMIN")
//                .antMatchers(HttpMethod.POST,"/files/**").hasAnyRole("USER","ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/audiofiles/**").hasRole("ADMIN")

                .antMatchers("/authenticated").authenticated()
                .antMatchers("/authenticate").permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}