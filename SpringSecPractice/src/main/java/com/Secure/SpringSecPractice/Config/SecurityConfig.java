package com.Secure.SpringSecPractice.Config;


import com.Secure.SpringSecPractice.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MyUserDetailsService userDetailService;

    public SecurityConfig(MyUserDetailsService userDetailService){
        this.userDetailService = userDetailService;
    }

    @Autowired
    private JwtFilter jwtfilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        this is the another way to configure the security filer chain
//        http
//                .csrf(customizer -> customizer.disable())
//                .authorizeHttpRequests(request->
//                        request
//                                .requestMatchers("/register","/login")  // allowing these two endpoints to be accessed without authentication
//                                .permitAll() // allowing all other endpoints to be accessed only after authentication
//                                .anyRequest()
//                                .authenticated()
//                )
////                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();


        return http.csrf(customizer -> customizer.disable()).
                authorizeHttpRequests(request -> request
                        .requestMatchers("/login", "/register").permitAll()
                        .anyRequest().authenticated()).
                httpBasic(Customizer.withDefaults()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

//    @Bean
//    public UserDetailsService userDetails(){
//      this is used to create the default users in the memory for testing purpose and we can also use the database to store the users and fetch them from there
//        UserDetails user1 =  User
//                .withDefaultPasswordEncoder()
//                .password("Abhi123")
//                .username("Abhi")
//                .roles("USER")
//                .build();
//
//        UserDetails user2 =  User
//                .withDefaultPasswordEncoder()
//                .password("Ashish123")
//                .username("Ashish")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1,user2);
//    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        // this is interface which is used to create the password in BCrypt format and also to fetch the user details from the database using the user details service
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//        provider.setUserDetailsService(userDetailService);
        return provider;
    }


    // creating below bean to create the web token
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
