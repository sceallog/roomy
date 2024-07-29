package com.roomy.controllers;

import com.roomy.models.UserEntity;
import com.roomy.repositories.UserRepository;
import com.roomy.services.RoleService;
import com.roomy.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
@EnableMethodSecurity
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserController(UserService userService, UserRepository userRepository, RoleService roleService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserEntity> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/users";
    }

    @GetMapping("new")
    public String createNewUser(Model model) {
        model.addAttribute("user", new UserEntity());
        model.addAttribute("roles", roleService.getAllRoles());
        return "user/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String getEditUserForm(Model model, @PathVariable Integer id) {
        UserEntity user = userRepository.getReferenceById(id);
        model.addAttribute("user", user);
        return "user/edit";
    }
}
