package com.github.supercoding.repository.userPrincipal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserPrincipalRolesRepository extends JpaRepository<UserPrincipalRoles, Integer> {

}
