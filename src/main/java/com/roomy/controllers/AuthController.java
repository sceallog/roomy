package com.roomy.controllers;

import com.roomy.dto.LoginDto;
import com.roomy.dto.RegisterDto;
import com.roomy.models.UserEntity;
import com.roomy.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/register")
    public String register(Model model) {
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute( registerDto);
        model.addAttribute("success", false);
        return "user/create";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/auth/login?logout";
    }

    @PostMapping("/register")
    public String register(
            Model model,
            @Valid @ModelAttribute RegisterDto registerDto,
            BindingResult result
    ) {
       UserEntity userEntity= userRepo.findByUserName(registerDto.getUserName());
       if (userEntity != null) {
           result.addError(
                   new FieldError("registerDto", "userName", "ユーザ名がすでに登録されています。")
           );
       }

       if (result.hasErrors()) {
           model.addAttribute("registerDto", registerDto);
           return "user/create";
       }

       try {
        BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

        UserEntity newUser = new UserEntity();
        newUser.setFirstName(registerDto.getFirstName());
        newUser.setLastName(registerDto.getLastName());
        newUser.setEmail(registerDto.getEmail());
        newUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));
        newUser.setUserName(registerDto.getUserName());
        newUser.setRole("USER");
        newUser.setCreatedAt(new Date());

        userRepo.save(newUser);

        model.addAttribute("registerDto", new RegisterDto());
        model.addAttribute("success", true);
       } catch(Exception ex) {
           result.addError(
                   new FieldError("registerDto", "firstName", ex.getMessage())
           );
        }
       return "user/create";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUserName(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("ログインしました。", HttpStatus.OK);
    }

}
