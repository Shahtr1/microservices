package com.eazybank.accounts.mapper;

import com.eazybank.accounts.dto.AccountsDto;
import com.eazybank.accounts.entity.Accounts;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountsMapper extends EntityMapper<AccountsDto, Accounts> {
}
