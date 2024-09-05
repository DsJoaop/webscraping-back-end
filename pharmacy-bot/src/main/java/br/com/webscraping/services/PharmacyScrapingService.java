package br.com.webscraping.services;

import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.mapper.PharmacyMapper;
import br.com.webscraping.repositories.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PharmacyScrapingService {

    private final PharmacyRepository repository;
    private final PharmacyMapper mapper;


    @Transactional(readOnly = true)
    public List<PharmacyDTO> findAll() {
        return mapper.toDto(repository.findAll());
    }

}
