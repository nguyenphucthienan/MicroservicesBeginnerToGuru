package com.nguyenphucthienan.msscbrewery.service;

import com.nguyenphucthienan.msscbrewery.web.model.CustomerDTO;

import java.util.UUID;

public interface CustomerService {

    CustomerDTO getCustomerById(UUID id);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(UUID id, CustomerDTO customerDTO);

    void deleteById(UUID id);
}

