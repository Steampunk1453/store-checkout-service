package com.store.checkout.service.services;

import com.store.checkout.service.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUser(long id);
    User save(User user);
    List<User> getAll();
}
