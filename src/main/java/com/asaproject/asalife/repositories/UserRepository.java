package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Nullable
    User findByNrp(String nrp);

    @Nullable
    User findByOtp(String otp);

    @Query(value = "SELECT u.* FROM users u " +
            "JOIN users_roles ur ON u.id  = ur.user_id " +
            "JOIN roles r ON r.id  = ur.role_id WHERE r.\"name\" = 'ROLE_MT'", nativeQuery = true)
    List<User> findByRole();

    void deleteByNrp(String nrp);

    @Modifying
    @Query(value = "DELETE from users u where u.nrp=:nrp", nativeQuery = true)
    void deleteByNrpNative(@Param("nrp") String nrp);
}