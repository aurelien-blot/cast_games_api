package com.castruche.cast_games_api.dao;

import com.castruche.cast_games_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String mail);

    User findByUsername(String username);
    User findByEmail(String mail);
    User findByMailVerificationToken(String token);
    User findByResetPasswordToken(String token);

}
