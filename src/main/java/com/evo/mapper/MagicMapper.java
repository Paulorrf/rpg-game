package com.evo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.evo.dtos.MagicDto;
import com.evo.models.Magic;

@Mapper(componentModel = "spring")
public interface MagicMapper {

    MagicMapper INSTANCE = Mappers.getMapper( MagicMapper.class);

    MagicDto magicToMagicDto(Magic magic);

    Magic magicDtoToMagic(MagicDto magicDto);

    List<MagicDto> magicToMagicDtoList(List<Magic> magic);

    List<Magic> magicDtoToMagicList(List<MagicDto> magicDto);

}
