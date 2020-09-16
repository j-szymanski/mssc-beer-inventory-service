package prv.jws.beer.inventory.service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import prv.jws.beer.inventory.service.config.JmsConfig;
import prv.jws.beer.inventory.service.domain.BeerInventory;
import prv.jws.beer.inventory.service.repositories.BeerInventoryRepository;
import prv.jws.brewery.dto.BeerDto;
import prv.jws.brewery.events.NewInventoryEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryUpdateListener {
    private final BeerInventoryRepository  beerInventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listenForBrewingRequest(NewInventoryEvent event) {
        BeerDto beerDto = event.getBeerDto();

        BeerInventory beerInventory = BeerInventory.builder()
                .beerId(beerDto.getId())
                .upc(beerDto.getUpc())
                .quantityOnHand(beerDto.getQuantityOnHand())
                .build();
        beerInventoryRepository.save(beerInventory);

        log.debug(">>>   Brewed beer {}/{} ", beerDto.getBeerName(), beerDto.getBeerStyle());
        log.debug(">>>   New inventory quantity {} ", beerDto.getQuantityOnHand());
    }
}
