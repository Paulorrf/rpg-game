package com.evo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evo.dtos.MagicDto;
import com.evo.mapper.MagicMapper;
import com.evo.models.Magic;
import com.evo.repositories.MagicRepository;

@Service
public class MagicService {

    private MagicRepository magicRepository;
    private MagicMapper magicMapper;

    @Autowired
    public MagicService(MagicRepository magicRepository, MagicMapper magicMapper) {
        this.magicRepository = magicRepository;
        this.magicMapper = magicMapper;
    }

    public MagicDto save(MagicDto magicDto) {
         Magic magicAlreadyExist = magicRepository.findByName(magicDto.name());

        try {
            if(magicAlreadyExist != null) {
                throw new RuntimeException("Error on magic creation.");
            }

            Magic magic = magicMapper.magicDtoToMagic(magicDto);

            
            if(magic != null) {
                magicRepository.save(magic);
                return magicMapper.magicToMagicDto(magic);
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error on magic creation. " + e.getMessage());
        }
    }

    public MagicDto findById(Long id) {
        if(id == null) {
            throw new RuntimeException("Unable to find magic.");
        }

        Optional<Magic> magic = magicRepository.findById(id);
        if(magic.isPresent()) {
            MagicDto magicDto = magicMapper.magicToMagicDto(magic.get());
            return magicDto;
        }else {
            throw new RuntimeException("Unable to find magic.");
        }
    }

    public List<MagicDto> findAll() {
        List<Magic> magic = magicRepository.findAll();
        if(magic.size() > 0) {
            List<MagicDto> magicDtos = magicMapper.magicToMagicDtoList(magic);
            return magicDtos;
        }else {
            throw new RuntimeException("Unable to find all magic.");
        }
    }

    public MagicDto update(Long id, MagicDto magicDto) {

        if(id == null) {
            throw new RuntimeException("Unable to update magic, only null was passed.");
        }

        Magic magic = magicRepository.findById(id).orElse(null);

        if (magic == null) {
            throw new RuntimeException("Magic doesn't exist.");
        }
        if (magicDto.name() != null) {
            magic.setName(magicDto.name());
        }
        if (magicDto.description() != null) {
            magic.setDescription(magicDto.description());
        }
        if (magicDto.damage() != null) {
            magic.setDamage(magicDto.damage());
        }
        Magic savedMagic = magicRepository.save(magic);

        return magicMapper.magicToMagicDto(savedMagic);
    }

    public void delete(Long id) {
        if(id == null) {
            throw new RuntimeException("Magic doesn't exist.");
        }
        magicRepository.deleteById(id);
    }
}
