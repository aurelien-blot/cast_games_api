package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.formatter.IFormatter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public abstract class GenericService<ENTITY, DTO> {

    private final JpaRepository<ENTITY, Long> repository;
    private final IFormatter<ENTITY, DTO> formatter;

    public GenericService(JpaRepository<ENTITY, Long> repository, IFormatter<ENTITY, DTO> formatter) {
        this.repository = repository;
        this.formatter = formatter;
    }

    public Optional<ENTITY> selectById(Long id) {
        return repository.findById(id);
    }
    public DTO selectDtoById(Long id) {
        Optional<ENTITY> entity = repository.findById(id);
        return entity.map(formatter::entityToDto).orElse(null);
    }
}
