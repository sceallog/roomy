package com.roomy.controllers;

import com.roomy.dto.LoginDto;
import com.roomy.dto.RegisterDto;
import com.roomy.models.Role;
import com.roomy.models.UserEntity;
import com.roomy.repositories.RoleRepository;
import com.roomy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepo,
                          RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@ModelAttribute RegisterDto registerDto) {
        if(userRepo.existsByUserName(registerDto.getUserName())) {
            return new ResponseEntity<>("ユーザー名がすでに登録されています。", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUserName(registerDto.getUserName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());


        Optional<Role> rolesOptional = roleRepo.findByName("USER");
        if(!rolesOptional.isPresent()) {
            return new ResponseEntity<>("役割を見つかりませんでした。", HttpStatus.BAD_REQUEST);
        }
        Role roles = rolesOptional.get();
        user.setRoles(Collections.singletonList(roles));

        userRepo.save(user);

        return new ResponseEntity<>("ユーザー登録が完了しました。", HttpStatus.OK);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("ログインしました。", HttpStatus.OK);
    }
}
