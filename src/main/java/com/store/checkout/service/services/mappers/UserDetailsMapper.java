package com.store.checkout.service.services.mappers;

import com.store.checkout.service.domain.Role;
import com.store.checkout.service.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsMapper {

	public UserDetails build(User user) {
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), getAuthorities(user));
	}

	private static Set<? extends GrantedAuthority> getAuthorities(User retrievedUser) {
		Set<Role> roles = retrievedUser.getRoles();
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
		return authorities;
	}
}
