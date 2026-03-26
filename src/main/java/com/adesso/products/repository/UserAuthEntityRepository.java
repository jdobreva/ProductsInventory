package com.adesso.products.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adesso.products.entity.UserAuthEntity;

@Repository
public interface UserAuthEntityRepository extends JpaRepository<UserAuthEntity, Long> {

	Optional<UserAuthEntity> findByUsername(String username);
}
