package peaksoft.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import peaksoft.model.User;
import peaksoft.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class WebAppSecurity {

    private final UserRepository userRepository;

    public WebAppSecurity(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
   public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(email -> {
            User user = userRepository.findByEmail(email).
                    orElseThrow(() -> new UsernameNotFoundException(
                            email + " not found!"));
            return new AuthInfo(user);
        });
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize->authorize
                .antMatchers("/companies/allCompanies").hasAnyAuthority("ADMIN","INSTRUCTOR","STUDENT")
                .antMatchers("/companies/new").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/companies/saveCompany").hasAuthority("ADMIN")
                .antMatchers("/companies/editCompany/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers(HttpMethod.GET,"/companies/updateCompany/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers(HttpMethod.GET,"/companies/deleteCompany/**/").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .anyRequest().authenticated())
                .formLogin().
                defaultSuccessUrl("/companies/allCompanies")
                .permitAll();
        return http.build();

    }
}














