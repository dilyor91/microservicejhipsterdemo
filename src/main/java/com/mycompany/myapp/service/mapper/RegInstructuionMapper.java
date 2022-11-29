package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.RegInstructuion;
import com.mycompany.myapp.service.dto.RegInstructuionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RegInstructuion} and its DTO {@link RegInstructuionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegInstructuionMapper extends EntityMapper<RegInstructuionDTO, RegInstructuion> {}
