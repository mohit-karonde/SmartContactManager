package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.scm.servicesImplement.SecurityCustomUserDetailService;



@Configuration
public class SecurityConfig {

//       @Bean
//       public UserDetailsService userDetailsService(){

//         UserDetails user1 = User.withDefaultPasswordEncoder()
//         .username("admin123")
//         .password("admin123")
//         .roles("ADMIN","USER")
//         .build();




//         UserDetails user2 = User.withDefaultPasswordEncoder()
//         .username("user1")
//         .password("password")
//         .build();

//         var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2 );
//         return inMemoryUserDetailsManager;

//       }



// } 

    @Autowired
    SecurityCustomUserDetailService userDetailsService;

    @Autowired
    OAuthAuthenicationSuccessHandler handler ;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        //user detail Service ka Object pass karenge
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        //passwordencoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); 
        
        return daoAuthenticationProvider;
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.authorizeHttpRequests(authorize ->{
           // authorize.requestMatchers("/home","/register","/do-register").permitAll();
           authorize.requestMatchers("/user/**").authenticated();
           authorize.anyRequest().permitAll();

        });


        httpSecurity.formLogin(formLogin ->{


            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/profile");
            // formLogin.failureForwardUrl("/login?error=true");
            // formLogin.defaultSuccessUrl("/home");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
            

        });


        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });
         
        httpSecurity.oauth2Login(oauth -> {
          
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });

        return httpSecurity.build();
    }
     








    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }



} 
