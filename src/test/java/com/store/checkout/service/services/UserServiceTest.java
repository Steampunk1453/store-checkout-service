package com.store.checkout.service.services;

import com.store.checkout.service.domain.Role;
import com.store.checkout.service.domain.User;
import com.store.checkout.service.repositories.RoleRepository;
import com.store.checkout.service.repositories.UserRepository;
import com.store.checkout.service.services.mappers.UserDetailsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private DefaultUserService userService;

    @Mock
    private UserDetailsMapper userDetailsMapper;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    public void whenGetLoadUserByUsernameCallMapper() {
        var user = buildUser();
        when(userRepository.findByName(anyString())).thenReturn(user);

        userService.loadUserByUsername("username");

        verify(userDetailsMapper, times(1)).build(any(User.class));
    }

    @Test
    public void whenSaveCallRepository() {
        var role = buildRole();
        var user = buildUser();
        when(roleRepository.findByName(anyString())).thenReturn(role);

        userService.save(user);

        verify(userRepository, times(1)).save(any(User.class));
    }

    private User buildUser() {
        Set<Role> roles = new HashSet<>();
        roles.add(buildRole());
       return User.builder().name("user").password("password").roles(roles).build();
    }

    private Role buildRole() {
        return Role.builder().name("USER").description("User role").build();
    }

}