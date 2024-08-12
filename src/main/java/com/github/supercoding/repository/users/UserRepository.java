package com.github.supercoding.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
        // findUserById 로 했다가 에러났었음
    Optional<UserEntity> findByUserId(Integer userId);

    Optional<UserEntity> findByUserName(String username);
}
