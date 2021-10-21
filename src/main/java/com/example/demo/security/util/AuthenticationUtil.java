package com.example.demo.security.util;


import com.example.demo.model.User;
import com.example.demo.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUtil {


    @Autowired
    private RepositoryUser userRepository;

    public User getCurrentUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Object principal = auth.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                String username = ((CustomUserDetails) auth.getPrincipal()).getUsername();
                return userRepository.findUserByEmail(username).get();
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }
}
