package com.nguyenphucthienan.msscbrewery.web.mapper;

import com.nguyenphucthienan.msscbrewery.domain.Customer;
import com.nguyenphucthienan.msscbrewery.web.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
