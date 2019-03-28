package API.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;

/**
 * Spring security configuration.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Dependencies.
     */

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    /**
     * UserDetailsService bean.
     * @return Instance of UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImplementation();
    }

    /**
     * PasswordEncoder bean.
     * @return Instance of PasswordEncoder
     */
//    @Bean
//    public BCryptPasswordEncoder appPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    /**
     * AuthenticationManager bean.
     * @return Instance of AuthenticationManager
     * @throws Exception Exception
     */
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    /**
     * Configures authentication.
     * @param auth AuthenticationManagerBuilder
     * @throws Exception Exception
     */
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
    
    /**
     * Configures routes security.
     * @param http HttpSecurity
     * @throws Exception Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            
            .formLogin()
                .successHandler(new AuthSuccessHandler())
                .failureHandler(new AuthFailureHandler())
            .and()
            .logout()
                .logoutSuccessHandler(new AuthLogoutSuccessHandler())
            .and()
            .csrf()
                .disable();
    
    
    
    }
}
