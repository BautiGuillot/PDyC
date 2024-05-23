package ar.edu.unnoba.pdyc.mymusic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private  UserDetailsService userDetailsService;  //Se declara un objeto UserDetailsService. Este objeto se utiliza para obtener los datos del usuario.
    private  AuthenticationConfiguration authenticationConfiguration;   //Se declara un objeto AuthenticationConfiguration. Este objeto se utiliza para obtener el AuthenticationManager.

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, AuthenticationConfiguration authenticationConfiguration) {     //Se inyecta el UserDetailsService y el AuthenticationConfiguration en el constructor.
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,"/songs/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/playlists/**").permitAll()
                        .anyRequest().fullyAuthenticated())
                .addFilter(new JWTAuthenticationFilter
                        (authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JWTAuthorizationFilter
                        (authenticationConfiguration.getAuthenticationManager()));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "1234";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println(encodedPassword);
    }



}
