package com.nguyenphucthienan.msscbeerorderservice.web.mapper;

import com.nguyenphucthienan.brewery.model.CustomerDTO;
import com.nguyenphucthienan.msscbeerorderservice.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {

    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
