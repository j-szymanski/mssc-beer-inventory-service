package prv.jws.beer.inventory.service.services;

import prv.jws.brewery.model.BeerOrderDto;

/**
 * Created by Jerzy Szymanski on 24.09.2020 at 14:34
 */
public interface AllocationService {
    Boolean allocateOrder(BeerOrderDto beerOrderDto);
}
