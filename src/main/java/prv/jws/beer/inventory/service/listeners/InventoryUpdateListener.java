package prv.jws.beer.inventory.service.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import prv.jws.beer.inventory.service.config.JmsConfig;
import prv.jws.beer.inventory.service.domain.BeerInventory;
import prv.jws.brewery.events.NewInventoryEvent;
import prv.jws.beer.inventory.service.repositories.BeerInventoryRepository;
import prv.jws.brewery.model.BeerDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryUpdateListener {
    private final BeerInventoryRepository  beerInventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listenForNewInventory(NewInventoryEvent event) {
        BeerDto beerDto = event.getBeerDto();

        log.info("---------------------  Handling New Inventory event ------------------------");
        log.info("Beer id {}",  beerDto.getId());
        log.info("Beer name {}/{}", beerDto.getBeerName(), beerDto.getBeerStyle());
        log.info("UPC {}", beerDto.getUpc());
        log.info("Quantity Brewed (quantity on hand) {}", beerDto.getQuantityOnHand());

        BeerInventory beerInventory = BeerInventory.builder()
                .beerId(beerDto.getId())
                .upc(beerDto.getUpc())
                .quantityOnHand(beerDto.getQuantityOnHand())
                .build();
        beerInventoryRepository.save(beerInventory);

        log.debug(">>>   Brewed beer {}/{} ", beerDto.getBeerName(), beerDto.getBeerStyle());
        log.debug(">>>   New inventory quantity {} ", beerDto.getQuantityOnHand());
        log.info("---------------------               EOM              ------------------------");
    }
}
