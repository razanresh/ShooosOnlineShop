package com.example.shooosonlineshop.service.impl;

import com.example.shooosonlineshop.config.SecurityConfig;
import com.example.shooosonlineshop.model.User;
import com.example.shooosonlineshop.model.dto.UserDTO;
import com.example.shooosonlineshop.model.enums.Role;
import com.example.shooosonlineshop.repository.UserRepository;
import com.example.shooosonlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    @Transactional
    public Boolean save(UserDTO userDTO) {
        if (!Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())) {
            throw new RuntimeException("Password is not equals");
        }
        User user = User.builder()
                .name(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .role(Role.CLIENT)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public User findByName(String name) {
        return userRepository.findFirstByName(name);
    }

    @Override
    @Transactional
    public void updateProfile(UserDTO userDTO) {
        User savedUser = userRepository.findFirstByName(userDTO.getUsername());
        if(savedUser == null){
            throw new RuntimeException("User not found by name " + userDTO.getUsername());
        }

        boolean changed = false;
        if(userDTO.getPassword() != null && ! userDTO.getPassword().isEmpty()){
            savedUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            changed = true;
        }
        if(!Objects.equals(userDTO.getEmail(), savedUser.getEmail())){
            savedUser.setEmail(userDTO.getEmail());
            changed = true;
        }
        if(changed){
            userRepository.save(savedUser);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with name : " + username);
        }
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority(user.getRole().name()));

            return new org.springframework.security.core.userdetails.User(
                    user.getName(),
                    user.getPassword(),
                    roles
            );
    }
    private UserDTO toDto(User user) {
        return UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
    }
}
