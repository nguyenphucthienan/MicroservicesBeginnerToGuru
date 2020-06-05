package com.nguyenphucthienan.msscbeerinventoryservice.service;

import com.nguyenphucthienan.brewery.model.BeerOrderDTO;
import com.nguyenphucthienan.brewery.model.BeerOrderLineDTO;
import com.nguyenphucthienan.msscbeerinventoryservice.domain.BeerInventory;
import com.nguyenphucthienan.msscbeerinventoryservice.repository.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service
public class AllocationServiceImpl implements AllocationService {

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public Boolean allocateOrder(BeerOrderDTO beerOrderDTO) {
        log.debug("Allocating BeerOrder ID: " + beerOrderDTO.getId());

        AtomicInteger totalOrdered = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger();

        beerOrderDTO.getBeerOrderLines().forEach(beerOrderLine -> {
            if ((((beerOrderLine.getOrderQuantity() != null ? beerOrderLine.getOrderQuantity() : 0)
                    - (beerOrderLine.getQuantityAllocated() != null ? beerOrderLine.getQuantityAllocated() : 0)) > 0)) {
                allocateBeerOrderLine(beerOrderLine);
            }
            totalOrdered.set(totalOrdered.get() + beerOrderLine.getOrderQuantity());
            totalAllocated.set(totalAllocated.get() + (beerOrderLine.getQuantityAllocated() != null ? beerOrderLine.getQuantityAllocated() : 0));
        });

        log.debug("Total Ordered: " + totalOrdered.get() + ". Total Allocated: " + totalAllocated.get());
        return totalOrdered.get() == totalAllocated.get();
    }

    @Override
    public void deallocateOrder(BeerOrderDTO beerOrderDTO) {
        beerOrderDTO.getBeerOrderLines().forEach(beerOrderLineDto -> {
            BeerInventory beerInventory = BeerInventory.builder()
                    .beerId(beerOrderLineDto.getBeerId())
                    .upc(beerOrderLineDto.getUpc())
                    .quantityOnHand(beerOrderLineDto.getQuantityAllocated())
                    .build();

            BeerInventory savedInventory = beerInventoryRepository.save(beerInventory);
            log.debug("Saved BeerInventory for Beer UPC: " + savedInventory.getUpc()
                    + ". BeerInventory ID: " + savedInventory.getId());
        });
    }

    private void allocateBeerOrderLine(BeerOrderLineDTO beerOrderLineDTO) {
        List<BeerInventory> beerInventories = beerInventoryRepository.findAllByUpc(beerOrderLineDTO.getUpc());
        beerInventories.forEach(beerInventory -> {
            int inventory = (beerInventory.getQuantityOnHand() == null) ? 0 : beerInventory.getQuantityOnHand();
            int orderQuantity = (beerOrderLineDTO.getOrderQuantity() == null) ? 0 : beerOrderLineDTO.getOrderQuantity();
            int allocatedQuantity = (beerOrderLineDTO.getQuantityAllocated() == null) ? 0 : beerOrderLineDTO.getQuantityAllocated();
            int quantityToAllocate = orderQuantity - allocatedQuantity;

            if (inventory >= quantityToAllocate) { // Full allocation
                beerOrderLineDTO.setQuantityAllocated(orderQuantity);
                beerInventory.setQuantityOnHand(inventory - quantityToAllocate);
                beerInventoryRepository.save(beerInventory);
            } else if (inventory > 0) { // Partial allocation
                beerOrderLineDTO.setQuantityAllocated(allocatedQuantity + inventory);
                beerInventory.setQuantityOnHand(0);
            }

            if (beerInventory.getQuantityOnHand() == 0) {
                beerInventoryRepository.delete(beerInventory);
            }
        });
    }
}
