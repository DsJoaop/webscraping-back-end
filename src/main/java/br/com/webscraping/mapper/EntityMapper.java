package br.com.webscraping.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);


    default Set<E> toEntity(Set<D> dtoSet) {
        if (dtoSet == null) {
            return null;
        }
        return dtoSet.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }

    default Set<D> toDto(Set<E> entitySet) {
        if (entitySet == null) {
            return null;
        }
        return entitySet.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
}
