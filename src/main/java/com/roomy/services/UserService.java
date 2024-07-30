package com.roomy.services;

import com.roomy.models.UserEntity;
import com.roomy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByUserName(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("ユーザが見つかりませんでした。");
        }
        return User.withUsername(userEntity.getUserName())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole())
                .build();
    }

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    public UserEntity saveUser(UserEntity userEntity) { return userRepo.save(userEntity); }

    public List<UserEntity> searchUsers(String keyword) {
        return userRepo.findAll((Specification<UserEntity>) (root, query, builder) ->
                builder.or(
                        builder.like(root.get("firstName"), "%" + keyword + "%"),
                        builder.like(root.get("lastName"), "%" + keyword + "%"),
                        builder.like(root.get("userName"), "%" + keyword + "%"),
                        builder.like(root.get("email"), "%" + keyword + "%")
                )
        );
    }

}
