package com.roomy.controllers;

import com.roomy.models.UserEntity;
import com.roomy.repositories.UserRepository;
import com.roomy.services.RoleService;
import com.roomy.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<UserEntity> users = userService.searchUsers(keyword);
        model.addAttribute("users", users);
        return "user/users";
    }

    @GetMapping("/update/{id}")
    public String getUserToUpdate(@PathVariable("id") Integer id, Model model) {
        UserEntity user = userRepository.getReferenceById(id);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public String updateUser(@PathVariable Integer id, @ModelAttribute UserEntity user) {
        UserEntity userToUpdate = userRepository.getReferenceById(id);
        if (!userToUpdate.getFirstName().equals(user.getFirstName())) {
            userToUpdate.setFirstName(user.getFirstName());
        }
        if (!userToUpdate.getLastName().equals(user.getLastName())) {
            userToUpdate.setLastName(user.getLastName());
        }
        if (!userToUpdate.getUserName().equals(user.getUserName())) {
            userToUpdate.setUserName(user.getUserName());
        }
        if (!userToUpdate.getEmail().equals(user.getEmail())) {
            userToUpdate.setEmail(user.getEmail());
        }

        userService.saveUser(userToUpdate);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        UserEntity user = userRepository.getReferenceById(id);
        userRepository.delete(user);
        return "redirect:/users";
    }
}
