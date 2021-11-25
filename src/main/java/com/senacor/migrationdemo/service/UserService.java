package com.senacor.migrationdemo.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  UserDetailsManager userDetailsManager;
  PasswordEncoder passwordEncoder;
  List<GrantedAuthority> authorities = new ArrayList<>();

  @Autowired
  public UserService(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder){
    this.userDetailsManager = userDetailsManager;
    this.passwordEncoder = passwordEncoder;
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
  }

  public UserDetails createUser(String username){

    UserDetails user = new User(username, passwordEncoder.encode("s3cr3t"), authorities);
    userDetailsManager.createUser(user);

    return user;
  }

  public UserDetails getUser(String userName){
    return userDetailsManager.loadUserByUsername(userName);
  }

}
