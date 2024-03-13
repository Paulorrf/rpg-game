package com.evo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evo.models.Potion;

@Repository
public interface PotionRepository extends JpaRepository<Potion, Long>{
    
    Optional<Potion> findByName(String name);
}
