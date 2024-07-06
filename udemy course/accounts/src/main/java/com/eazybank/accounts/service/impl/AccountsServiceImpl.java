package com.eazybank.accounts.service.impl;

import com.eazybank.accounts.constants.AccountsConstants;
import com.eazybank.accounts.dto.AccountsDto;
import com.eazybank.accounts.dto.AccountsMsgDto;
import com.eazybank.accounts.dto.CustomerDto;
import com.eazybank.accounts.entity.Accounts;
import com.eazybank.accounts.entity.Customer;
import com.eazybank.accounts.exception.CustomerAlreadyExistsException;
import com.eazybank.accounts.exception.ResourceNotFoundException;
import com.eazybank.accounts.mapper.AccountsMapper;
import com.eazybank.accounts.mapper.CustomerMapper;
import com.eazybank.accounts.repository.AccountsRepository;
import com.eazybank.accounts.repository.CustomerRepository;
import com.eazybank.accounts.service.IAccountsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private static Logger log = LoggerFactory.getLogger(AccountsServiceImpl.class);

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AccountsMapper accountsMapper;
    private final StreamBridge streamBridge;

    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "
                    + customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        Accounts savedAccount = accountsRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccount,savedCustomer);
    }

    private void sendCommunication(Accounts account, Customer customer){
        var accountsMsgDto = new AccountsMsgDto(account.getAccountNumber(),customer.getName(),
                customer.getEmail(),customer.getMobileNumber());
        log.info("Sending communication request for the details: {}", accountsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        log.info("Is the communication request successfully triggered?: {}", result);
    }


    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = customerMapper.toDto(customer);
        customerDto.setAccountsDto(accountsMapper.toDto(accounts));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException
                            ("Account", "accountNumber", accountsDto.getAccountNumber().toString())
            );
            Accounts accountsToSave = accountsMapper.toEntity(accountsDto);
            accountsToSave.setCustomerId(accounts.getCustomerId());

            accounts = accountsRepository.save(accountsToSave);

            Long customerId = accounts.getCustomerId();
            customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
            );

            Customer customerToSave = customerMapper.toEntity(customerDto);
            customerToSave.setCustomerId(customerId);

            customerRepository.save(customerToSave);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if(accountNumber != null){
            Accounts accounts = accountsRepository.findById(accountNumber).orElseThrow(
                    ()->new ResourceNotFoundException("Account", "accountNumber", accountNumber.toString())
            );
            accounts.setCommunicationSw(true);
            accountsRepository.save(accounts);
            isUpdated = true;

        }
        return isUpdated;
    }


}
