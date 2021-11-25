package com.senacor.migrationdemo.rest;

import com.senacor.migrationdemo.service.UserService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

  final UserService userService;
  final AtomicLong id = new AtomicLong(0);

  @GetMapping(path = "/user")
  public ResponseEntity<UserDetails> getUser(@RequestParam String username) {
    return ResponseEntity.ok(userService.getUser(username));
  }

  @PostMapping(path = "/user")
  public ResponseEntity<UserDetails> createNewUser() {
    return ResponseEntity.ok(userService.createUser(String.valueOf(id.getAndIncrement())));
  }

  @GetMapping("/")
  public String helloWorld() {
    return "This is the welcome page";
  }
}
