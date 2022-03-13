package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}