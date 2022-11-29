package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.RegInstructuion;
import com.mycompany.myapp.repository.RegInstructuionRepository;
import com.mycompany.myapp.service.RegInstructuionService;
import com.mycompany.myapp.service.dto.RegInstructuionDTO;
import com.mycompany.myapp.service.mapper.RegInstructuionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RegInstructuion}.
 */
@Service
@Transactional
public class RegInstructuionServiceImpl implements RegInstructuionService {

    private final Logger log = LoggerFactory.getLogger(RegInstructuionServiceImpl.class);

    private final RegInstructuionRepository regInstructuionRepository;

    private final RegInstructuionMapper regInstructuionMapper;

    public RegInstructuionServiceImpl(RegInstructuionRepository regInstructuionRepository, RegInstructuionMapper regInstructuionMapper) {
        this.regInstructuionRepository = regInstructuionRepository;
        this.regInstructuionMapper = regInstructuionMapper;
    }

    @Override
    public RegInstructuionDTO save(RegInstructuionDTO regInstructuionDTO) {
        log.debug("Request to save RegInstructuion : {}", regInstructuionDTO);
        RegInstructuion regInstructuion = regInstructuionMapper.toEntity(regInstructuionDTO);
        regInstructuion = regInstructuionRepository.save(regInstructuion);
        return regInstructuionMapper.toDto(regInstructuion);
    }

    @Override
    public RegInstructuionDTO update(RegInstructuionDTO regInstructuionDTO) {
        log.debug("Request to update RegInstructuion : {}", regInstructuionDTO);
        RegInstructuion regInstructuion = regInstructuionMapper.toEntity(regInstructuionDTO);
        regInstructuion = regInstructuionRepository.save(regInstructuion);
        return regInstructuionMapper.toDto(regInstructuion);
    }

    @Override
    public Optional<RegInstructuionDTO> partialUpdate(RegInstructuionDTO regInstructuionDTO) {
        log.debug("Request to partially update RegInstructuion : {}", regInstructuionDTO);

        return regInstructuionRepository
            .findById(regInstructuionDTO.getId())
            .map(existingRegInstructuion -> {
                regInstructuionMapper.partialUpdate(existingRegInstructuion, regInstructuionDTO);

                return existingRegInstructuion;
            })
            .map(regInstructuionRepository::save)
            .map(regInstructuionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RegInstructuionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RegInstructuions");
        return regInstructuionRepository.findAll(pageable).map(regInstructuionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RegInstructuionDTO> findOne(Long id) {
        log.debug("Request to get RegInstructuion : {}", id);
        return regInstructuionRepository.findById(id).map(regInstructuionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegInstructuion : {}", id);
        regInstructuionRepository.deleteById(id);
    }
}
