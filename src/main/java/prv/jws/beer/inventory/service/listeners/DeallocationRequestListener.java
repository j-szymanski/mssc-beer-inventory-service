package prv.jws.beer.inventory.service.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import prv.jws.beer.inventory.service.config.JmsConfig;
import prv.jws.beer.inventory.service.services.AllocationService;
import prv.jws.brewery.events.DeallocateBeerOrderRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeallocationRequestListener {
    private final AllocationService allocationService;

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
    public void listenForNewInventory(DeallocateBeerOrderRequest event) {
        allocationService.deallocateOrder(event.getBeerOrderDto());
//        final DeallocateBeerOrderRequest builder = DeallocateBeerOrderRequest.builder();
//        builder.beerOrder(event.getBeerOrderDto());
//
//        try {
//            final Boolean allocationResult = allocationService.allocateOrder(event.getBeerOrderDto());
//            builder.pendingInventory(!allocationResult);
//        }
//        catch (Exception e) {
//            log.error("UNEXPECTED ERROR FOR ORDER ID {}",event.getBeerOrderDto().getId(), e);
//            builder.allocationError(true);
//        }
//        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE, builder.build());
    }
}
