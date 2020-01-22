package com.store.checkout.service.repositories;

import com.store.checkout.service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByName(String name);
}
