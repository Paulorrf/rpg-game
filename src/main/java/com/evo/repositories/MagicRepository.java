package com.evo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.models.Magic;

public interface MagicRepository extends JpaRepository<Magic, Long>{

    Magic findByName(String name);

}
