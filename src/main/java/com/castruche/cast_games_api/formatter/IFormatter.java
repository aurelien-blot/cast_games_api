package com.castruche.cast_games_api.formatter;

import com.castruche.cast_games_api.dto.AbstractDto;
import com.castruche.cast_games_api.entity.AbstractEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface IFormatter<ENTITY, DTO> {

    DTO entityToDto(ENTITY entity);
    ENTITY dtoToEntity(DTO dto);
    default List<DTO> entityToDto(List<ENTITY> entityList) {
        List<DTO> results= new ArrayList<>();
        if(entityList == null || entityList.isEmpty()){
            return results;
        }
        entityList.forEach(entity -> results.add(entityToDto(entity)));
        return results;
    }

}
