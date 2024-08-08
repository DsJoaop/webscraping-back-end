package br.com.webscraping.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);
}
