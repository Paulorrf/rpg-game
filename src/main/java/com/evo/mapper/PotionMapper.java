package com.evo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.evo.dtos.PotionDto;
import com.evo.models.Potion;

@Mapper(componentModel = "spring")
public interface PotionMapper {
    PotionMapper INSTANCE = Mappers.getMapper( PotionMapper.class);

    PotionDto potionToPotionDto(Potion potion);

    Potion potionDtoToPotion(PotionDto potionDto);

    List<PotionDto> potionToPotionDtoList(List<Potion> potionList);

    List<Potion> potionDtoToPotionList(List<PotionDto> potionDtoList);
}
