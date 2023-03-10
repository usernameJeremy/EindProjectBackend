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


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



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
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET,"/users").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/users/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT,"/users/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/accounts").permitAll()
                .antMatchers(HttpMethod.GET,"/accounts").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/accounts/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/accounts/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/grocerylists").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/grocerylists").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/grocerylists/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/grocerylists/**").hasAnyRole("ADMIN", "USER")

                .antMatchers(HttpMethod.POST, "/deliveryrequest").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/deliveryrequest").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/deliveryrequest/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/deliveryrequest/**").hasAnyRole("ADMIN", "USER")

                .antMatchers(HttpMethod.POST, "/upload").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET,"/files").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST,"/files/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.DELETE, "/audiofiles/**").hasRole("ADMIN")

                .antMatchers("/authenticated").authenticated()
                .antMatchers("/authenticate").permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}