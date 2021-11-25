package com.senacor.migrationdemo.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsConfiguration implements UserDetailsPasswordService {

  UserDetailsManager userDetailsManager;

  @Override
  public UserDetails updatePassword(UserDetails user, String newPassword) {
    userDetailsManager.changePassword(user.getPassword(), newPassword);
    return user;
  }
}
