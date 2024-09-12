package com.bank.authorization.repository;

import com.bank.authorization.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT e FROM User e WHERE e.profile_id = :profile_id")
    User findByProfile_id(@Param("profile_id") Long profile_id);
}
