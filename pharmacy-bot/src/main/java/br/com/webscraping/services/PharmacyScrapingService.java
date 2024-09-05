package br.com.webscraping.services;

import br.com.webscraping.dto.PharmacyResponseDTO;
import br.com.webscraping.mapper.PharmacyMapper;
import br.com.webscraping.mapper.PharmacyResponseMapper;
import br.com.webscraping.repositories.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PharmacyScrapingService {

    private final PharmacyRepository repository;
    private final PharmacyResponseMapper responseMapper;


    @Transactional(readOnly = true)
    public List<PharmacyResponseDTO> findAll() {
        return responseMapper.toDto(repository.findAll());
    }

}
