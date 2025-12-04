package com.example.Healthify.repositories;

import com.example.Healthify.models.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {


    Optional<Users> findByUserId(String userId);

    boolean existsByUserId(String userId);

    void deleteByUserId(String userId);

    Optional<Users> findByEmail(String email);

    Page<Users> findAll(Pageable pageable);

    Page<Users> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Users> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    Page<Users> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String name, String email, Pageable pageable);
}
