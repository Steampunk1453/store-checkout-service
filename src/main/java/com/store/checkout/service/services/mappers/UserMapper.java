package com.store.checkout.service.services.mappers;

import com.store.checkout.service.domain.User;
import com.store.checkout.service.security.dtos.AuthorizationRequest;
import com.store.checkout.service.security.dtos.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserMapper {

	public UserResponse toResponse(User user) {
		return UserResponse.builder().name(user.getName()).id(user.getId()).build();
	}

	public User toDomain(AuthorizationRequest authorizationRequest) {
		return User.builder().name(authorizationRequest.getUserName()).password(authorizationRequest.getPassword())
				.build();
	}

	public List<UserResponse> usersToResponse(List<User> users) {
		return users.stream()
				.filter(Objects::nonNull)
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

}
