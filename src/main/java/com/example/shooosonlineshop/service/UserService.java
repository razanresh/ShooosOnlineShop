package com.example.shooosonlineshop.service;

import com.example.shooosonlineshop.model.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Boolean save(UserDTO userDTO);
}
