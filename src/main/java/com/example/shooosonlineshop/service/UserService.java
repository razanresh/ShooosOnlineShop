package com.example.shooosonlineshop.service;

import com.example.shooosonlineshop.model.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    Boolean save(UserDTO userDTO);
    List<UserDTO> getAll();
}
