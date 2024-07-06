package com.eazybank.accounts.mapper;

import com.eazybank.accounts.dto.CustomerDetailsDto;
import com.eazybank.accounts.dto.CustomerDto;
import com.eazybank.accounts.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDto, Customer> {
    CustomerDetailsDto toCustomerDetailsDto(Customer customer);
}
