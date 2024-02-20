package com.eazybank.loans.mapper;

import com.eazybank.loans.dto.LoansDto;
import com.eazybank.loans.entity.Loans;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface LoansMapper extends EntityMapper<LoansDto, Loans> {
}
