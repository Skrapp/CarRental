package com.nilsson.carrental;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //TODO for each user?
        //TODO Safety?
        //TODO User has customerId
        auth.inMemoryAuthentication()
                .withUser("TheKitten")
                .password("pass")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("1234")
                .roles("ADMIN");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/orders/customer-id").hasRole("USER")
                .antMatchers("/bookingform").hasRole("USER")
                .antMatchers("/save-booking").hasRole("USER")
                .antMatchers("/update/booking-id").hasRole("USER")
                .antMatchers("/delete-booking").hasRole("USER")
                .antMatchers("/admin/customers").hasRole("ADMIN")
                .antMatchers("/admin/customers/customer-id").hasRole("ADMIN")
                .antMatchers("/delete-customer").hasRole("ADMIN")
                .antMatchers("/admin/vehicles").hasRole("ADMIN")
                .antMatchers("/admin/add-vehicle").hasRole("ADMIN")
                .antMatchers("/save-vehicle").hasRole("ADMIN")
                .antMatchers("/admin/vehicle/vehicle-id").hasRole("ADMIN")
                .antMatchers("/delete-vehicle").hasRole("ADMIN")
                .antMatchers("/cars").hasRole("USER")
                .and().formLogin()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
    }
}
