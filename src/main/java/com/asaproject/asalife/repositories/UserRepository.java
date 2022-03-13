package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

public interface UserRepository extends JpaRepository<User, Long> {
    @Nullable
    User findByNrp(String nrp);

    @Nullable
    User findByOtp(String otp);
}