package com.nguyenphucthienan.msscbeerservice.service.inventory;

import com.nguyenphucthienan.brewery.model.BeerInventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "inventory-failover-service")
public interface InventoryFailoverServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/inventory-failover")
    ResponseEntity<List<BeerInventoryDTO>> getOnHandInventories();
}
