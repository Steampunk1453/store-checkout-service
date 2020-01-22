package com.store.checkout.service.repositories;

import com.store.checkout.service.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
