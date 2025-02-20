package com.example.CDIO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.CDIO.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    public Boolean existsByEmail(String email);

    User findByRefreshTokenAndEmail(String token, String email);

}
