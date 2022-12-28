//package nl.example.boodschappenbezorgapp.Config;
//
//
//import nl.example.boodschappenbezorgapp.filter.JwtRequestFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//
//public class SpringSecurityConfig {
//
//
//
//public CustomUserDetailsService customUserDetailsService;
//
//private JwtRequestFilter jwtRequestFilter;
//
//    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
//        this.customUserDetailsService = customUserDetailsService;
//        this.jwtRequestFilter = jwtRequestFilter;
//    }
//
//    public SpringSecurityConfig() {
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(customUserDetailsService)
//                .passwordEncoder(passwordEncoder())
//                .and()
//                .build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {
//
//        //JWT token authentication
//        http
//                .csrf().disable()
//                .httpBasic().disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/users").permitAll()
//                .antMatchers(HttpMethod.GET,"/users").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.POST,"/users/**").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ADMIN")
//
//                .antMatchers(HttpMethod.POST, "/televisions").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.POST, "/remotecontroller").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.POST, "/wallbracket").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.POST, "/cimodule").hasAuthority("ADMIN")
//
//                .antMatchers(HttpMethod.GET, "/televisions").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.GET, "/remotecontroller").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.GET, "/wallbracket").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.GET, "/cimodule").hasAuthority("ADMIN")
//
//                .antMatchers(HttpMethod.DELETE, "/televisions/**").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/remotecontroller/**").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/wallbracket/**").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/cimodule/**").hasAuthority("ADMIN")
//
//
//                .antMatchers("/authenticated").authenticated()
//                .antMatchers("/authenticate").permitAll()/*allen dit punt mag toegankelijk zijn voor niet ingelogde gebruikers*/
//                .anyRequest().permitAll()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//
//}