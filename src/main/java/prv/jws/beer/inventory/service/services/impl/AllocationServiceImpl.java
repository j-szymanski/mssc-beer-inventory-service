package prv.jws.beer.inventory.service.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import prv.jws.beer.inventory.service.domain.BeerInventory;
import prv.jws.beer.inventory.service.repositories.BeerInventoryRepository;
import prv.jws.beer.inventory.service.services.AllocationService;
import prv.jws.brewery.model.BeerOrderDto;
import prv.jws.brewery.model.BeerOrderLineDto;

import static java.util.Objects.nonNull;

/**
 * Created by Jerzy Szymanski on 24.09.2020 at 14:36
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AllocationServiceImpl implements AllocationService {
    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public Boolean allocateOrder(final BeerOrderDto beerOrderDto) {
        log.debug("Allocating order id: {}", beerOrderDto.getId());
        AtomicInteger totalOrdered = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger();

        beerOrderDto.getBeerOrderLines().forEach(orderLine -> {
            if (((nonNull(orderLine.getOrderQuantity()) ? orderLine.getOrderQuantity() : 0)
                 - (nonNull(orderLine.getQuantityAllocated()) ? orderLine.getQuantityAllocated() : 0)) > 0) {
                allocateBeerOrderLine(orderLine);
            }
            totalOrdered.set(totalOrdered.get() + orderLine.getOrderQuantity());
            totalAllocated.set(totalAllocated.get() + (orderLine.getQuantityAllocated() != null ? orderLine.getQuantityAllocated() : 0));
        });
        return totalOrdered.get() == totalAllocated.get();
    }

    @Override
    public void deallocateOrder(final BeerOrderDto beerOrderDto) {
        beerOrderDto.getBeerOrderLines().forEach(beerOrderLine -> {
            BeerInventory beerInventory = BeerInventory.builder()
                    .beerId(beerOrderLine.getBeerId())
                    .upc(beerOrderLine.getUpc())
                    .quantityOnHand(beerOrderLine.getQuantityAllocated())
                    .build();
            BeerInventory saved = beerInventoryRepository.save(beerInventory);
            log.debug("Saved Inventory for beer UOC:{}, quantity: {}, inventory id: {}",
                    saved.getUpc(), saved.getQuantityOnHand(), saved.getId());
        });
    }

    private void allocateBeerOrderLine(final BeerOrderLineDto orderLine) {
        List<BeerInventory> beerInventoryList = beerInventoryRepository.findAllByUpc(orderLine.getUpc());
        beerInventoryList.forEach(beerInventory -> {
            int inventory = nonNull(beerInventory.getQuantityOnHand()) ? beerInventory.getQuantityOnHand() : 0;
            int orderQty = nonNull(orderLine.getOrderQuantity()) ? orderLine.getOrderQuantity() : 0;
            int allocatedQty = nonNull(orderLine.getQuantityAllocated()) ? orderLine.getQuantityAllocated() : 0;
            int qtyToAllocate = orderQty - allocatedQty;

            if (inventory >= qtyToAllocate) {
                inventory = inventory - qtyToAllocate;
                orderLine.setQuantityAllocated(orderQty);
                beerInventory.setQuantityOnHand(inventory);
                beerInventoryRepository.save(beerInventory);
            }
            else if (inventory > 0) {
                orderLine.setQuantityAllocated(allocatedQty + inventory);
                beerInventory.setQuantityOnHand(0);
            }

            if (beerInventory.getQuantityOnHand()==0) {
                beerInventoryRepository.delete(beerInventory);
            }
        });
    }
}
