package com.senacor.migrationdemo.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@ComponentScan
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  public DataSource dataSource;

  @Override
  protected void configure(HttpSecurity security) throws Exception {
    security.httpBasic().disable();
    security.headers().frameOptions().sameOrigin();

    security
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/user").permitAll()
        .antMatchers( "/h2-console/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin();

    security.csrf().disable();

    security.headers().frameOptions().disable();
  }

  @Bean
  @Override
  public UserDetailsManager userDetailsService() {
    return new JdbcUserDetailsManager() {{
      setDataSource(dataSource);
    }};
  }

  @Bean
  public PasswordEncoder delegatingPasswordEncoder() {
    PasswordEncoder defaultEncoder = NoOpPasswordEncoder.getInstance();
    Map<String, PasswordEncoder> encoders = new HashMap<>();
    encoders.put("bcrypt", new BCryptPasswordEncoder());
    encoders.put("scrypt", new SCryptPasswordEncoder());
    encoders.put("noop", NoOpPasswordEncoder.getInstance());

    DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder(
        "noop", encoders);
    delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder);

    return delegatingPasswordEncoder;
  }

}
