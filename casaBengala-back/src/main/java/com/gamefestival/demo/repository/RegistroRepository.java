package com.gamefestival.demo.repository;

import com.gamefestival.demo.entity.RegistroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistroRepository extends JpaRepository<RegistroEntity, Long> {

    Optional<RegistroEntity> findByEmail(String email);
}
