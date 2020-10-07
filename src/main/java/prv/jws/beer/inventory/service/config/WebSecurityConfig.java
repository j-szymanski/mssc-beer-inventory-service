package prv.jws.beer.inventory.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Jerzy Szymanski on 06.10.2020 at 11:07
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .httpBasic();
    }
//    @Bean
//    public HttpTraceRepository httpTraceRepository(){
//        return new InMemoryHttpTraceRepository();
//    }
}
