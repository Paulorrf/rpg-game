package com.evo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evo.dtos.MagicDto;
import com.evo.dtos.PotionDto;
import com.evo.mapper.PotionMapper;
import com.evo.models.Magic;
import com.evo.models.Potion;
import com.evo.repositories.PotionRepository;

@Service
public class PotionService {
    private PotionRepository potionRepository;
    private PotionMapper potionMapper;

    @Autowired
    PotionService(PotionRepository potionRepository, PotionMapper potionMapper) {
        this.potionRepository = potionRepository;
        this.potionMapper = potionMapper;
    }

    public PotionDto save(PotionDto potionDto) {
        Optional<Potion> potionAlreadyExist = potionRepository.findByName(potionDto.name());

        try {
            if(potionAlreadyExist.isPresent()) {
                throw new RuntimeException("Potion already exist.");
            }

            Potion potion = potionMapper.potionDtoToPotion(potionDto);

            
            if(potion != null) {
                potionRepository.save(potion);
                return potionMapper.potionToPotionDto(potion);
            }

            return null;

            
        } catch (Exception e) {
            throw new RuntimeException("Error on potion creation. " + e.getMessage());
        }
    }

    public PotionDto findById(Long id) {
        try {
            Optional<Potion> potion = potionRepository.findById(id);
            if(potion.isPresent()) {
                PotionDto potionDto = potionMapper.potionToPotionDto(potion.get());
                return potionDto;
            }else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error on find potion by id.");
        }
    }

    public PotionDto findByName(String name) {
        try {
            Optional<Potion> potion = potionRepository.findByName(name);
            if(potion.isPresent()) {
                return potionMapper.potionToPotionDto(potion.get());
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error on find potion by name.");
        }
    
    }

    public List<PotionDto> findAll() {
        try {
            List<Potion> potions = potionRepository.findAll();
            return potionMapper.potionToPotionDtoList(potions);
        } catch (Exception e) {
            throw new RuntimeException("Error on find all potions.");
        }
    }

    public PotionDto update(Long id, PotionDto potionDto) {

        if(id == null) {
            throw new RuntimeException("Unable to update magic, only null was passed.");
        }

        Potion potion = potionRepository.findById(id).orElse(null);

        if (potion == null) {
            throw new RuntimeException("Magic doesn't exist.");
        }
        if (potionDto.name() != null) {
            potion.setName(potionDto.name());
        }
        if (potionDto.description() != null) {
            potion.setDescription(potionDto.description());
        }
        if (potionDto.effect() != null) {
            potion.setEffect(potionDto.effect());
        }
        if (potionDto.size() != 0) {
            potion.setSize(potionDto.size());
        } 
        Potion savedPotion = potionRepository.save(potion);

        return potionMapper.potionToPotionDto(savedPotion);
    }

    public void delete(Long id) {
        try {
            if(id != null) {
                potionRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("ID cannot be null");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error on delete potion." + e.getMessage());
        }
    }
    
}
