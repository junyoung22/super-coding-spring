package com.github.supercoding.repository.userPrincipal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserPrincipalRepository extends JpaRepository<UserPrincipal, Integer> {

    @Query("SELECT up FROM UserPrincipal up JOIN FETCH up.userPrincipalRoles upr JOIN FETCH upr.roles r WHERE up.email = :email ")
    Optional<UserPrincipal> findByEmailFetchJoin(String email);

    boolean existsByEmail(String email);
}
