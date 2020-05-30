package com.nguyenphucthienan.msscbeerservice.repository;

import com.nguyenphucthienan.msscbeerservice.domain.Beer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
}
