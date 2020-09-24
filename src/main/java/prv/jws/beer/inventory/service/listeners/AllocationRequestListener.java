package prv.jws.beer.inventory.service.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import prv.jws.beer.inventory.service.config.JmsConfig;
import prv.jws.beer.inventory.service.services.AllocationService;
import prv.jws.brewery.events.AllocateBeerOrderRequest;
import prv.jws.brewery.events.AllocateBeerOrderResponse;
import prv.jws.brewery.events.AllocateBeerOrderResponse.AllocateBeerOrderResponseBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllocationRequestListener {
    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listenForNewInventory(AllocateBeerOrderRequest event) {
        final AllocateBeerOrderResponseBuilder builder = AllocateBeerOrderResponse.builder();
        builder.beerOrder(event.getBeerOrderDto());

        try {
            final Boolean allocationResult = allocationService.allocateOrder(event.getBeerOrderDto());
            builder.pendingInventory(!allocationResult);
        }
        catch (Exception e) {
            log.error("UNEXPECTED ERROR FOR ORDER ID {}",event.getBeerOrderDto().getId(), e);
            builder.allocationError(true);
        }
        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE, builder.build());
    }
}
