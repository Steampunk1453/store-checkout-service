package com.store.checkout.service.services;

import com.store.checkout.service.domain.Role;
import com.store.checkout.service.domain.User;
import com.store.checkout.service.repositories.RoleRepository;
import com.store.checkout.service.repositories.UserRepository;
import com.store.checkout.service.services.mappers.UserDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class DefaultUserService implements UserService {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final UserDetailsMapper userDetailsMapper;

	@Autowired
	public DefaultUserService(UserRepository userRepository, RoleRepository roleRepository, UserDetailsMapper userDetailsMapper) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userDetailsMapper = userDetailsMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		final var retrievedUser = userRepository.findByName(userName);
		if (retrievedUser == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return userDetailsMapper.build(retrievedUser);
	}

	@Override
	public User getUser(long id) {
		return userRepository.getOne(id);
	}

	@Override
	public User save(User user) {
		var userRole = roleRepository.findByName("USER");
		Set<Role> roles = new HashSet<>();
		roles.add(userRole);
		var userToSave = User.builder().name(user.getName()).password(user.getPassword()).roles(roles).build();
		return userRepository.save(userToSave);
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

}
