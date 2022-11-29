package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.AddmissionRules;
import com.mycompany.myapp.service.dto.AddmissionRulesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AddmissionRules} and its DTO {@link AddmissionRulesDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddmissionRulesMapper extends EntityMapper<AddmissionRulesDTO, AddmissionRules> {}
